package br.com.loysciapp.loysci_android.model;


import java.util.List;

public class TimeGame {
    private String idJornada;
    private String descripcion;
    private List<GameLeague> juegos;

    public String getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(String idJornada) {
        this.idJornada = idJornada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<GameLeague> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<GameLeague> juegos) {
        this.juegos = juegos;
    }
}
