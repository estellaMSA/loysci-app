package br.com.monitoratec.loysci_android.model;

import java.util.List;


public class Tournament {
    private String idTorneo;
    private String descripcion;
    private List<TimeGame> juegos;

    public String getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(String idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<TimeGame> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<TimeGame> juegos) {
        this.juegos = juegos;
    }
}
