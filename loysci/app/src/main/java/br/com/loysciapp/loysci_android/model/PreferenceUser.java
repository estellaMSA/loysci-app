package br.com.loysciapp.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;




public class PreferenceUser implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            ANSWER_TYPE_SINGLE_SELECTION,
            ANSWER_TYPE_MULTIPLE_SELECTION,
            ANSWER_TYPE_INPUT_TEXT
    })
    public @interface AnswerType {
    }

    public static final String ANSWER_TYPE_SINGLE_SELECTION = "SU";
    public static final String ANSWER_TYPE_MULTIPLE_SELECTION = "RM";
    public static final String ANSWER_TYPE_INPUT_TEXT = "TX";

    private String idPreferencia;
    private String respuestas;
    private String pregunta;
    private String indTipoRespuesta;
    private String respuestaMiembro;

    public String getIdPreferencia() {
        return idPreferencia;
    }

    public void setIdPreferencia(String idPreferencia) {
        this.idPreferencia = idPreferencia;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getIndTipoRespuesta() {
        return indTipoRespuesta;
    }

    public void setIndTipoRespuesta(String indTipoRespuesta) {
        this.indTipoRespuesta = indTipoRespuesta;
    }

    public String getRespuestaMiembro() {
        return respuestaMiembro;
    }

    public void setRespuestaMiembro(String respuestaMiembro) {
        this.respuestaMiembro = respuestaMiembro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPreferencia);
        dest.writeString(this.respuestas);
        dest.writeString(this.pregunta);
        dest.writeString(this.indTipoRespuesta);
        dest.writeString(this.respuestaMiembro);
    }

    public PreferenceUser() {
    }

    protected PreferenceUser(Parcel in) {
        this.idPreferencia = in.readString();
        this.respuestas = in.readString();
        this.pregunta = in.readString();
        this.indTipoRespuesta = in.readString();
        this.respuestaMiembro = in.readString();
    }

    public static final Parcelable.Creator<PreferenceUser> CREATOR = new Parcelable.Creator<PreferenceUser>() {
        @Override
        public PreferenceUser createFromParcel(Parcel source) {
            return new PreferenceUser(source);
        }

        @Override
        public PreferenceUser[] newArray(int size) {
            return new PreferenceUser[size];
        }
    };
}

