package controlador;

import modelo.Actuador;
import modelo.Sensor;
import modelo.ServicioLog;
import modelo.ServicioPersistencia;
import modelo.SmartTecnoHouse;
import modelo.reglas.Regla;
import modelo.reglas.ReglaIluminacionAutomatica;
import modelo.reglas.ReglaVentilacionConfortable;
import vista.VistaPrincipal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controlador {
    private final SmartTecnoHouse modelo;
    private final VistaPrincipal  vista;

    /** Instancias canónicas de todas las reglas posibles (usadas para add/remove). */
    private List<Regla> todasLasReglas;

    public Controlador(SmartTecnoHouse modelo, VistaPrincipal vista) {
        this.modelo = modelo;
        this.vista  = vista;
    }

    /** Punto de entrada: carga estado persistido, configura la vista y la hace visible. */
    public void iniciar() {
        // Instancias canónicas — estas son las que se añaden/quitan del modelo
        todasLasReglas = new ArrayList<>();
        todasLasReglas.add(new ReglaVentilacionConfortable());
        todasLasReglas.add(new ReglaIluminacionAutomatica());


        // Carga el estado persistido (puede reemplazar las reglas del modelo)
        ServicioPersistencia.cargar(modelo);

        // Reemplaza las instancias del modelo con las instancias canónicas,
        // manteniendo el mismo conjunto de reglas activas (por nombre)
        Set<String> activos = new HashSet<>();
        for (Regla r : modelo.getReglas()) activos.add(r.getNombre());
        modelo.limpiarReglas();
        for (Regla r : todasLasReglas) {
            if (activos.contains(r.getNombre())) modelo.addRegla(r);
        }

        // Construye los estados iniciales para los checkboxes
        List<String> nombres = new ArrayList<>();
        boolean[] estadosActivos = new boolean[todasLasReglas.size()];
        for (int i = 0; i < todasLasReglas.size(); i++) {
            nombres.add(todasLasReglas.get(i).getNombre());
            estadosActivos[i] = activos.contains(todasLasReglas.get(i).getNombre());
        }

        vista.configurarActuadores(modelo.getActuadores());
        vista.configurarReglas(nombres, estadosActivos);
        conectarListeners();
        refrescar();
        vista.setVisible(true);
    }

    // -------------------------------------------------------------------------
    // Wiring de listeners
    // -------------------------------------------------------------------------

    private void conectarListeners() {
        // Shutdown hook: garantiza el guardado aunque se cierre desde el IDE
        // Solo para Test Usando IDE
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> ServicioPersistencia.guardar(modelo)));

        // WindowListener: guarda al cerrar con el botón X de la ventana
        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ServicioPersistencia.guardar(modelo);
            }
        });

        // Ciclo completo de automatización
        vista.getBotonCiclo().addActionListener(e -> {
            modelo.procesarCiclo();
            refrescar();
        });

        // Actualización manual de todos los sensores
        vista.getBotonSimularLectura().addActionListener(e -> {
            for (Sensor s : modelo.getSensores()) {
                s.actualizarValor();
            }
            vista.actualizarSensores(modelo.getSensores());
            vista.actualizarLog(ServicioLog.getInstance().getHistorial());
        });

        // Acción manual por actuador
        List<Actuador> actuadores = modelo.getActuadores();
        for (int i = 0; i < actuadores.size(); i++) {
            final int    idx = i;
            final String id  = actuadores.get(idx).getID();
            vista.getBotonAplicar(idx).addActionListener(e -> {
                modelo.ejecutarAccionManual(id, vista.getAccionSeleccionada(idx));
                vista.actualizarEstadoActuador(idx, modelo.getActuadores().get(idx).getEstadoActual());
                vista.actualizarLog(ServicioLog.getInstance().getHistorial());
            });
        }

        // Toggle de reglas: cada checkbox activa o desactiva su regla en el modelo
        for (int i = 0; i < todasLasReglas.size(); i++) {
            final Regla regla = todasLasReglas.get(i);
            final int   idx   = i;
            vista.getCheckRegla(idx).addActionListener(e -> {
                if (vista.getCheckRegla(idx).isSelected()) {
                    modelo.addRegla(regla);
                } else {
                    modelo.removeRegla(regla);
                }
            });
        }
    }

    // -------------------------------------------------------------------------
    // Refresco completo de la vista
    // -------------------------------------------------------------------------

    private void refrescar() {
        vista.actualizarSensores(modelo.getSensores());
        List<Actuador> actuadores = modelo.getActuadores();
        for (int i = 0; i < actuadores.size(); i++) {
            vista.actualizarEstadoActuador(i, actuadores.get(i).getEstadoActual());
        }
        vista.actualizarLog(ServicioLog.getInstance().getHistorial());
    }
}
