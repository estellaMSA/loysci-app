package br.com.loysciapp.loysci_android.model;

import java.util.List;


public class Badge {
    private String idInsignia;
    private String nombreInsignia;
    private String descripcion;
    private String imagen;
    private boolean obtenida;
    private String tipo;
    private List<BadgeLevel> nivelesInsigniaList;

    public String getIdInsignia() {
        return idInsignia;
    }

    public void setIdInsignia(String idInsignia) {
        this.idInsignia = idInsignia;
    }

    public String getNombreInsignia() {
        return nombreInsignia;
    }

    public void setNombreInsignia(String nombreInsignia) {
        this.nombreInsignia = nombreInsignia;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<BadgeLevel> getNivelesInsigniaList() {
        return nivelesInsigniaList;
    }

    public void setNivelesInsigniaList(List<BadgeLevel> nivelesInsigniaList) {
        this.nivelesInsigniaList = nivelesInsigniaList;
    }
}
