package modelo;

import java.util.Random;

/**
 * Sensor PIR de presencia. Devuelve "DETECTADO" / "NO_DETECTADO" en lugar
 * de un valor numérico continuo, por lo que sobreescribe {@link #getEstadoActual()}.
 */
public class SensorPresencia extends Sensor {

    private static final Random random = new Random();

    public SensorPresencia() {
        super("pir", "Sensor de Presencia", "");
    }

    @Override
    public void actualizarValor() {
        setValor(random.nextBoolean() ? "ON" : "OFF");
    }

    @Override
    public String getEstadoActual() {
        return getValor();
    }
}

