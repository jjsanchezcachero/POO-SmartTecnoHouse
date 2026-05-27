package modelo;

/** Actuador de bombilla. Acepta las acciones "ON" y "OFF". */
public class ActuadorBombilla extends Actuador {

    private static final String[] ACCIONES = {"ON", "OFF"};

    public ActuadorBombilla() {
        super("bulb", "Bombilla", "OFF");
    }

    @Override
    public void ejecutarAccion(String accion) {
        if (esAccionValida(accion)) {
            setEstadoActual(accion);
        } else {
            throw new IllegalArgumentException("Acción no válida para Bombilla: " + accion);
        }
    }

    @Override
    public String[] getAccionesPosibles() {
        return ACCIONES;
    }
}
