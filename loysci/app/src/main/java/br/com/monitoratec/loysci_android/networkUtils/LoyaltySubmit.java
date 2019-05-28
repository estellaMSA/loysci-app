package br.com.monitoratec.loysci_android.networkUtils;

import java.util.Map;

import br.com.monitoratec.loysci_android.model.SubmitAnswerChallenge;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


public interface LoyaltySubmit {
    @POST("missao/resposta")
    Call<Void> setChallengeAnswer(@HeaderMap Map<String, String> headers, @Body SubmitAnswerChallenge submitAnswerChallenge);
}

