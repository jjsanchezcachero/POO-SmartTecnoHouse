package modelo;

/**
 * Contrato común para todo dispositivo del sistema domótico.
 */
public interface IDispositivo {

    /** @return identificador del dispositivo */
    String getID();

    /** @return nombre del dispositivo  */
    String getNombre();

    /** @return resumen del estado actual */
    String getEstadoActual();
}
