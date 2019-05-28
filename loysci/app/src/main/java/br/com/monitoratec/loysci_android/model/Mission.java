package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static br.com.monitoratec.loysci_android.util.Constants.RANDOM_IMG_BASE_URL;

/**
 * Created by Pedro Mazarini on 09/Nov/2018.
 **/
public class Mission implements Parcelable {
    public static final Creator<Mission> CREATOR = new Creator<Mission>() {
        @Override
        public Mission createFromParcel(Parcel source) {
            return new Mission(source);
        }

        @Override
        public Mission[] newArray(int size) {
            return new Mission[size];
        }
    };
    @SerializedName("id_workflow")
    String idMissao;
    @SerializedName("nome_workflow")
    String titulo;
    @SerializedName("imagem_workflow")
    String imagem;
    String descricao;
    int valor;
    @SerializedName("missoes")
    List<Challenge> challenges;
    private Topic topic;


    public Mission(String titulo, String imagem, int valor, boolean completed, List<Challenge> challenges) {
        this.titulo = titulo;
        this.imagem = imagem;
        this.valor = valor;
        this.challenges = challenges;
    }

    public Mission() {
    }

    protected Mission(Parcel in) {
        this.idMissao = in.readString();
        this.titulo = in.readString();
        this.imagem = in.readString();
        this.descricao = in.readString();
        this.valor = in.readInt();
        this.challenges = in.createTypedArrayList(Challenge.CREATOR);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        if (imagem == null || imagem.equals("#"))
            return RANDOM_IMG_BASE_URL;
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public String getIdMissao() {
        return idMissao;
    }

    public void setIdMissao(String idMissao) {
        this.idMissao = idMissao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idMissao);
        dest.writeString(this.titulo);
        dest.writeString(this.imagem);
        dest.writeString(this.descricao);
        dest.writeInt(this.valor);
        dest.writeTypedList(this.challenges);

    }

    public boolean isCompleted() {
        valor = 0;
        for (Challenge challenge : challenges) {
            if (challenge.getValor() != 0 && challenge.getStatus() == null) {
                return false;
            }
            valor += challenge.getValor();
        }
        return true;
    }

    public boolean isInitied() {


        double valorEsperado = 0;

        for (Challenge challenge : challenges) {
            valorEsperado += challenge.getValor();
        }


        for (Challenge challenge : challenges) {
            if (challenge.getValor() != 0 && challenge.status != null) {

                valor += challenge.getValor();
            }
        }

        if(valor > 0  && valor < valorEsperado)
            return true;
        else
            return false;

    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
