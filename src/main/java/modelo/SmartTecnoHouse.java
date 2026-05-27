package modelo;

import modelo.reglas.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo principal del sistema domótico (Patrón Singleton).
 * Centraliza las colecciones de sensores, actuadores y reglas, y expone
 * dos operaciones de alto nivel:
 * <ul>
 *   <li>{@link #procesarCiclo()} — actualiza sensores, aplica reglas y loguea cambios.</li>
 *   <li>{@link #ejecutarAccionManual(String, String)} — acción directa desde la GUI.</li>
 * </ul>
 *
 * En primera instancia se gestiona con estos ciclos de ejecución para comprobar su uso,
 * Se refactorizará cuando se integre con la GUI y posibles lecturas desde fichero.
 */
public class SmartTecnoHouse {

    private static SmartTecnoHouse instancia;

    private final List<Sensor> sensores;
    private final List<Actuador> actuadores;
    private final List<Regla>    reglas;
    private final ServicioLog    log;

    private SmartTecnoHouse() {
        sensores   = new ArrayList<>();
        actuadores = new ArrayList<>();
        reglas     = new ArrayList<>();
        log        = ServicioLog.getInstance();
        inicializar();
    }

    /** @return instancia única Patrón Singleton  */
    public static SmartTecnoHouse getInstance() {
        if (instancia == null) {
            instancia = new SmartTecnoHouse();
        }
        return instancia;
    }

    // -------------------------------------------------------------------------
    // Operaciones de ciclo
    // -------------------------------------------------------------------------

    /**
     * Ejecuta un ciclo completo de automatización:
     * <ol>
     *   <li>Actualiza todos los sensores.</li>
     *   <li>Aplica todas las reglas activas.</li>
     *   <li>Registra en el log los actuadores cuyo estado haya cambiado.</li>
     * </ol>
     */
    public void procesarCiclo() {
        for (Sensor s : sensores) {
            s.actualizarValor();
        }

        String[] estadoAntes = capturarEstadoActuadores();

        for (Regla r : reglas) {
            r.aplicar(sensores, actuadores);
        }

        for (int i = 0; i < actuadores.size(); i++) {
            String despues = actuadores.get(i).getEstadoActual();
            if (!despues.equals(estadoAntes[i])) {
                log.registrar(actuadores.get(i).getID(), despues, "RULE");
            }
        }
    }

    /**
     * Ejecuta una acción manual sobre un actuador (originada desde la GUI).
     *
     * @param idActuador identificador del actuador
     * @param accion     acción a aplicar
     * @throws IllegalArgumentException si el ID no existe o la acción no es válida
     */
    public void ejecutarAccionManual(String idActuador, String accion) {
        Actuador a = buscarActuador(idActuador);
        a.ejecutarAccion(accion);
        log.registrar(idActuador, accion, "MANUAL");
    }

    // -------------------------------------------------------------------------
    // Helpers privados - Getters
    // -------------------------------------------------------------------------

    public List<Sensor>   getSensores()   { return sensores; }
    public List<Actuador> getActuadores() { return actuadores; }

    // -------------------------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------------------------

    private void inicializar() {
        sensores.add(new SensorTemperatura());
        sensores.add(new SensorLuz());
        sensores.add(new SensorPresencia());
        sensores.add(new SensorHumedad());

        actuadores.add(new ActuadorBombilla());
        actuadores.add(new ActuadorVentilador());
        actuadores.add(new ActuadorEnchufeInteligente());

        reglas.add(new ReglaVentilacionConfortable());
        reglas.add(new ReglaIluminacionAutomatica());
    }

    private String[] capturarEstadoActuadores() {
        String[] estados = new String[actuadores.size()];
        for (int i = 0; i < actuadores.size(); i++) {
            estados[i] = actuadores.get(i).getEstadoActual();
        }
        return estados;
    }

    private Actuador buscarActuador(String id) {
        return actuadores.stream()
                .filter(a -> a.getID().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Actuador no encontrado: " + id));
    }
}
