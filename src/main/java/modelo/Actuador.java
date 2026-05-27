package modelo;

import java.util.Arrays;

/**
 * Clase base abstracta para todos los actuadores.
 * Gestiona el estado actual y obliga a cada subclase a declarar sus acciones
 * válidas y a implementar la lógica de cambio de estado.
 */
public abstract class Actuador implements IDispositivo {

    private final String id;
    private final String nombre;
    private String estadoActual;

    protected Actuador(String id, String nombre, String estadoInicial) {
        this.id = id;
        this.nombre = nombre;
        this.estadoActual = estadoInicial;
    }

    /**
     * Valida y aplica la acción al actuador.
     * @param accion debe ser uno de los valores devueltos por {@link #getAccionesPosibles()}
     * @throws IllegalArgumentException si la acción no es válida para este actuador
     */
    public abstract void ejecutarAccion(String accion);

    /** @return array con las acciones válidas para este actuador (ej. ["ON", "OFF"]) */
    public abstract String[] getAccionesPosibles();

    /** Comprueba si una acción pertenece al conjunto de acciones válidas. */
    protected boolean esAccionValida(String accion) {
        return Arrays.asList(getAccionesPosibles()).contains(accion);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getEstadoActual() {
        return estadoActual;
    }

    protected void setEstadoActual(String estado) {
        this.estadoActual = estado;
    }
}

