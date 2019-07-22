package br.com.monitoratec.loysci_android.mvp.conteudo;

import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.util.Constants;

public interface VerConteudoView {
    void setMissionAndPoints(Mission mission, int pontos);

    void setVideoURL(String videoStr);

    void setImgUrl(String url);

    void setWebUrl(String url);

    void showProgress(boolean show);

    void successSendAnswer(String string);

    void errorSendAnswer(String message);

    void setTime(int duration);

}
