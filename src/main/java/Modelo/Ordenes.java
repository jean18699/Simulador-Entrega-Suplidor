package Modelo;

import java.util.Date;
import java.util.List;

public class Ordenes {

    private long codigoOrden;
    private long codigoSuplidor;
    private Date fechaOrden;
    private Date fechaRequerida;
    private float montoTotal;
    private List<Articulo> articulos;

    public Ordenes(long codigoOrden, long codigoSuplidor, Date fechaOrden, Date fechaRequerida, float montoTotal, List<Articulo> articulos) {
        this.codigoOrden = codigoOrden;
        this.codigoSuplidor = codigoSuplidor;
        this.fechaOrden = fechaOrden;
        this.fechaRequerida = fechaRequerida;
        this.montoTotal = montoTotal;
        this.articulos = articulos;
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

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Date getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(Date fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
}
