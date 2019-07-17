package br.com.monitoratec.loysci_android.mvp.conteudo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSeeContentAnswer;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.ChallengeUploadAnswer;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.model.Vimeo.GetVideoResponse;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.networkUtils.VimeoService;
import br.com.monitoratec.loysci_android.presentation.ui.activities.UploadDataActivity;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import br.com.monitoratec.loysci_android.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.model.ChallengeSeeContent.CONTENT_TYPE_IMAGE;
import static br.com.monitoratec.loysci_android.model.ChallengeSeeContent.CONTENT_TYPE_VIDEO;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;

public class VerConteudoPresenter {


    private Context context;
    private VerConteudoView view;
    private Mission mission;
    private Challenge desafio;
    private int pontos;

    public VerConteudoPresenter(Context context, VerConteudoView view, Intent intent) {
        this.context = context;
        this.view = view;
        this.mission = mission = intent.getParcelableExtra(MISSION_PARCELABLE);

    }


    public void init(){

        calculatePontos();
        defineMissionTipe();
        view.setMissionAndPoints(mission,pontos);
        setRegistrarVistaMision();


    }




    private void defineMissionTipe() {

        if(mission.getChallenges().size() > 0){

            desafio = mission.getChallenges().get(0);
            if(desafio.getMisionVerContenido().getIndTipo().equals(CONTENT_TYPE_VIDEO)){
                mountVideoUrl();
            }
            else if(desafio.getMisionVerContenido().getIndTipo().equals(CONTENT_TYPE_IMAGE)){

                view.setImgUrl(desafio.getMisionVerContenido().getUrl());
            }
            else{

                view.setWebUrl(desafio.getMisionVerContenido().getUrl());
            }

        }

    }

    private void mountVideoUrl() {

        String videoCode = desafio.getMisionVerContenido().getUrl().replace("https://vimeo.com/","");

        String videoStr = String.format("<html><body><br> <center>" +
                "<iframe src=\"https://player.vimeo.com/video/%s?autoplay=1\" width=\"300\" height=\"300\" frameborder=\"0\" allow=\"autoplay; fullscreen\" allowfullscreen></iframe>" +
                " </center></body></html>",videoCode);


        view.showProgress(true);

        LoyaltyApi.getVimeoVideo(videoCode, new Callback<GetVideoResponse>() {
            @Override
            public void onResponse(Call<GetVideoResponse> call, Response<GetVideoResponse> response) {


                view.setTime(response.body().getDuration());
                view.setVideoURL(videoStr);

                view.showProgress(false);

            }

            @Override
            public void onFailure(Call<GetVideoResponse> call, Throwable t) {

                view.showProgress(false);
                view.errorSendAnswer(t.getMessage());
                view.showProgress(false);
            }
        });









    }

    private void calculatePontos() {


        for (Challenge perguntas : mission.getChallenges()) {

            pontos += perguntas.getValor();
        }

    }


    public void onCallAPIAnswer() {

        view.showProgress(true);

        ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
        submitAnswers.setRespuestaVerContenido(new ChallengeSeeContentAnswer());
        LoyaltyApi.submitChallenge(desafio, submitAnswers, new Callback<ChallengeSubmitResponse>() {
            @Override
            public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {


                view.showProgress(false);

                if (response.isSuccessful() && response.body().getIndEstado().equals("G")) {

                    view.successSendAnswer(context.getString(R.string.success_see_conteudo));

                } else {
                    view.errorSendAnswer(ApiUtils.getError(response).getMessage());

                }
            }

            @Override
            public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {

                view.showProgress(false);
                view.errorSendAnswer(t.getMessage());

            }
        });

    }


    private void setRegistrarVistaMision() {
        LoyaltyApi.setRegistrarVistaMision(desafio.getIdMision(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Toast.makeText(ChallengeDetailActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.println(Log.ERROR, "RegistrarVistaMision", t.getMessage());
            }
        });
    }
}
