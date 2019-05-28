package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Reward implements Parcelable {
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
    @RewardType
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
    private Metric idMetrica;
    private long fechaExpiracion;
    private int cantDiasVencimiento;
    private int quantity;
    private String codPremio;

    public String getCodPremio() {
        return codPremio;
    }

    public void setCodPremio(String codPremio) {
        this.codPremio = codPremio;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCantDiasVencimiento() {
        return cantDiasVencimiento;
    }

    public void setCantDiasVencimiento(int cantDiasVencimiento) {
        this.cantDiasVencimiento = cantDiasVencimiento;
    }

    public long getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(long fechaExpiracion) {
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

    @RewardType
    public String getIndTipoPremio() {
        return indTipoPremio;
    }

    public void setIndTipoPremio(@RewardType String indTipoPremio) {
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
        return idMetrica;
    }

    public void setMetrica(Metric idMetrica) {
        this.idMetrica = idMetrica;
    }

    public Reward() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPremio);
        dest.writeString(this.nombre);
        dest.writeString(this.indTipoPremio);
        dest.writeByte(this.indEnvio ? (byte) 1 : (byte) 0);
        dest.writeString(this.descripcion);
        dest.writeLong(this.valorMoneda);
        dest.writeLong(this.valorEfectivo);
        dest.writeString(this.imagenArte);
        dest.writeString(this.trackingCode);
        dest.writeString(this.sku);
        dest.writeString(this.encabezadoArte);
        dest.writeString(this.subencabezadoArte);
        dest.writeString(this.detalleArte);
        dest.writeLong(this.cantMinAcumulado);
        dest.writeString(this.codigoCertificado);
        dest.writeString(this.fechaRedencion);
        dest.writeString(this.indEstado);
        dest.writeParcelable(this.idMetrica, flags);
        dest.writeLong(this.fechaExpiracion);
        dest.writeInt(this.cantDiasVencimiento);
        dest.writeInt(this.quantity);
        dest.writeString(this.codPremio);
    }

    protected Reward(Parcel in) {
        this.idPremio = in.readString();
        this.nombre = in.readString();
        this.indTipoPremio = in.readString();
        this.indEnvio = in.readByte() != 0;
        this.descripcion = in.readString();
        this.valorMoneda = in.readLong();
        this.valorEfectivo = in.readLong();
        this.imagenArte = in.readString();
        this.trackingCode = in.readString();
        this.sku = in.readString();
        this.encabezadoArte = in.readString();
        this.subencabezadoArte = in.readString();
        this.detalleArte = in.readString();
        this.cantMinAcumulado = in.readLong();
        this.codigoCertificado = in.readString();
        this.fechaRedencion = in.readString();
        this.indEstado = in.readString();
        this.idMetrica = in.readParcelable(Metric.class.getClassLoader());
        this.fechaExpiracion = in.readLong();
        this.cantDiasVencimiento = in.readInt();
        this.quantity = in.readInt();
        this.codPremio = in.readString();
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel source) {
            return new Reward(source);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };
}
