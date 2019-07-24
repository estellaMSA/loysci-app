package br.com.loysciapp.loysci_android.model;


public class BadgeLevel {
    private String idNivel;
    private String nombreNivel;
    private String descripcion;
    private String imagen;
    private boolean obtenida;

    public String getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(String idNivel) {
        this.idNivel = idNivel;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        this.nombreNivel = nombreNivel;
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

    public boolean isObtenida() {
        return obtenida;
    }

    public void setObtenida(boolean obtenida) {
        this.obtenida = obtenida;
    }
}
