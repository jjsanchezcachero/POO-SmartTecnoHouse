package modelo;

/** Actuador de ventilador. Acepta las acciones "OFF", "LOW" y "HIGH".
 * "LOW" y "HIGH" suponen un "ON" implícito */
public class ActuadorVentilador extends Actuador {

    private static final String[] ACCIONES = {"OFF", "LOW", "HIGH"};

    public ActuadorVentilador() {
        super("fan", "Ventilador", "OFF");
    }

    @Override
    public void ejecutarAccion(String accion) throws IllegalArgumentException {
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
