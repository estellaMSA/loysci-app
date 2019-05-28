package br.com.monitoratec.loysci_android.model;

import java.util.List;


public class Leaderboard {
    private String idTabla;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Metric metrica;
    private List<Top> top;

    public String getIdTabla() {
        return idTabla;
    }

    public void setIdTabla(String idTabla) {
        this.idTabla = idTabla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }

    public List<Top> getTop() {
        return top;
    }

    public void setTop(List<Top> top) {
        this.top = top;
    }
}
