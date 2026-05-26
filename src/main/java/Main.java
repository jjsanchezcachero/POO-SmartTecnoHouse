import modelo.*;

import java.util.List;

// Main de ejemplo para probar la instaciación de sensores y polimorfisemo
public class Main {
    public static void main(String[] args) {



        // --- Sensores ---
        List<modelo.Sensor> sensores = List.of(
                new modelo.SensorTemperatura(),
                new modelo.SensorLuz(),
                new modelo.SensorPresencia(),
                new modelo.SensorHumedad()
        );

        System.out.println("=== SENSORES ===");
        for (modelo.Sensor s : sensores) {
            s.actualizarValor();
            System.out.printf("%-30s | getValor: %-20s | getEstadoActual: %s%n",
                    s.getNombre(), s.getValor(), s.getEstadoActual());
        }

        // --- Polimorfismo: List<IDispositivo> ---
        System.out.println("\n=== POLIMORFISMO (List<IDispositivo>) ===");
        List<modelo.IDispositivo> dispositivos = List.of(
                new modelo.SensorTemperatura(),
                new modelo.SensorPresencia()
        );

        for (modelo.IDispositivo d : dispositivos) {
            if (d instanceof modelo.Sensor) ((modelo.Sensor) d).actualizarValor();
            System.out.printf("[%s] %-30s -> %s%n",
                    d.getID(), d.getNombre(), d.getEstadoActual());
        }
    }
}
