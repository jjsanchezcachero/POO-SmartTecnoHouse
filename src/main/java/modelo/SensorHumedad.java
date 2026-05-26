package modelo;

import java.util.Random;

/** Sensor de humedad relativa (extensión adicional). Simula lecturas entre 20 % y 80 %. */
public class SensorHumedad extends Sensor {

    private static final Random random = new Random();

    public SensorHumedad() {
        super("hum", "Sensor de Humedad", "%");
    }

    @Override
    public void actualizarValor() {
        setValor(String.valueOf(20.0 + random.nextDouble() * 60.0));
    }
}