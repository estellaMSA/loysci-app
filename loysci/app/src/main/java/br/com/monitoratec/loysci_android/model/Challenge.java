package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static br.com.monitoratec.loysci_android.util.Constants.RANDOM_IMG_BASE_URL;

public class Challenge implements Parcelable {

    public Challenge(String nombre, String imagenArte, boolean completed) {
        this.nombre = nombre;
        this.imagenArte = imagenArte;
        this.completed = completed;
    }

    public String getEncabezadoArte() {
        return encabezadoArte;
    }

    public void setEncabezadoArte(String encabezadoArte) {
        this.encabezadoArte = encabezadoArte;
    }



    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            TYPE_SURVEY,
            TYPE_UPDATE_PROFILE,
            TYPE_SEE_CONTENT,
            TYPE_UPLOAD_CONTENT,
            TYPE_SOCIAL_NETWORK,
            TYPE_PREFERENCES_USER
    })
    public @interface ChallengeType {
    }

    public static final String TYPE_SURVEY = "E";
    public static final String TYPE_UPDATE_PROFILE = "P";
    public static final String TYPE_SEE_CONTENT = "V";
    public static final String TYPE_UPLOAD_CONTENT = "S";
    public static final String TYPE_SOCIAL_NETWORK = "R";
    public static final String TYPE_GAMES = "J";
    public static final String TYPE_PREFERENCES_USER = "A";

     private String idMision;
    private String nombre;
    @SerializedName("detalleArte") private String descripcion;
    private String imagenArte;

    private String encabezadoArte;

    @ChallengeType
    private String indTipoMision;
    private String tags;
    private Metric metrica;
    private ChallengeSeeContent misionVerContenido;
    private List<ChallengeProfileAttribute> misionPerfilAtributos;
    private List<ChallengeQuestion> misionEncuestaPreguntas;
    private List<PreferenceUser> misionEncuestaPreferencias;
    private ChallengeSocialNetwork misionRedSocial;
    private ChallengeUploadContent misionSubirContenido;
    private Game juego;
    boolean completed;
    String status;
    String pergunta;
    String respostas;



    @SerializedName("cantMetrica")
    int valor;

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas = respostas;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getStatus() {



        if(misionEncuestaPreguntas.size() == 0)
            return null;
        else
            return misionEncuestaPreguntas.get(0).getRespuestaMiembro();



    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return valor == 0 || status != null;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Game getJuego() {
        return juego;
    }

    public void setJuego(Game juego) {
        this.juego = juego;
    }

    public String getIdMision() {
        return idMision;
    }

    public void setIdMision(String idMision) {
        this.idMision = idMision;
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

    public String getImagen() {
        if(imagenArte == null || imagenArte.equals("#"))
            return RANDOM_IMG_BASE_URL;
        return imagenArte;
    }

    public void setImagen(String imagen) {
        this.imagenArte = imagen;
    }



    @ChallengeType
    public String getIndTipoMision() {
        return indTipoMision;
    }

    public void setIndTipoMision(@ChallengeType String indTipoMision) {
        this.indTipoMision = indTipoMision;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Metric getMetrica() {
        return metrica;
    }

    public void setMetrica(Metric metrica) {
        this.metrica = metrica;
    }

    public ChallengeSeeContent getMisionVerContenido() {
        return misionVerContenido;
    }

    public void setMisionVerContenido(ChallengeSeeContent misionVerContenido) {
        this.misionVerContenido = misionVerContenido;
    }

    public List<ChallengeProfileAttribute> getMisionPerfilAtributos() {
        return misionPerfilAtributos;
    }

    public void setMisionPerfilAtributos(List<ChallengeProfileAttribute> misionPerfilAtributos) {
        this.misionPerfilAtributos = misionPerfilAtributos;
    }

    public List<ChallengeQuestion> getMisionEncuestaPreguntas() {
        return misionEncuestaPreguntas;
    }

    public void setMisionEncuestaPreguntas(List<ChallengeQuestion> misionEncuestaPreguntas) {
        this.misionEncuestaPreguntas = misionEncuestaPreguntas;
    }

    public ChallengeSocialNetwork getMisionRedSocial() {
        return misionRedSocial;
    }

    public void setMisionRedSocial(ChallengeSocialNetwork misionRedSocial) {
        this.misionRedSocial = misionRedSocial;
    }

    public ChallengeUploadContent getMisionSubirContenido() {
        return misionSubirContenido;
    }

    public void setMisionSubirContenido(ChallengeUploadContent misionSubirContenido) {
        this.misionSubirContenido = misionSubirContenido;
    }

    public List<PreferenceUser> getMisionEncuestaPreferencias() {
        return misionEncuestaPreferencias;
    }

    public void setMisionEncuestaPreferencias(List<PreferenceUser> misionEncuestaPreferencias) {
        this.misionEncuestaPreferencias = misionEncuestaPreferencias;
    }

    public Challenge() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMision);
        dest.writeString(this.nombre);
        dest.writeString(this.descripcion);
        dest.writeString(this.imagenArte);

        dest.writeString(this.encabezadoArte);

        dest.writeString(this.indTipoMision);
        dest.writeString(this.tags);
        dest.writeParcelable(this.metrica, flags);
        dest.writeParcelable(this.misionVerContenido, flags);
        dest.writeTypedList(this.misionPerfilAtributos);
        dest.writeTypedList(this.misionEncuestaPreguntas);
        dest.writeTypedList(this.misionEncuestaPreferencias);
        dest.writeParcelable(this.misionRedSocial, flags);
        dest.writeParcelable(this.misionSubirContenido, flags);
        dest.writeByte(this.completed ? (byte) 1 : (byte) 0);
        dest.writeString(this.status);
        dest.writeString(this.pergunta);
        dest.writeString(this.respostas);
        dest.writeInt(this.valor);
    }

    protected Challenge(Parcel in) {
        this.idMision = in.readString();
        this.nombre = in.readString();
        this.descripcion = in.readString();
        this.imagenArte = in.readString();

        this.encabezadoArte = in.readString();
        this.indTipoMision = in.readString();
        this.tags = in.readString();
        this.metrica = in.readParcelable(Metric.class.getClassLoader());
        this.misionVerContenido = in.readParcelable(ChallengeSeeContent.class.getClassLoader());
        this.misionPerfilAtributos = in.createTypedArrayList(ChallengeProfileAttribute.CREATOR);
        this.misionEncuestaPreguntas = in.createTypedArrayList(ChallengeQuestion.CREATOR);
        this.misionEncuestaPreferencias = in.createTypedArrayList(PreferenceUser.CREATOR);
        this.misionRedSocial = in.readParcelable(ChallengeSocialNetwork.class.getClassLoader());
        this.misionSubirContenido = in.readParcelable(ChallengeUploadContent.class.getClassLoader());

        this.completed = in.readByte() != 0;
        this.status = in.readString();
        this.pergunta = in.readString();
        this.respostas = in.readString();
        this.valor = in.readInt();
    }

    public static final Creator<Challenge> CREATOR = new Creator<Challenge>() {
        @Override
        public Challenge createFromParcel(Parcel source) {
            return new Challenge(source);
        }

        @Override
        public Challenge[] newArray(int size) {
            return new Challenge[size];
        }
    };
}
