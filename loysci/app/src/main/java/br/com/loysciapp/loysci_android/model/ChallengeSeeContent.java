package br.com.loysciapp.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ChallengeSeeContent implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            CONTENT_TYPE_VIDEO,
            CONTENT_TYPE_IMAGE,
            CONTENT_TYPE_URL
    })
    public @interface ContentType {
    }

    public static final String CONTENT_TYPE_VIDEO = "V";
    public static final String CONTENT_TYPE_IMAGE = "I";
    public static final String CONTENT_TYPE_URL = "U";

    @ContentType
    private String indTipo;
    private String url;
    private String texto;

    @ContentType
    public String getIndTipo() {
        return indTipo;
    }

    public void setIndTipo(@ContentType String indTipo) {
        this.indTipo = indTipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        dest.writeString(this.url);
        dest.writeString(this.texto);
    }

    public ChallengeSeeContent() {
    }

    protected ChallengeSeeContent(Parcel in) {
        this.indTipo = in.readString();
        this.url = in.readString();
        this.texto = in.readString();
    }

    public static final Parcelable.Creator<ChallengeSeeContent> CREATOR = new Parcelable.Creator<ChallengeSeeContent>() {
        @Override
        public ChallengeSeeContent createFromParcel(Parcel source) {
            return new ChallengeSeeContent(source);
        }

        @Override
        public ChallengeSeeContent[] newArray(int size) {
            return new ChallengeSeeContent[size];
        }
    };
}
