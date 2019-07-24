package br.com.loysciapp.loysci_android.networkUtils;

import java.util.Map;

import br.com.loysciapp.loysci_android.model.MissionsActivityResponse;
import br.com.loysciapp.loysci_android.model.TopicsMissionsResponse;
import br.com.loysciapp.loysci_android.model.TopicsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;


public interface LoyaltyMissions {
    @GET("jornada/")
    Call<TopicsResponse> getTopics(@HeaderMap Map<String, String> headers);

    @GET("jornada/{idTopic}/{idMission}/")
    Call<TopicsMissionsResponse> getTopicsMission(@HeaderMap Map<String, String> headers, @Path("idTopic") String idTopic, @Path("idMission") String idMission);

    @GET("missao/{idActivity}/")
    Call<MissionsActivityResponse> getActivityInfo(@HeaderMap Map<String, String> headers, @Path("idActivity") String idActivity);
}

