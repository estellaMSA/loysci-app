package br.com.monitoratec.loysci_android.model;

import java.util.List;

public class Store {
    private String idUbicacion;
    private String nombre;
    private String indDirPais;
    private String indDirEstado;
    private String indDirCiudad;
    private String direccion;
    private String horarioAtencion;
    private String telefono;
    private float dirLat;
    private float dirLng;
    private List<Zone> zonaList;

    public String getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(String idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndDirPais() {
        return indDirPais;
    }

    public void setIndDirPais(String indDirPais) {
        this.indDirPais = indDirPais;
    }

    public String getIndDirEstado() {
        return indDirEstado;
    }

    public void setIndDirEstado(String indDirEstado) {
        this.indDirEstado = indDirEstado;
    }

    public String getIndDirCiudad() {
        return indDirCiudad;
    }

    public void setIndDirCiudad(String indDirCiudad) {
        this.indDirCiudad = indDirCiudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getDirLat() {
        return dirLat;
    }

    public void setDirLat(float dirLat) {
        this.dirLat = dirLat;
    }

    public float getDirLng() {
        return dirLng;
    }

    public void setDirLng(float dirLng) {
        this.dirLng = dirLng;
    }

    public List<Zone> getZonaList() {
        return zonaList;
    }

    public void setZonaList(List<Zone> zonaList) {
        this.zonaList = zonaList;
    }
}
