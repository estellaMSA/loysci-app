package br.com.loysciapp.loysci_android.model;


import java.util.List;

public class League {
    private String idLiga;
    private String descripcion;
    private String imagen;
    private List<Tournament> torneos;

    public String getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(String idLiga) {
        this.idLiga = idLiga;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Tournament> getTorneos() {
        return torneos;
    }

    public void setTorneos(List<Tournament> torneos) {
        this.torneos = torneos;
    }
}
