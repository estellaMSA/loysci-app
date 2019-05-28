package br.com.monitoratec.loysci_android.model;


public class Product {
    private String idProducto;
    private String nombre;
    private String imagenArte;
    private String descripcion;
    private String sku;
    private float precio;
    private String indImpuesto;
    private float montoEntrega;
    private String indEntrega;
    private String fechaArchivado;
    private String encabezadoArte;
    private String subencabezadoArte;
    private String detalleArte;
    private long cantMinMetrica;

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenArte() {
        return imagenArte;
    }

    public void setImagenArte(String imagenArte) {
        this.imagenArte = imagenArte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getIndImpuesto() {
        return indImpuesto;
    }

    public void setIndImpuesto(String indImpuesto) {
        this.indImpuesto = indImpuesto;
    }

    public float getMontoEntrega() {
        return montoEntrega;
    }

    public void setMontoEntrega(float montoEntrega) {
        this.montoEntrega = montoEntrega;
    }

    public String getIndEntrega() {
        return indEntrega;
    }

    public void setIndEntrega(String indEntrega) {
        this.indEntrega = indEntrega;
    }

    public String getFechaArchivado() {
        return fechaArchivado;
    }

    public void setFechaArchivado(String fechaArchivado) {
        this.fechaArchivado = fechaArchivado;
    }

    public String getEncabezadoArte() {
        return encabezadoArte;
    }

    public void setEncabezadoArte(String encabezadoArte) {
        this.encabezadoArte = encabezadoArte;
    }

    public String getSubencabezadoArte() {
        return subencabezadoArte;
    }

    public void setSubencabezadoArte(String subencabezadoArte) {
        this.subencabezadoArte = subencabezadoArte;
    }

    public String getDetalleArte() {
        return detalleArte;
    }

    public void setDetalleArte(String detalleArte) {
        this.detalleArte = detalleArte;
    }

    public long getCantMinMetrica() {
        return cantMinMetrica;
    }

    public void setCantMinMetrica(long cantMinMetrica) {
        this.cantMinMetrica = cantMinMetrica;
    }
}
