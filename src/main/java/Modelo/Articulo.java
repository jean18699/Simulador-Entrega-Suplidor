package Modelo;

import com.mongodb.client.MongoDatabase;

public class Articulo {


    private String codigoArticulo;
    private Almacen almacen;
    private String descripcion;

    public Articulo(String codigoArticulo, long codigoAlmacen, long balanceInicial, String descripcion){

        this.codigoArticulo = codigoArticulo;
        this.descripcion = descripcion;
        this.almacen = new Almacen(codigoAlmacen,balanceInicial);
    }

    public Articulo()
    {}

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }
}
