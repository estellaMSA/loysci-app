package br.com.monitoratec.loysci_android.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class RewardRedeem {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            TYPE_PRODUCT,
            TYPE_CERTIFICATE
    })
    public @interface RewardType {
    }

    public static final String TYPE_PRODUCT = "P";
    public static final String TYPE_CERTIFICATE = "C";

    public static final String IS_AVAILABLE = "D";
    private String idPremio;
    private String nombre;
    @Reward.RewardType
    private String indTipoPremio;
    private boolean indEnvio;
    private String descripcion;
    private long valorMoneda;
    private long valorEfectivo;
    private String imagenArte;
    private String trackingCode;
    private String sku;
    private String encabezadoArte;
    private String subencabezadoArte;
    private String detalleArte;
    private long cantMinAcumulado;
    private String codigoCertificado;
    private String fechaRedencion;
    private String indEstado;
    private Metric metrica;
    private String fechaExpiracion;

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(String idPremio) {
        this.idPremio = idPremio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Reward.RewardType
    public String getIndTipoPremio() {
        return indTipoPremio;
    }

    public void setIndTipoPremio(@Reward.RewardType String indTipoPremio) {
        this.indTipoPremio = indTipoPremio;
    }

    public boolean isIndEnvio() {
        return indEnvio;
    }

    public void setIndEnvio(boolean indEnvio) {
        this.indEnvio = indEnvio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getValorMoneda() {
        return valorMoneda;
    }

    public void setValorMoneda(long valorMoneda) {
        this.valorMoneda = valorMoneda;
    }

    public long getValorEfectivo() {
        return valorEfectivo;
    }

    public void setValorEfectivo(long valorEfectivo) {
        this.valorEfectivo = valorEfectivo;
    }

    public String getImagenArte() {
        return imagenArte;
    }

    public void setImagenArte(String imagenArte) {
        this.imagenArte = imagenArte;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public long getCantMinAcumulado() {
        return cantMinAcumulado;
    }

    public void setCantMinAcumulado(long cantMinAcumulado) {
        this.cantMinAcumulado = cantMinAcumulado;
    }

    public String getCodigoCertificado() {
        return codigoCertificado;
    }

    public void setCodigoCertificado(String codigoCertificado) {
        this.codigoCertificado = codigoCertificado;
    }

    public String getFechaRedencion() {
        return fechaRedencion;
    }

    public void setFechaRedencion(String fechaRedencion) {
        this.fechaRedencion = fechaRedencion;
    }

    public String getIndEstado() {
        return indEstado;
    }

    public void setIndEstado(String indEstado) {
        this.indEstado = indEstado;
    }

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }
}
