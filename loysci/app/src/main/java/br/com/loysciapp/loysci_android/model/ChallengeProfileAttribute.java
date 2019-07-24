package br.com.loysciapp.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ChallengeProfileAttribute implements Parcelable {

    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            ATTRIBUTE_TYPE_BIRTH_DATE,
            ATTRIBUTE_TYPE_GENDER,
            ATTRIBUTE_TYPE_MARRIAGE_STATUS,
            ATTRIBUTE_TYPE_NAME,
            ATTRIBUTE_TYPE_FIRST_NAME,
            ATTRIBUTE_TYPE_LAST_NAME,
            ATTRIBUTE_TYPE_CITY,
            ATTRIBUTE_TYPE_STATE,
            ATTRIBUTE_TYPE_COUNTRY,
            ATTRIBUTE_TYPE_PO_BOX,
            ATTRIBUTE_TYPE_EDUCATION,
            ATTRIBUTE_TYPE_INCOME,
            ATTRIBUTE_TYPE_HAVE_CHILD,
            ATTRIBUTE_TYPE_DYNAMIC
    })
    public @interface AttributeType {
    }

    public static final String ATTRIBUTE_TYPE_BIRTH_DATE = "A";
    public static final String ATTRIBUTE_TYPE_GENDER = "B";
    public static final String ATTRIBUTE_TYPE_MARRIAGE_STATUS = "C";
    public static final String ATTRIBUTE_TYPE_NAME = "E";
    public static final String ATTRIBUTE_TYPE_FIRST_NAME = "F";
    public static final String ATTRIBUTE_TYPE_LAST_NAME = "S";
    public static final String ATTRIBUTE_TYPE_CITY = "K";
    public static final String ATTRIBUTE_TYPE_STATE = "L";
    public static final String ATTRIBUTE_TYPE_COUNTRY = "M";
    public static final String ATTRIBUTE_TYPE_PO_BOX = "N";
    public static final String ATTRIBUTE_TYPE_EDUCATION = "O";
    public static final String ATTRIBUTE_TYPE_INCOME = "P";
    public static final String ATTRIBUTE_TYPE_HAVE_CHILD = "Q";
    public static final String ATTRIBUTE_TYPE_DYNAMIC = "R";
    public static final String ATTRIBUTE_TYPE_FRECUENCY = "D";

    private String idAtributo;
    private boolean indRequerido;
    @AttributeType
    private String indAtributo;
    private ProfileDynamicAttribute atributoDinamico;
    private String respuesta;

    public String getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(String idAtributo) {
        this.idAtributo = idAtributo;
    }

    public boolean isIndRequerido() {
        return indRequerido;
    }

    public void setIndRequerido(boolean indRequerido) {
        this.indRequerido = indRequerido;
    }

    @AttributeType
    public String getIndAtributo() {
        return indAtributo;
    }

    public void setIndAtributo(String indAtributo) {
        this.indAtributo = indAtributo;
    }

    public ProfileDynamicAttribute getAtributoDinamico() {
        return atributoDinamico;
    }

    public void setAtributoDinamico(ProfileDynamicAttribute atributoDinamico) {
        this.atributoDinamico = atributoDinamico;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idAtributo);
        dest.writeByte(this.indRequerido ? (byte) 1 : (byte) 0);
        dest.writeString(this.indAtributo);
        dest.writeParcelable(this.atributoDinamico, flags);
        dest.writeString(this.respuesta);
    }

    public ChallengeProfileAttribute() {
    }

    protected ChallengeProfileAttribute(Parcel in) {
        this.idAtributo = in.readString();
        this.indRequerido = in.readByte() != 0;
        this.indAtributo = in.readString();
        this.atributoDinamico = in.readParcelable(ProfileDynamicAttribute.class.getClassLoader());
        this.respuesta = in.readString();
    }

    public static final Parcelable.Creator<ChallengeProfileAttribute> CREATOR = new Parcelable.Creator<ChallengeProfileAttribute>() {
        @Override
        public ChallengeProfileAttribute createFromParcel(Parcel source) {
            return new ChallengeProfileAttribute(source);
        }

        @Override
        public ChallengeProfileAttribute[] newArray(int size) {
            return new ChallengeProfileAttribute[size];
        }
    };
}
