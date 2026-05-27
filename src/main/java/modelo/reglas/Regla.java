package modelo.reglas;

import modelo.Actuador;
import modelo.Sensor;

import java.util.List;

/**
 * Interfaz del patrón Strategy para el sistema de reglas de automatización.
 */
public interface Regla {

    /** @return nombre descriptivo de la regla */
    String getNombre();

    /**
     * Evalúa los sensores y actúa sobre los actuadores según la lógica de esta regla.
     *
     * @param sensores   lista de sensores disponibles en el sistema
     * @param actuadores lista de actuadores disponibles en el sistema
     */
    void aplicar(List<Sensor> sensores, List<Actuador> actuadores);
}
