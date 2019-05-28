package br.com.monitoratec.loysci_android.model;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


public class GameMission {


    public String getEncabezadoArte() {
        return encabezadoArte;
    }

    public void setEncabezadoArte(String encabezadoArte) {
        this.encabezadoArte = encabezadoArte;
    }

    public String getDetalleArte() {
        return detalleArte;
    }

    public void setDetalleArte(String detalleArte) {
        this.detalleArte = detalleArte;
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
    private String descripcion;
    private String imagenArte;
    private float cantMetrica;
    private String encabezadoArte;
    private String detalleArte;
    @Challenge.ChallengeType
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
        return imagenArte;
    }

    public void setImagen(String imagen) {
        this.imagenArte = imagen;
    }

    public float getCantMetrica() {
        return cantMetrica;
    }

    public void setCantMetrica(float cantMetrica) {
        this.cantMetrica = cantMetrica;
    }

    @Challenge.ChallengeType
    public String getIndTipoMision() {
        return indTipoMision;
    }

    public void setIndTipoMision(@Challenge.ChallengeType String indTipoMision) {
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
}
