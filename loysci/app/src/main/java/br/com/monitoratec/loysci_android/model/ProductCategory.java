package br.com.monitoratec.loysci_android.model;


import java.util.List;

public class ProductCategory  {
    private String idCategoria;
    private String nombre;
    private String imagen;
    private String descripcion;
    private List<ProductSubCategory> subcategorias;

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ProductSubCategory> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<ProductSubCategory> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
