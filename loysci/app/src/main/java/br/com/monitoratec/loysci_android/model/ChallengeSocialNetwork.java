package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class ChallengeSocialNetwork implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            TYPE_LIKE,
            TYPE_SHARE,
            TYPE_MESSAGE,
            TYPE_IMAGE
    })
    public @interface ChallengeType {
    }

    public static final String TYPE_LIKE = "G";
    public static final String TYPE_SHARE = "L";
    public static final String TYPE_MESSAGE = "M";
    public static final String TYPE_IMAGE = "I";
    public static final String TYPE_TWITER_SHARE = "A";
    public static final String TYPE_TWITER_LIKE = "B";
    public static final String TYPE_INSTAGRAM_LIKE= "C";

    private String indTipo;
    private String mensaje;
    private String tituloUrl;
    private String urlObjectivo;
    private String urlImagen;

    public String getIndTipo() {
        return indTipo;
    }

    public void setIndTipo(String indTipo) {
        this.indTipo = indTipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTituloUrl() {
        return tituloUrl;
    }

    public void setTituloUrl(String tituloUrl) {
        this.tituloUrl = tituloUrl;
    }

    public String getUrlObjectivo() {
        return urlObjectivo;
    }

    public void setUrlObjectivo(String urlObjectivo) {
        this.urlObjectivo = urlObjectivo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.indTipo);
        dest.writeString(this.mensaje);
        dest.writeString(this.tituloUrl);
        dest.writeString(this.urlObjectivo);
        dest.writeString(this.urlImagen);
    }

    public ChallengeSocialNetwork() {
    }

    protected ChallengeSocialNetwork(Parcel in) {
        this.indTipo = in.readString();
        this.mensaje = in.readString();
        this.tituloUrl = in.readString();
        this.urlObjectivo = in.readString();
        this.urlImagen = in.readString();
    }

    public static final Parcelable.Creator<ChallengeSocialNetwork> CREATOR = new Parcelable.Creator<ChallengeSocialNetwork>() {
        @Override
        public ChallengeSocialNetwork createFromParcel(Parcel source) {
            return new ChallengeSocialNetwork(source);
        }

        @Override
        public ChallengeSocialNetwork[] newArray(int size) {
            return new ChallengeSocialNetwork[size];
        }
    };
}
