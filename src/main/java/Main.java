import modelo.*;

import java.util.List;

// Main de ejemplo para probar la instanciación de sensores y polimorfismo
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

        // --- Actuadores ---
        // --- Actuadores ---
        ActuadorBombilla bombilla = new ActuadorBombilla();
        ActuadorVentilador ventilador = new ActuadorVentilador();
        ActuadorEnchufeInteligente enchufe = new ActuadorEnchufeInteligente();

        List<Actuador> actuadores = List.of(bombilla, ventilador, enchufe);

        System.out.println("\n=== ACTUADORES (acciones válidas) ===");
        bombilla.ejecutarAccion("ON");
        bombilla.ejecutarAccion(bombilla.getAccionesPosibles()[1]);
        ventilador.ejecutarAccion("HIGH");
        ventilador.ejecutarAccion(ventilador.getAccionesPosibles()[0]);
        enchufe.ejecutarAccion("ON");
        enchufe.ejecutarAccion(enchufe.getAccionesPosibles()[1]);

        for (Actuador a : actuadores) {
            System.out.printf("%-30s | getEstadoActual: %-10s | acciones: %s%n",
                    a.getNombre(), a.getEstadoActual(),
                    String.join(", ", a.getAccionesPosibles()));
        }

        // --- Prueba de IllegalArgumentException con acción inválida ---
        System.out.println("\n=== ACCIÓN INVÁLIDA ===");
        try {
            ventilador.ejecutarAccion("TURBO");
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada: " + e.getMessage() +
                    " Acciones posibles para " + ventilador.getNombre() + " " +
                    String.join(", ", ventilador.getAccionesPosibles()));
        }
    }
}
