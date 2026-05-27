package modelo.reglas;

import modelo.Actuador;
import modelo.Sensor;

import java.util.List;

/**
 * R1. Ventilación Confortable.
 * Controla el ventilador según la temperatura ambiente:
 * <ul>
 *   <li>>= 27 °C → HIGH</li>
 *   <li>21–27 °C → LOW</li>
 *   <li><= 21 °C → OFF</li>
 * </ul>
 */
public class ReglaVentilacionConfortable extends ReglaBase {

    private static final String NOMBRE_REGLA = "R1. Ventilación Confortable";
    private static final double TEMP_ALTA  = 27.0;
    private static final double TEMP_MEDIA = 21.0;


    @Override
    public String getNombre() { return NOMBRE_REGLA; }

    @Override
    public void aplicar(List<Sensor> sensores, List<Actuador> actuadores) {
        Sensor sTemp = buscar(sensores, "temp");
        Actuador fan  = buscar(actuadores, "fan");
        if (sTemp == null || fan == null) return;

        double temp = Double.parseDouble(sTemp.getValor());
        if (temp >= TEMP_ALTA) {
            fan.ejecutarAccion("HIGH");
        } else if (temp >= TEMP_MEDIA) {
            fan.ejecutarAccion("LOW");
        } else {
            fan.ejecutarAccion("OFF");
        }
    }

}
