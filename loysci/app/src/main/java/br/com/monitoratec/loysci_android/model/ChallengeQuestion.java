package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ChallengeQuestion implements Parcelable {
    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            ANSWER_TYPE_SINGLE_SELECTION,
            ANSWER_TYPE_MULTIPLE_SELECTION,
            ANSWER_TYPE_INPUT_TEXT,
            ANSWER_TYPE_INPUT_NUMBER
    })
    public @interface AnswerType {
    }

    public static final String ANSWER_TYPE_SINGLE_SELECTION = "RU";
    public static final String ANSWER_TYPE_MULTIPLE_SELECTION = "RM";
    public static final String ANSWER_TYPE_INPUT_TEXT = "RT";
    public static final String ANSWER_TYPE_INPUT_NUMBER = "RN";

    @Retention(RetentionPolicy.CLASS)
    @StringDef({
            TYPE_INPUT,
            TYPE_RATING
    })
    public @interface QuestionType {
    }

    public static final String TYPE_INPUT = "E";
    public static final String TYPE_RATING = "C";
    private String idPregunta;
    @QuestionType
    private String indTipoPregunta;
    private String pregunta;
    private String respuestas;
    @AnswerType
    private String indTipoRespuesta;
    private String imagen;
    private boolean indComentario;
    private String respuestaMiembro;

    public String getRespuestaMiembro() {
        return respuestaMiembro;
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    @QuestionType
    public String getIndTipoPregunta() {
        return indTipoPregunta;
    }

    public void setIndTipoPregunta(@QuestionType String indTipoPregunta) {
        this.indTipoPregunta = indTipoPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(String respuestas) {
        this.respuestas = respuestas;
    }

    @AnswerType
    public String getIndTipoRespuesta() {
        return indTipoRespuesta;
    }

    public void setIndTipoRespuesta(@AnswerType String indTipoRespuesta) {
        this.indTipoRespuesta = indTipoRespuesta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isIndComentario() {
        return indComentario;
    }

    public void setIndComentario(boolean indComentario) {
        this.indComentario = indComentario;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idPregunta);
        dest.writeString(this.indTipoPregunta);
        dest.writeString(this.pregunta);
        dest.writeString(this.respuestas);
        dest.writeString(this.indTipoRespuesta);
        dest.writeString(this.imagen);
        dest.writeByte(this.indComentario ? (byte) 1 : (byte) 0);
    }

    public ChallengeQuestion() {
    }

    protected ChallengeQuestion(Parcel in) {
        this.idPregunta = in.readString();
        this.indTipoPregunta = in.readString();
        this.pregunta = in.readString();
        this.respuestas = in.readString();
        this.indTipoRespuesta = in.readString();
        this.imagen = in.readString();
        this.indComentario = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ChallengeQuestion> CREATOR = new Parcelable.Creator<ChallengeQuestion>() {
        @Override
        public ChallengeQuestion createFromParcel(Parcel source) {
            return new ChallengeQuestion(source);
        }

        @Override
        public ChallengeQuestion[] newArray(int size) {
            return new ChallengeQuestion[size];
        }
    };
}
