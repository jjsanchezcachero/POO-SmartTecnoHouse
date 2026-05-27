import modelo.*;
import modelo.reglas.Regla;
import modelo.reglas.ReglaIluminacionAutomatica;
import modelo.reglas.ReglaVentilacionConfortable;

import java.util.List;

// Main de ejemplo para probar la instanciación de sensores y polimorfismo
public class Main {
    public static void main(String[] args) {

        // Se sustityue esta instanciación por el uso de la clase Singleton SmartTecnoHouse
        System.out.println("\n=== SmartTecnoHouse (Singleton) ===");
        SmartTecnoHouse sistema = SmartTecnoHouse.getInstance();

        System.out.println("-- Ciclo automático 1 --");
        sistema.procesarCiclo();

        System.out.println("\nEstado sensores:");
        for (Sensor s : sistema.getSensores()) {
            System.out.printf("  %-25s -> %s%n", s.getNombre(), s.getEstadoActual());
        }
        System.out.println("Estado actuadores:");
        for (Actuador a : sistema.getActuadores()) {
            System.out.printf("  %-25s -> %s%n", a.getNombre(), a.getEstadoActual());
        }

        System.out.println("\n-- Acción manual --");
        sistema.ejecutarAccionManual("bulb", "ON");
        sistema.ejecutarAccionManual("plug", "ON");

        System.out.println("\n-- Ciclo automático 2 --");
        sistema.procesarCiclo();

        System.out.println("\nLog guardado en: " + ServicioLog.FICHERO);

        /*
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
            System.out.printf(
                    "Excepción capturada: %s Acciones posibles para %s %s%n",
                    e.getMessage(),
                    ventilador.getNombre(),
                    String.join(", ", ventilador.getAccionesPosibles())
            );
        }

        // --- Patrón Strategy: sistema de reglas ---
        System.out.println("\n=== PATRÓN STRATEGY: REGLAS ===");

        // Forzar valores conocidos para demostrar cada regla
        // Se accede por posición de array sólo para comprobar y con motivo del test
        sensores.get(0).actualizarValor(); // temperatura aleatoria
        sensores.get(2).actualizarValor(); // presencia aleatoria

        System.out.println("Estado sensores antes de aplicar reglas:");
        for (Sensor s : sensores) {
            System.out.printf("  %-25s -> %s%n", s.getNombre(), s.getEstadoActual());
        }

        List<Regla> reglas = List.of(
                new ReglaVentilacionConfortable(),
                new ReglaIluminacionAutomatica()
        );

        System.out.println("\nAplicando reglas...");
        for (Regla r : reglas) {
            r.aplicar(sensores, actuadores);
            System.out.printf("  [%s] aplicada%n", r.getNombre());
        }

        System.out.println("\nEstado actuadores tras aplicar reglas:");
        for (Actuador a : actuadores) {
            System.out.printf("  %-30s -> %s%n", a.getNombre(), a.getEstadoActual());
        }
        */
    }
}
