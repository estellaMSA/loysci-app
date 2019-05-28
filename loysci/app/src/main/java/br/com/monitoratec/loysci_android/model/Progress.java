package br.com.monitoratec.loysci_android.model;

import java.util.List;



public class Progress {
    private float progreso;
    //@TODO total? nivel en el que esta?
    private int tope;
    private Level nivelActual;
    private Metric metrica;
    private List<Level> niveles;

    public int getTope() {
        return tope;
    }

    public void setTope(int tope) {
        this.tope = tope;
    }

    public Level getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Level nivelActual) {
        this.nivelActual = nivelActual;
    }

    public float getProgreso() {
        return progreso;
    }

    public void setProgreso(float progreso) {
        this.progreso = progreso;
    }

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }

    public List<Level> getNiveles() {
        return niveles;
    }

    public void setNiveles(List<Level> niveles) {
        this.niveles = niveles;
    }
}

