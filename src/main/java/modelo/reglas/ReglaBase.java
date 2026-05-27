package modelo.reglas;

import modelo.IDispositivo;

import java.util.List;

/**
 * Clase base abstracta para todas las reglas de automatización.
 * Proporciona el helper {@link #buscar} para localizar un dispositivo por ID
 */
public abstract class ReglaBase implements Regla {

    /**
     * Busca el primer dispositivo de {@code lista} cuyo ID coincida con {@code id}.
     *
     * @return el dispositivo encontrado, o {@code null} si no existe
     */
    protected <T extends IDispositivo> T buscar(List<T> lista, String id) {
        return lista.stream().filter(d -> d.getID().equals(id)).findFirst().orElse(null);
    }
}
