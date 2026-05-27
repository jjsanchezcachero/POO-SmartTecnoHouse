package modelo.reglas;

import modelo.Actuador;
import modelo.Sensor;

import java.util.List;

/**
 * R2. Iluminación Automática.
 * Enciende la bombilla cuando el sensor de presencia detecta actividad.
 * La apaga en cualquier otro caso.
 */
public class ReglaIluminacionAutomatica extends ReglaBase {

    private static final String NOMBRE_REGLA = "R2. Iluminación Automática";

    @Override
    public String getNombre() {
        return NOMBRE_REGLA;
    }

    @Override
    public void aplicar(List<Sensor> sensores, List<Actuador> actuadores) {
        Sensor sPir   = buscar(sensores, "pir");
        Actuador bulb = buscar(actuadores, "bulb");
        if (bulb == null) return;

        boolean hayPresencia = sPir != null && "ON".equals(sPir.getValor());
        bulb.ejecutarAccion(hayPresencia ? "ON" : "OFF");
    }

}