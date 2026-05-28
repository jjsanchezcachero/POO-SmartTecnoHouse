package modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de logging Singleton que escribe las acciones sobre actuadores
 * en {@value #FICHERO} con formato tabular (columnas fijas)
 * <pre>
 * Timestamp                 Actuator     Action   Source
 * ---------------------------------------------------------
 * 2025-08-04T12:05:02Z      fan          HIGH     RULE
 * 2025-08-04T12:05:10Z      bulb         ON       MANUAL
 * </pre>
 */
public class ServicioLog {

    public static final String FICHERO = "actuators.log";

    private static final String CABECERA =
            String.format("%-25s %-12s %-8s %-10s", "Timestamp", "Actuator", "Action", "Source");
    private static final String SEPARADOR = "-".repeat(57);

    private static ServicioLog instancia;

    private final List<String> historial = new ArrayList<>();

    private ServicioLog() {
        escribirCabecera();
    }

    public List<String> getHistorial() { return historial; }


    /** @return única instancia del servicio */
    public static ServicioLog getInstance() {
        if (instancia == null) {
            instancia = new ServicioLog();
        }
        return instancia;
    }

    /**
     * Registra una acción sobre un actuador.
     *
     * @param idActuador identificador del actuador (ej. "fan")
     * @param accion     nuevo estado aplicado (ej. "HIGH")
     * @param fuente     origen de la acción: "RULE" o "MANUAL"
     */
    public void registrar(String idActuador, String accion, String fuente) {
        String ts = Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
        String linea = String.format("%-25s %-12s %-8s %-10s", ts, idActuador, accion, fuente);
        System.out.println("[LOG] " + linea);
        historial.add(linea);
        append(linea);
    }

    private void escribirCabecera() {
        historial.add(CABECERA);
        historial.add(SEPARADOR);
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO, false))) {
            pw.println(CABECERA);
            pw.println(SEPARADOR);
        } catch (IOException e) {
            System.err.println("[LOG] No se pudo crear " + FICHERO + ": " + e.getMessage());
        }
    }

    private void append(String linea) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO, true))) {
            pw.println(linea);
        } catch (IOException e) {
            System.err.println("[LOG] Error al escribir en el log: " + e.getMessage());
        }
    }
}
