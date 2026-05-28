package modelo;

import java.util.Random;

/** Sensor de temperatura. Simula lecturas entre 15 °C y 35 °C. */
public class SensorTemperatura extends Sensor {

    private static final Random random = new Random();

    public SensorTemperatura() {
        super("temp", "Sensor de Temperatura", "°C");
    }

    @Override
    public void actualizarValor() {
        // Valor aleatorio
        setValor(String.valueOf(15.0 + random.nextDouble() * 20.0));
    }
}
