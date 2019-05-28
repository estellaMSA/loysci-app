package br.com.monitoratec.loysci_android.model;



public class Points { //@TODO this object should be part of Profile because of the lacking ID
    private float totalAcumulado;
    private float disponible;
    private float vencido;
    private float redimido;
    private Metric metrica;

    public float getTotalAcumulado() {
        return totalAcumulado;
    }

    public void setTotalAcumulado(float totalAcumulado) {
        this.totalAcumulado = totalAcumulado;
    }

    public float getDisponible() {
        return disponible;
    }

    public void setDisponible(float disponible) {
        this.disponible = disponible;
    }

    public float getVencido() {
        return vencido;
    }

    public void setVencido(float vencido) {
        this.vencido = vencido;
    }

    public float getRedimido() {
        return redimido;
    }

    public void setRedimido(float redimido) {
        this.redimido = redimido;
    }

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }
}
