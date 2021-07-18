package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para recibir las ordenes desde la base de datos, ya procesadas
 */
public class OrdenCompraTotal {

    private static long contador;
    private long codigoOrden;
    private long codigoSuplidor;
    private LocalDate fechaOrden;
    private LocalDate fechaRequerida;
    private float montoTotal;
    private List<ArticuloOrdenado> articulosOrdenados;

    public OrdenCompraTotal(long codigoOrden, long codigoSuplidor, float montoTotal, LocalDate fechaOrden, List<ArticuloOrdenado> articulo) {
        contador += 1;
        this.codigoOrden = contador;
        this.codigoSuplidor = codigoSuplidor;
        this.articulosOrdenados = new ArrayList<>();
        this.montoTotal = montoTotal;
        this.fechaOrden = fechaOrden;
    }

    public long getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(long codigoOrden) {
        this.codigoOrden = codigoOrden;
    }

    public long getCodigoSuplidor() {
        return codigoSuplidor;
    }

    public void setCodigoSuplidor(long codigoSuplidor) {
        this.codigoSuplidor = codigoSuplidor;
    }

    public LocalDate getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDate fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public LocalDate getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(LocalDate fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<ArticuloOrdenado> getArticuloOrdenado() {
        return articulosOrdenados;
    }

    public void setArticuloOrdenado(List<ArticuloOrdenado> articuloOrdenado) {
        this.articulosOrdenados = articuloOrdenado;
    }



}
