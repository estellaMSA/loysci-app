package br.com.monitoratec.loysci_android.model;


public class ChallengeGame  {
    private String idMisionJuego;
    private String indTipoPremio;
    private int cantMetrica;
    private Metric metrica;
    private Reward premio;
    private boolean isGanar;

    public boolean isGanar() {
        return isGanar;
    }

    public void setGanar(boolean ganar) {
        isGanar = ganar;
    }

    public String getIdMisionJuego() {
        return idMisionJuego;
    }

    public void setIdMisionJuego(String idMisionJuego) {
        this.idMisionJuego = idMisionJuego;
    }

    public String getIndTipoPremio() {
        return indTipoPremio;
    }

    public void setIndTipoPremio(String indTipoPremio) {
        this.indTipoPremio = indTipoPremio;
    }

    public int getCantMetrica() {
        return cantMetrica;
    }

    public void setCantMetrica(int cantMetrica) {
        this.cantMetrica = cantMetrica;
    }

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }

    public Reward getPremio() {
        return premio;
    }

    public void setPremio(Reward idPremio) {
        this.premio = premio;
    }

}
