package br.com.loysciapp.loysci_android.model;


public class Level {
    private String idNivel;
    private String nombre;
    private String descripcion;
    private float metricaInicial;

    public String getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(String idNivel) {
        this.idNivel = idNivel;
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

    public float getMetricaInicial() {
        return metricaInicial;
    }

    public void setMetricaInicial(float metricaInicial) {
        this.metricaInicial = metricaInicial;
    }
}
