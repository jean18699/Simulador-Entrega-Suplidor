package Modelo;

public class ArticuloOrdenado {

    private String codigoArticulo;
    private long cantidadOrdenada;
    private long codigoAlmacen;
    private float precioCompra;
    private boolean status;

    public ArticuloOrdenado() {

    }

    public ArticuloOrdenado(String codigoArticulo, long cantidadOrdenada, long codigoAlmacen) {
        this.codigoArticulo = codigoArticulo;
        this.cantidadOrdenada = cantidadOrdenada;
        this.codigoAlmacen = codigoAlmacen;
        this.status = false;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public long getCantidadOrdenada() {
        return cantidadOrdenada;
    }

    public void setCantidadOrdenada(long cantidadOrdenada) {
        this.cantidadOrdenada = cantidadOrdenada;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public long getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(long codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }
}
