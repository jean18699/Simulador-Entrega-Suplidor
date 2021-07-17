package Modelo;

public class ArticuloOrdenado {

    private String codigoArticulo;
    private long cantidadOrdenada;
    private float precioCompra;

    public ArticuloOrdenado() {

    }

    public ArticuloOrdenado(String codigoArticulo, long cantidadOrdenada, float precioCompra) {
        this.codigoArticulo = codigoArticulo;
        this.cantidadOrdenada = cantidadOrdenada;
        this.precioCompra = precioCompra;
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
}
