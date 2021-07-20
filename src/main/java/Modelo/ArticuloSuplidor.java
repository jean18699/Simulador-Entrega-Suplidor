package Modelo;


public class ArticuloSuplidor {

    private long codigoSuplidor;
    private String codigoArticulo;
    private long tiempoEntrega;
    private float precioCompra;

    public ArticuloSuplidor(long codigoSuplidor, String codigoArticulo, long tiempoEntrega, float precioCompra) {
        this.codigoSuplidor = codigoSuplidor;
        this.codigoArticulo = codigoArticulo;
        this.tiempoEntrega = tiempoEntrega;
        this.precioCompra = precioCompra;
    }

    public long getCodigoSuplidor() {
        return codigoSuplidor;
    }

    public void setCodigoSuplidor(long codigoSuplidor) {
        this.codigoSuplidor = codigoSuplidor;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public long getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(long tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }
}
