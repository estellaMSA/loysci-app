package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


public class Game implements Parcelable {

    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            TYPE_RULETA,
            TYPE_RASPADITA,
            TYPE_ROMPECABEZAS,
            TYPE_REALIDADAUMENTADA,
            TYPE_CRUCIGRAMA
    })
    public @interface ChallengeGameType {
    }

    public static final String TYPE_RULETA = "R";
    public static final String TYPE_RASPADITA = "A";
    public static final String TYPE_ROMPECABEZAS = "O";
    public static final String TYPE_REALIDADAUMENTADA = "";
    public static final String TYPE_CRUCIGRAMA = "";

    //----------------------------------------------------------------------------------------------
    private List<ChallengeGame> misionJuego;
    //private List<String> imagenes;
    //private String imagen;
    private String tipo;
    private String estrategia;
    private String idMision;
    private List<Image> imagenes;
    private int tiempo;

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public List<ChallengeGame> getMisionJuego() {
        return misionJuego;
    }

    public void setMisionJuego(List<ChallengeGame> misionJuego) {
        this.misionJuego = misionJuego;
    }

    public List<Image> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Image> imagenes) {
        this.imagenes = imagenes;
    }
/* public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
*/


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdMision() {
        return idMision;
    }

    public void setIdMision(String idMision) {
        this.idMision = idMision;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.tipo);
        dest.writeString(this.estrategia);
        dest.writeString(this.idMision);
        dest.writeTypedList(this.imagenes);
        dest.writeInt(this.tiempo);
    }

    public Game() {
    }

    protected Game(Parcel in) {
        this.misionJuego = new ArrayList<ChallengeGame>();
        this.tipo = in.readString();
        this.estrategia = in.readString();
        this.idMision = in.readString();
        this.imagenes = in.createTypedArrayList(Image.CREATOR);
        this.tiempo = in.readInt();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
