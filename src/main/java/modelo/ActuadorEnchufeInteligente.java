package modelo;

/** Actuador de enchufe inteligente (extensión adicional). Acepta "ON" y "OFF". */
public class ActuadorEnchufeInteligente extends Actuador {

    private static final String[] ACCIONES = {"ON", "OFF"};

    public ActuadorEnchufeInteligente() {
        super("plug", "Enchufe Inteligente", "OFF");
    }

    @Override
    public void ejecutarAccion(String accion) {
        if (esAccionValida(accion)) {
            setEstadoActual(accion);
        } else {
            throw new IllegalArgumentException("Acción no válida para Enchufe Inteligente: " + accion);
        }
    }

    @Override
    public String[] getAccionesPosibles() {
        return ACCIONES;
    }
}
