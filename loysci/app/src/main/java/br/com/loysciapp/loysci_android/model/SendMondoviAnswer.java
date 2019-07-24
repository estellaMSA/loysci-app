package br.com.loysciapp.loysci_android.model;


public class SendMondoviAnswer{
    private String idJuego;
    private double metricaApostada;
    private int pronostico;

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public double getMetricaApostada() {
        return metricaApostada;
    }

    public void setMetricaApostada(double metricaApostada) {
        this.metricaApostada = metricaApostada;
    }

    public int getPronostico() {
        return pronostico;
    }

    public void setPronostico(int pronostico) {
        this.pronostico = pronostico;
    }
}
