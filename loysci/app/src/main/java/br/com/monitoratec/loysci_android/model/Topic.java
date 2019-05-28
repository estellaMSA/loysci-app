package br.com.monitoratec.loysci_android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static br.com.monitoratec.loysci_android.util.Constants.RANDOM_IMG_BASE_URL;

/**
 * Created by Pedro Mazarini on 09/Nov/2018.
 **/
public class Topic implements Parcelable {
    @SerializedName("id_card") String id;
    String nome;
    String descricao;
    @SerializedName("valor_premio") int points;
    String imagem;
    @SerializedName("id_premioWorkflow") String idPremio;
    @SerializedName("nome_premio") String nomePremio;
    @SerializedName("imagem_premio") String imagemPremio;
    @SerializedName("descricao_premio") String descricaoPremio;
    @SerializedName("workflows") List<Mission> missions;

    public Topic(String nome, int points, String imagem,List<Mission> missions) {
        this.nome = nome;
        this.points = points;
        this.imagem = imagem;
        this.missions = missions;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImagem() {
        if(imagem == null || imagem.equals("#"))
            return RANDOM_IMG_BASE_URL;
        return imagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(String idPremio) {
        this.idPremio = idPremio;
    }

    public String getNomePremio() {
        return nomePremio;
    }

    public void setNomePremio(String nomePremio) {
        this.nomePremio = nomePremio;
    }

    public String getImagemPremio() {
        return imagemPremio;
    }

    public void setImagemPremio(String imagemPremio) {
        this.imagemPremio = imagemPremio;
    }

    public String getDescricaoPremio() {
        return descricaoPremio;
    }

    public void setDescricaoPremio(String descricaoPremio) {
        this.descricaoPremio = descricaoPremio;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }

    public Topic() {
    }
    public boolean isCompleted() {
        for (Mission mission : missions) {
            for (Challenge challenge : mission.getChallenges()) {
                if(challenge.getValor() != 0 && challenge.status==null){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nome);
        dest.writeString(this.descricao);
        dest.writeInt(this.points);
        dest.writeString(this.imagem);
        dest.writeString(this.idPremio);
        dest.writeString(this.nomePremio);
        dest.writeString(this.imagemPremio);
        dest.writeString(this.descricaoPremio);
        dest.writeTypedList(this.missions);
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.nome = in.readString();
        this.descricao = in.readString();
        this.points = in.readInt();
        this.imagem = in.readString();
        this.idPremio = in.readString();
        this.nomePremio = in.readString();
        this.imagemPremio = in.readString();
        this.descricaoPremio = in.readString();
        this.missions = in.createTypedArrayList(Mission.CREATOR);
    }

    public static final Creator<Topic> CREATOR = new Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
