package Modelo;

public class Almacen {

    private long codigoAlmacen;
    private long balanceActual;

    public Almacen(long codigoAlmacen,long balanceInicial)
    {
        this.codigoAlmacen = codigoAlmacen;
        this.balanceActual = balanceInicial;

    }

    public void addBalance(long balance)
    {
        balanceActual += balance;
    }

    public long getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(long codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public long getBalanceActual() {
        return balanceActual;
    }

    public void setBalanceActual(long balanceActual) {
        this.balanceActual = balanceActual;
    }
}
