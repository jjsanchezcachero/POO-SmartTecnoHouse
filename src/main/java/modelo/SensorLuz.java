package modelo;

import java.util.Random;

/** Sensor de luminosidad. Simula lecturas entre 0 y 1000 lux. */
public class SensorLuz extends Sensor {

    private static final Random random = new Random();

    public SensorLuz() {
        super("light", "Sensor de Luz", "lux");
    }

    @Override
    public void actualizarValor() {
        setValor(String.valueOf(random.nextDouble() * 1000.0));
    }
}
