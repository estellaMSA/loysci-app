package br.com.loysciapp.loysci_android.model;



public class GameLeagueUno {
    private String idJuego;
    private Boolean indEstado;
    private Team idEquipo1;
    private Team idEquipo2;
    private long fechaLimite;
    private long metricaApostada;
    private long pronostico;

    public String getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
    }

    public Boolean getIndEstado() {
        return indEstado;
    }

    public void setIndEstado(Boolean indEstado) {
        this.indEstado = indEstado;
    }

    public Team getIdEquipo1() {
        return idEquipo1;
    }

    public void setIdEquipo1(Team idEquipo1) {
        this.idEquipo1 = idEquipo1;
    }

    public Team getIdEquipo2() {
        return idEquipo2;
    }

    public void setIdEquipo2(Team idEquipo2) {
        this.idEquipo2 = idEquipo2;
    }

    public long getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(long fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public long getMetricaApostada() {
        return metricaApostada;
    }

    public void setMetricaApostada(long metricaApostada) {
        this.metricaApostada = metricaApostada;
    }

    public long getPronostico() {
        return pronostico;
    }

    public void setPronostico(long pronostico) {
        this.pronostico = pronostico;
    }
}
