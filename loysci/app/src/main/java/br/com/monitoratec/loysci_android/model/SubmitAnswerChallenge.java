package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pedro Mazarini on 09/Nov/2018.
 **/
public class SubmitAnswerChallenge implements Parcelable {
    @SerializedName("ID_MIEMBRO") String idMember;
    @SerializedName("ID_CARD") String idTopic;
    @SerializedName("ID_WORKFLOW") String idMission;
    @SerializedName("ID_MISION") String idChallenge;
    @SerializedName("TITULO_MISSAO") String title;
    @SerializedName("STATUS") String status;
    @SerializedName("VALOR") String valor;

    public SubmitAnswerChallenge(String idMember, String idTopic, String idMission, String idChallenge, String title, String status, String valor) {
        this.idMember = idMember;
        this.idTopic = idTopic;
        this.idMission = idMission;
        this.idChallenge = idChallenge;
        this.title = title;
        this.status = status;
        this.valor = valor;
    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public String getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(String idTopic) {
        this.idTopic = idTopic;
    }

    public String getIdMission() {
        return idMission;
    }

    public void setIdMission(String idMission) {
        this.idMission = idMission;
    }

    public String getIdChallenge() {
        return idChallenge;
    }

    public void setIdChallenge(String idChallenge) {
        this.idChallenge = idChallenge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMember);
        dest.writeString(this.idTopic);
        dest.writeString(this.idMission);
        dest.writeString(this.idChallenge);
        dest.writeString(this.title);
        dest.writeString(this.status);
        dest.writeString(this.valor);
    }

    public SubmitAnswerChallenge() {
    }

    protected SubmitAnswerChallenge(Parcel in) {
        this.idMember = in.readString();
        this.idTopic = in.readString();
        this.idMission = in.readString();
        this.idChallenge = in.readString();
        this.title = in.readString();
        this.status = in.readString();
        this.valor = in.readString();
    }

    public static final Parcelable.Creator<SubmitAnswerChallenge> CREATOR = new Parcelable.Creator<SubmitAnswerChallenge>() {
        @Override
        public SubmitAnswerChallenge createFromParcel(Parcel source) {
            return new SubmitAnswerChallenge(source);
        }

        @Override
        public SubmitAnswerChallenge[] newArray(int size) {
            return new SubmitAnswerChallenge[size];
        }
    };
}
