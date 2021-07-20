package Modelo;

public class ConsumoDiario {

    private String codigoArticulo;
    private long codigoAlmacen;
    private long promedioConsumo;

    public ConsumoDiario(String codigoArticulo, long codigoAlmacen, long promedioConsumo) {
        this.codigoArticulo = codigoArticulo;
        this.codigoAlmacen = codigoAlmacen;
        this.promedioConsumo = promedioConsumo;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public long getPromedioConsumo() {
        return promedioConsumo;
    }

    public void setPromedioConsumo(long promedioConsumo) {
        this.promedioConsumo = promedioConsumo;
    }

    public long getCodigoAlmacen() {
        return codigoAlmacen;
    }
}
