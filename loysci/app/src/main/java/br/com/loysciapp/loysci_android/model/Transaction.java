package br.com.loysciapp.loysci_android.model;


public class Transaction {
    private long totalComprado;
    private String lugarCompra;

    public long getTotalComprado() {
        return totalComprado;
    }

    public void setTotalComprado(long totalComprado) {
        this.totalComprado = totalComprado;
    }

    public String getLugarCompra() {
        return lugarCompra;
    }

    public void setLugarCompra(String lugarCompra) {
        this.lugarCompra = lugarCompra;
    }
}
