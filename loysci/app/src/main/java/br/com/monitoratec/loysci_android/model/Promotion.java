package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Promotion implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            ACTION_TYPE_URL,
            ACTION_TYPE_QR_CODE,
            ACTION_TYPE_ITF,
            ACTION_TYPE_EAN,
            ACTION_TYPE_CODE_128,
            ACTION_TYPE_CODE_29,
            ACTION_TYPE_REGALO
    })
    public @interface ActionType {
    }

    public static final String ACTION_TYPE_URL = "A";
    public static final String ACTION_TYPE_QR_CODE = "B";
    public static final String ACTION_TYPE_ITF = "C";
    public static final String ACTION_TYPE_EAN = "D";
    public static final String ACTION_TYPE_CODE_128 = "E";
    public static final String ACTION_TYPE_CODE_29 = "F";
    public static final String ACTION_TYPE_REGALO = "G";

    private String idPromocion;
    private String nombre;
    private String descripcion;
    @ActionType
    private String indTipoAccion;
    private String urlOrCodigo;
    private String imagenArte;
    private String encabezadoArte;
    private String subencabezadoArte;
    private String detalleArte;
    private boolean marcada;
    private Reward premio;

    public Reward getPremio() {
        return premio;
    }

    public void setPremio(Reward premio) {
        this.premio = premio;
    }

    public String getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(String idPromocion) {
        this.idPromocion = idPromocion;
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

    @ActionType
    public String getIndTipoAccion() {
        return indTipoAccion;
    }

    public void setIndTipoAccion(@ActionType String indTipoAccion) {
        this.indTipoAccion = indTipoAccion;
    }

    public String getUrlOrCodigo() {
        return urlOrCodigo;
    }

    public void setUrlOrCodigo(String urlOrCodigo) {
        this.urlOrCodigo = urlOrCodigo;
    }

    public String getImagenArte() {
        return imagenArte;
    }

    public void setImagenArte(String imagenArte) {
        this.imagenArte = imagenArte;
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

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPromocion);
        dest.writeString(this.nombre);
        dest.writeString(this.descripcion);
        dest.writeString(this.indTipoAccion);
        dest.writeString(this.urlOrCodigo);
        dest.writeString(this.imagenArte);
        dest.writeString(this.encabezadoArte);
        dest.writeString(this.subencabezadoArte);
        dest.writeString(this.detalleArte);
        dest.writeByte(this.marcada ? (byte) 1 : (byte) 0);
    }

    public Promotion() {
    }

    protected Promotion(Parcel in) {
        this.idPromocion = in.readString();
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.indTipoAccion = in.readString();
        this.urlOrCodigo = in.readString();
        this.imagenArte = in.readString();
        this.encabezadoArte = in.readString();
        this.subencabezadoArte = in.readString();
        this.detalleArte = in.readString();
        this.marcada = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Promotion> CREATOR = new Parcelable.Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel source) {
            return new Promotion(source);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };
}
