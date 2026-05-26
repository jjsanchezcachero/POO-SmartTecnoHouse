package modelo;

/**
 * Clase base abstracta para todos los sensores.
 */
public abstract class Sensor implements IDispositivo {

    private final String id;
    private final String nombre;
    private final String unidad;
    private String valor;

    protected Sensor(String id, String nombre, String unidad) {
        this.id = id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.valor = "";
    }

    /**
     * Actualiza el valor del sensor.
     * Cada subclase implementa su propia estrategia de lectura.
     */
    public abstract void actualizarValor();

    /** @return último valor leído */
    public String getValor() {
        return valor;
    }

    /** Permite a las subclases actualizar el valor encapsulado. */
    protected void setValor(String valor) {
        this.valor = valor;
    }

    protected String getUnidad() {
        return unidad;
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
        return valor + (unidad.isEmpty() ? "" : " " + unidad);
    }
}
