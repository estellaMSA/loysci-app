package br.com.loysciapp.loysci_android.presentation.ui.viewModels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.model.Badge;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.model.Mission;
import br.com.loysciapp.loysci_android.model.Notification;
import br.com.loysciapp.loysci_android.model.Points;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.model.Progress;
import br.com.loysciapp.loysci_android.model.Promotion;
import br.com.loysciapp.loysci_android.model.Topic;
import br.com.loysciapp.loysci_android.model.TopicsMissionsResponse;
import br.com.loysciapp.loysci_android.model.TopicsResponse;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.loysciapp.loysci_android.util.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;
import static br.com.loysciapp.loysci_android.util.Constants.RESPONSE_EXPIRED_TOKEN;
import static br.com.loysciapp.loysci_android.util.Constants.RESPONSE_FAILED_LOAD;

/**
 * Created by Pedro Mazarini on 24/Oct/2018
 */
public class MainViewModel extends ViewModel {

    public Profile profile;
    public Points points;
    public List<Badge> badges;
    public List<Notification> notifications;
    public List<Topic> topicList;
    public List<Mission> missionList = new ArrayList<>();

    public Progress progress;
    public List<Promotion> promotions;
    public boolean finishedLoading = false;
    int loadCounter = 0;


    public void loadProfile(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (profile == null || forceUpdate) {
            LoyaltyApi.getProfile(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    finishedLoading();
                    if (response.isSuccessful()) {
                        if (response.code() == RESPONSE_EXPIRED_TOKEN) {
                            callback.onFailed(response.code());
                        } else {
                            profile = response.body();
                            Prefs.saveProfile(profile);
                            callback.onSuccess();
                        }
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadPoints(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (points == null || forceUpdate) {
            LoyaltyApi.getPoints(new Callback<Points>() {
                @Override
                public void onResponse(Call<Points> call, Response<Points> response) {
                    finishedLoading();
                    if (response.isSuccessful()) {
                        points = response.body();
                        Prefs.savePoints(points);
                        callback.onSuccess();
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<Points> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadBadges(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (badges == null || forceUpdate) {
            LoyaltyApi.getBadges(new Callback<List<Badge>>() {
                @Override
                public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {
                    finishedLoading();
                    if (response.isSuccessful()) {
                        badges = response.body();
                        Prefs.saveJewels(badges);
                        callback.onSuccess();
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<List<Badge>> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadProgress(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (progress == null || forceUpdate) {
            LoyaltyApi.getProgress(new Callback<Progress>() {
                @Override
                public void onResponse(Call<Progress> call, Response<Progress> response) {
                    finishedLoading();
                    if (response.isSuccessful()) {
                        progress = response.body();
                        Prefs.saveProgress(progress);
                        callback.onSuccess();
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<Progress> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadNotifications(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (notifications == null || forceUpdate) {
            LoyaltyApi.getNotifications(new Callback<List<Notification>>() {
                @Override
                public void onResponse(Call<List<Notification>> call, final Response<List<Notification>> response) {
                    finishedLoading();
                    if (response.isSuccessful()) {
                        notifications = response.body();
                        Prefs.saveNotifications(notifications);
                        callback.onSuccess();
                    } else
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                }

                @Override
                public void onFailure(Call<List<Notification>> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadPromotions(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (promotions == null || forceUpdate) {
            LoyaltyApi.getPromotions(new Callback<List<Promotion>>() {
                @Override
                public void onResponse(Call<List<Promotion>> call, final Response<List<Promotion>> response) {
                    promotions = response.body();
                    callback.onSuccess();
                }

                @Override
                public void onFailure(Call<List<Promotion>> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadChallenges(final ViewModelSimpleCallback callback, boolean forceUpdate) {
        if (promotions == null || forceUpdate) {
            LoyaltyApi.getChallengesGames(new Callback<List<Challenge>>() {
                @Override
                public void onResponse(Call<List<Challenge>> call, final Response<List<Challenge>> response) {
                    if (response.isSuccessful()) {
                        //challenges = response.body();
                        callback.onSuccess();
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<List<Challenge>> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        } else {
            callback.onSuccess();
        }
    }

    public void loadTopics(final Context context, final ViewModelSimpleCallback callback, boolean forceUpdate) {

//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,
//                "https://movida-gameficacao-api.now.sh/jornada",
//                null,
//
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("Response", response.toString());
//
//                        Gson gson = new Gson();
//                        TopicsResponse model = gson.fromJson(response.toString(), TopicsResponse.class);
//
//                        topicList = model.getTopicList();
//                        callback.onSuccess();
//                    }
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.getMessage());
//                        callback.onFailed(RESPONSE_FAILED_LOAD);
//                    }
//                }
//        );
//
//        queue.add(getRequest);

        LoyaltyApi.getTopics(new Callback<TopicsResponse>() {
            @Override
            public void onResponse(Call<TopicsResponse> call, final Response<TopicsResponse> response) {
                if (response.isSuccessful()) {
                    topicList = response.body().getTopicList();
                    callback.onSuccess();
                } else {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<TopicsResponse> call, Throwable t) {

                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }


    public void loadMission(final ViewModelSimpleCallback callback){

        LoyaltyApi.getChallenges(new Callback<List<Challenge>>() {
            @Override
            public void onResponse(Call<List<Challenge>> call, Response<List<Challenge>> response) {

                if(response.isSuccessful()){

                    missionList = new ArrayList<>();

                    for (Challenge challenge:response.body()) {

                        //IF QUE ESTAVA LISTANDO OS DESAFIOS TIPO "E "DO AFFINITY
                        //if(challenge.getMisionEncuestaPreguntas().size() == 0)
                            //continue;

                        Mission mission = new Mission();
                        List<Challenge> challengeList = new ArrayList<>();
                        challengeList.add(challenge);
                        mission.setChallenges(challengeList);
                        mission.setTitulo(challenge.getEncabezadoArte());
                        mission.setDescricao(challenge.getDescripcion());
                        mission.setIdMissao(challenge.getIdMision());
                        mission.setImagem(challenge.getImagen());
                        missionList.add(mission);

                    }



                    callback.onSuccess();
                }
                else{
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            }

            @Override
            public void onFailure(Call<List<Challenge>> call, Throwable t) {
                callback.onFailed(RESPONSE_FAILED_LOAD);
            }
        });
    }


    public void loadTopicsMission(final String idTopic, final String idMission, final ViewModelSimpleCallback callback) {
        if (profile != null) {
            LoyaltyApi.getTopicsMission(profile.getIdMiembro(), idTopic, idMission, new Callback<TopicsMissionsResponse>() {
                @Override
                public void onResponse(Call<TopicsMissionsResponse> call, final Response<TopicsMissionsResponse> response) {
                    if (response.isSuccessful()) {

                        int topicCounter = 0;
                        for (Topic topic : topicList) {
                            int missionCounter = 0;
                            for (Mission mission : topic.getMissions()) {
                                if (topic.getId().equals(idTopic) && mission.getIdMissao().equals(idMission)) {
                                    topicList.get(topicCounter).getMissions().get(missionCounter).setChallenges(response.body().getMission().getChallenges());
                                }
                                missionCounter++;
                            }
                            topicCounter++;
                        }
                        callback.onSuccess();
                    } else {
                        callback.onFailed(RESPONSE_FAILED_LOAD);
                    }
                }

                @Override
                public void onFailure(Call<TopicsMissionsResponse> call, Throwable t) {
                    callback.onFailed(RESPONSE_FAILED_LOAD);
                }
            });
        }
    }

    public void finishedLoading() {
        loadCounter++;
        if (loadCounter == 4) {
            finishedLoading = true;
        }
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case UPDATE_LICENSE:
        if (resultCode == RESULT_OK) {
            profile = data.getExtras().getParcelable(PROFILE_PARCELABLE);
        }
//                break;
//        }
    }
}
