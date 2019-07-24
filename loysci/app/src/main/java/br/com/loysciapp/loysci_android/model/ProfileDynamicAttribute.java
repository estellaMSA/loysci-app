package br.com.loysciapp.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ProfileDynamicAttribute implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            DYNAMIC_ATTRIBUTE_TYPE_NUMERIC,
            DYNAMIC_ATTRIBUTE_TYPE_TEXT,
            DYNAMIC_ATTRIBUTE_TYPE_BOOLEAN,
            DYNAMIC_ATTRIBUTE_TYPE_DATE
    })
    public @interface DynamicAttributeType {
    }

    public static final String DYNAMIC_ATTRIBUTE_TYPE_NUMERIC = "N";
    public static final String DYNAMIC_ATTRIBUTE_TYPE_TEXT = "T";
    public static final String DYNAMIC_ATTRIBUTE_TYPE_BOOLEAN = "B";
    public static final String DYNAMIC_ATTRIBUTE_TYPE_DATE = "F";

    private String idAtributo;
    private String nombre;
    private String descripcion;
    @DynamicAttributeType
    private String indTipoDato;
    private boolean indRequerido;
    private String valorAtributoMiembro;

    public String getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(String idAtributo) {
        this.idAtributo = idAtributo;
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

    public String getIndTipoDato() {
        return indTipoDato;
    }

    public void setIndTipoDato(String indTipoDato) {
        this.indTipoDato = indTipoDato;
    }

    public boolean isIndRequerido() {
        return indRequerido;
    }

    public void setIndRequerido(boolean indRequerido) {
        this.indRequerido = indRequerido;
    }

    public String getValorAtributoMiembro() {
        return valorAtributoMiembro;
    }

    public void setValorAtributoMiembro(String valorAtributoMiembro) {
        this.valorAtributoMiembro = valorAtributoMiembro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idAtributo);
        dest.writeString(this.nombre);
        dest.writeString(this.descripcion);
        dest.writeString(this.indTipoDato);
        dest.writeByte(this.indRequerido ? (byte) 1 : (byte) 0);
        dest.writeString(this.valorAtributoMiembro);
    }

    public ProfileDynamicAttribute() {
    }

    protected ProfileDynamicAttribute(Parcel in) {
        this.idAtributo = in.readString();
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.indTipoDato = in.readString();
        this.indRequerido = in.readByte() != 0;
        this.valorAtributoMiembro = in.readString();
    }

    public static final Parcelable.Creator<ProfileDynamicAttribute> CREATOR = new Parcelable.Creator<ProfileDynamicAttribute>() {
        @Override
        public ProfileDynamicAttribute createFromParcel(Parcel source) {
            return new ProfileDynamicAttribute(source);
        }

        @Override
        public ProfileDynamicAttribute[] newArray(int size) {
            return new ProfileDynamicAttribute[size];
        }
    };
}
