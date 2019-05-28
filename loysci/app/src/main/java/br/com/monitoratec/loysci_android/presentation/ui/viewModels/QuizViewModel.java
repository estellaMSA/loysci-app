package br.com.monitoratec.loysci_android.presentation.ui.viewModels;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeQuestion;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.ChallengeSurveyAnswer;
import br.com.monitoratec.loysci_android.model.MissionsActivityResponse;
import br.com.monitoratec.loysci_android.model.SubmitAnswerChallenge;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.CHALLENGE_CORRECT;
import static br.com.monitoratec.loysci_android.util.Constants.CHALLENGE_WRONG;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_WRONG;
import static br.com.monitoratec.loysci_android.util.Constants.RESPONSE_FAILED_LOAD;

/**
 * Created by Pedro Mazarini on 24/Oct/2018
 */
public class QuizViewModel extends ViewModel {

    public Challenge challenge;

    public void loadActivityInfo(final String idMember, final String idActivity,  final ViewModelSimpleCallback callback){
        LoyaltyApi.getActivityInfo(idMember, idActivity, new Callback<MissionsActivityResponse>() {
            @Override
            public void onResponse(Call<MissionsActivityResponse> call, final Response<MissionsActivityResponse> response) {
                if(response.isSuccessful() && response.body() != null && response.body().getChallenge() != null){
                    challenge.setPergunta(response.body().getChallenge().getPergunta());
                    challenge.setRespostas(response.body().getChallenge().getRespostas());
                    callback.onSuccess();
                }else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<MissionsActivityResponse> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }

    public void setChallengeCorrect(Challenge challenge, ChallengeQuestion question, int indexResposta, final ViewModelSimpleCallback callback){


        ChallengeSubmitAnswers answers = new ChallengeSubmitAnswers();

        List<ChallengeSurveyAnswer> respostas = new ArrayList<>();

        ChallengeSurveyAnswer e = new ChallengeSurveyAnswer();
        e.setIdPregunta(question.getIdPregunta());
        e.setRespuesta(String.valueOf(indexResposta));
        respostas.add(e);

        answers.setRespuestaEncuesta(respostas);

        LoyaltyApi.submitChallenge(challenge, answers, new Callback<ChallengeSubmitResponse>() {

            @Override
            public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {

                if(response.isSuccessful() && response.body().getIndEstado().equals("F")){

                    callback.onFailed(QUIZ_COMPLETED_WRONG);
                }
                else if(response.isSuccessful()){

                    callback.onSuccess();
                }
                else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }

            }

            @Override
            public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {

            }
        });
    }

    public void setChallengeWrong(final String idMember, String idTopic, String idMission,  final ViewModelSimpleCallback callback){
        LoyaltyApi.setChallengeCorrect(new SubmitAnswerChallenge(idMember, idTopic, idMission, challenge.getIdMision(), challenge.getNombre(), CHALLENGE_WRONG, String.valueOf(challenge.getValor())), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, final Response<Void> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }

}
