package Modelo;

public class Movimiento {

    private static long contador;
    private long codigoMovimiento;
    private long codigoAlmacen;
    private String codigoArticulo;
    private String tipoMovimiento;
    private long cantidad;

    public Movimiento(String codigoArticulo, long codigoAlmacen, String tipoMovimiento, long cantidad) {
        contador += 1;
        this.codigoMovimiento = contador;
        this.codigoAlmacen = codigoAlmacen;
        this.codigoArticulo = codigoArticulo;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
    }

    public Movimiento(long codigoMovimiento, String codigoArticulo, long codigoAlmacen, String tipoMovimiento, long cantidad)
    {
        this.codigoMovimiento = codigoMovimiento;
        this.codigoAlmacen = codigoAlmacen;
        this.codigoArticulo = codigoArticulo;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
    }

    public long getCodigoMovimiento() {
        return codigoMovimiento;
    }

    public void setCodigoMovimiento(long codigoMovimiento) {
        this.codigoMovimiento = codigoMovimiento;
    }

    public long getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(long codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }
}
