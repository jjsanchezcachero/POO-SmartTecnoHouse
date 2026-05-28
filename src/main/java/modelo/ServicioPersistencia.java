package modelo;

import modelo.reglas.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Gestiona la persistencia del estado del sistema en formato JSON.
 *
 * <p>El fichero {@value #FICHERO} guarda el estado de los actuadores y
 * qué reglas están activas. Se escribe al cerrar la aplicación y se lee
 * al arrancar, antes de mostrar la GUI.</p>
 *
 * <p>Utiliza la librería <a href="https://github.com/stleary/JSON-java">org.json</a>
 * para serializar y deserializar</p>
 */
public class ServicioPersistencia {

    public static final String FICHERO = "estado.json";

    private ServicioPersistencia() {}

    // -------------------------------------------------------------------------
    // Guardar
    // -------------------------------------------------------------------------

    public static void guardar(SmartTecnoHouse sistema) {
        JSONObject raiz = new JSONObject();

        // Estado de los actuadores
        JSONArray jsonActuadores = new JSONArray();
        for (Actuador a : sistema.getActuadores()) {
            JSONObject obj = new JSONObject();
            obj.put("id",     a.getID());
            obj.put("estado", a.getEstadoActual());
            jsonActuadores.put(obj);
        }
        raiz.put("actuadores", jsonActuadores);

        // Nombres de las reglas activas
        JSONArray jsonReglas = new JSONArray();
        for (Regla r : sistema.getReglas()) {
            jsonReglas.put(r.getNombre());
        }
        raiz.put("reglas", jsonReglas);

        try (Writer w = new OutputStreamWriter(
                new FileOutputStream(FICHERO), StandardCharsets.UTF_8)) {
            w.write(raiz.toString(2));   // indentación de 2 espacios
        } catch (IOException e) {
            System.err.println("[PERSISTENCIA] Error al guardar: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Cargar
    // -------------------------------------------------------------------------

    public static void cargar(SmartTecnoHouse sistema) {
        File f = new File(FICHERO);
        if (!f.exists()) return;

        try {
            String contenido = new String(Files.readAllBytes(Paths.get(FICHERO)), StandardCharsets.UTF_8);
            JSONObject raiz  = new JSONObject(contenido);

            cargarActuadores(raiz, sistema);
            cargarReglas(raiz, sistema);

        } catch (IOException e) {
            System.err.println("[PERSISTENCIA] Error al cargar: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // Helpers privados
    // -------------------------------------------------------------------------

    private static void cargarActuadores(JSONObject raiz, SmartTecnoHouse sistema) {
        if (!raiz.has("actuadores")) return;

        JSONArray arr = raiz.getJSONArray("actuadores");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj    = arr.getJSONObject(i);
            String     id     = obj.getString("id");
            String     estado = obj.getString("estado");

            for (Actuador a : sistema.getActuadores()) {
                if (a.getID().equals(id)) {
                     a.ejecutarAccion(estado);
                }
            }
        }
    }

    private static void cargarReglas(JSONObject raiz, SmartTecnoHouse sistema) {
        if (!raiz.has("reglas")) return;

        JSONArray arr = raiz.getJSONArray("reglas");
        if (arr.isEmpty()) return;

        sistema.limpiarReglas();
        for (int i = 0; i < arr.length(); i++) {
            Regla regla = crearRegla(arr.getString(i));
            if (regla != null) sistema.addRegla(regla);
        }
    }

    /** Patrón Factory . **/
    private static Regla crearRegla(String nombre) {
        switch (nombre) {
            case "R1. Ventilación Confortable": return new ReglaVentilacionConfortable();
            case "R2. Iluminación Automática":  return new ReglaIluminacionAutomatica();
            default:                            return null;
        }
    }
}
