package Modelo;

import java.time.LocalDate;

public class OrdenCompra {

    private static long contador;
    private long codigoOrden;
    private long codigoSuplidor;
    private LocalDate fechaOrden;
    private LocalDate fechaRequerida;
    private float montoTotal;
    private ArticuloOrdenado articuloOrdenado;

    public OrdenCompra(long codigoOrden, long codigoSuplidor) {
        this.codigoOrden = codigoOrden;
        this.codigoSuplidor = codigoSuplidor;
    }

    public OrdenCompra(long codigoSuplidor, LocalDate fechaRequerida, ArticuloOrdenado articulo) {
        contador += 1;
        this.codigoOrden = contador;
        this.codigoSuplidor = codigoSuplidor;
        this.fechaRequerida = fechaRequerida;
        this.articuloOrdenado = articulo;
        this.montoTotal = articulo.getPrecioCompra();
        this.fechaOrden = LocalDate.now();
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

    public ArticuloOrdenado getArticuloOrdenado() {
        return articuloOrdenado;
    }

    public void setArticuloOrdenado(ArticuloOrdenado articuloOrdenado) {
        this.articuloOrdenado = articuloOrdenado;
    }



}
