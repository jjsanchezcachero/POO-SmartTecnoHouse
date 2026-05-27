package modelo;

/** Actuador de ventilador. Acepta las acciones "OFF", "LOW", "MEDIUM" y "HIGH".
 * "LOW", "MEDIUM" y "HIGH" suponen un "ON" implícito */
public class ActuadorVentilador extends Actuador {

    private static final String[] ACCIONES = {"OFF", "LOW", "MEDIUM", "HIGH"};

    public ActuadorVentilador() {
        super("fan", "Ventilador", "OFF");
    }

    @Override
    public void ejecutarAccion(String accion) {
        if (esAccionValida(accion)) {
            setEstadoActual(accion);
        } else {
            throw new IllegalArgumentException("Acción no válida para Ventilador: " + accion);
        }
    }

    @Override
    public String[] getAccionesPosibles() {
        return ACCIONES;
    }
}
