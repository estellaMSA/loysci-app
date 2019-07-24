package br.com.loysciapp.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ChallengeUploadContent implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({TYPE_IMAGE, TYPE_VIDEO})
    public @interface ChallengeGameType {
    }

    public static final String TYPE_IMAGE = "I";
    public static final String TYPE_VIDEO = "V";

    private String indTipo;
    private String texto;

    public String getIndTipo() {
        return indTipo;
    }

    public void setIndTipo(String indTipo) {
        this.indTipo = indTipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.indTipo);
        dest.writeString(this.texto);
    }

    public ChallengeUploadContent() {
    }

    protected ChallengeUploadContent(Parcel in) {
        this.indTipo = in.readString();
        this.texto = in.readString();
    }

    public static final Parcelable.Creator<ChallengeUploadContent> CREATOR = new Parcelable.Creator<ChallengeUploadContent>() {
        @Override
        public ChallengeUploadContent createFromParcel(Parcel source) {
            return new ChallengeUploadContent(source);
        }

        @Override
        public ChallengeUploadContent[] newArray(int size) {
            return new ChallengeUploadContent[size];
        }
    };
}
