package Modelo;

import java.time.LocalDate;
import java.util.List;

public class OrdenCompra {

    private static long contador;
    private long codigoOrden;
    private long codigoSuplidor;
    private LocalDate fechaOrden;
    private LocalDate fechaRequerida;
    private float montoTotal;
    private List<ArticuloOrdenado> articulos;

    public OrdenCompra(long codigoSuplidor, LocalDate fechaRequerida, List<ArticuloOrdenado> articulos) {
        contador += 1;
        this.codigoOrden = contador;
        this.codigoSuplidor = codigoSuplidor;
        this.fechaRequerida = fechaRequerida;
        this.articulos = articulos;

        for(int i = 0; i < articulos.size(); i++)
        {
            montoTotal += articulos.get(i).getPrecioCompra();
        }

    }

    public void agregarArticulo(ArticuloOrdenado articulo)
    {
        this.articulos.add(articulo);
    }

    public void quitarArticulo(ArticuloOrdenado articulo)
    {
        for(int i = 0; i < articulos.size(); i++)
        {
            if(articulos.get(i).getCodigoArticulo().equalsIgnoreCase(articulo.getCodigoArticulo()))
            {
                articulos.remove(i);
            }
        }

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

    public List<ArticuloOrdenado> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloOrdenado> articulos) {
        this.articulos = articulos;
    }
}
