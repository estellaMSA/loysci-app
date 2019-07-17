package br.com.monitoratec.loysci_android.networkUtils;

import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.monitoratec.loysci_android.model.AccessToken;
import br.com.monitoratec.loysci_android.model.Badge;
import br.com.monitoratec.loysci_android.model.CepModel;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.GameLeague;
import br.com.monitoratec.loysci_android.model.GameLeagueDos;
import br.com.monitoratec.loysci_android.model.GameLeagueTres;
import br.com.monitoratec.loysci_android.model.GameLeagueUno;
import br.com.monitoratec.loysci_android.model.Historial;
import br.com.monitoratec.loysci_android.model.History;
import br.com.monitoratec.loysci_android.model.HistoryNegative;
import br.com.monitoratec.loysci_android.model.Leaderboard;
import br.com.monitoratec.loysci_android.model.MissionsActivityResponse;
import br.com.monitoratec.loysci_android.model.Notification;
import br.com.monitoratec.loysci_android.model.Points;
import br.com.monitoratec.loysci_android.model.Product;
import br.com.monitoratec.loysci_android.model.ProductCategory;
import br.com.monitoratec.loysci_android.model.ProductSubCategory;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.Progress;
import br.com.monitoratec.loysci_android.model.Promotion;
import br.com.monitoratec.loysci_android.model.Register;
import br.com.monitoratec.loysci_android.model.Reward;
import br.com.monitoratec.loysci_android.model.RewardRedeem;
import br.com.monitoratec.loysci_android.model.Secuency;
import br.com.monitoratec.loysci_android.model.SecuencyClose;
import br.com.monitoratec.loysci_android.model.SendMondoviAnswer;
import br.com.monitoratec.loysci_android.model.Store;
import br.com.monitoratec.loysci_android.model.SubmitAnswerChallenge;
import br.com.monitoratec.loysci_android.model.TopicsMissionsResponse;
import br.com.monitoratec.loysci_android.model.TopicsResponse;
import br.com.monitoratec.loysci_android.model.Tournament;
import br.com.monitoratec.loysci_android.model.Vimeo.GetTokenRequest;
import br.com.monitoratec.loysci_android.model.Vimeo.GetTokenResponse;
import br.com.monitoratec.loysci_android.model.Vimeo.GetVideoResponse;
import br.com.monitoratec.loysci_android.model.VoucherBackendRequest.VoucherBackend;
import br.com.monitoratec.loysci_android.model.VoucherBackendResponse.VoucherRequestEnvelope;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionRequest;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionResponse;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleCallback;
import br.com.monitoratec.loysci_android.util.Prefs;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public final class LoyaltyApi {
    //private static final String LOYALTY_SIGN_IN_API_BASE_URL = "https://idp.flecharoja.com/";
    //private static final String LOYALTY_API_BASE_URL = "https://api-loyalty.flecharoja.com/loyalty-api-client-test-movida/webresources/";
    private static final String LOYALTY_SIGN_IN_API_BASE_URL = "https://idp-brasil.loyaltysci.com";
    private static final String LOYALTY_API_BASE_URL = "https://api-brasil.loyaltysci.com/loyalty-api-client/webresources/";
    private static final String LOYALTY_API_EXTERNAL_BASE_URL = "https://api-brasil.loyaltysci.com/loyalty-api-external/webresources/";
    private static final String LOYALTY_API_BASE_URL_VOUCHER = "https://xml.movida.com.br/movida/";
    private static final String LOYALTY_API_BASE_URL_MISSIONS = "https://movida-gameficacao-api.now.sh/";
    private static final String LOYALTY_API_BASE_URL_SUBMIT = "https://movida-gameficacao-api.now.sh/";
    private static final String CLOUD_STORAGE_URL = "https://brcom-central-1.storage.oraclecloud.com/";
    private static final String VIMEO_URL = "https://api.vimeo.com/";

    private static final String LOYALTY_CEP = "http://cep.republicavirtual.com.br/";

    private static final LoyaltyService loyaltyService;
    private static final CloudStorageService cloudStorageService;
    private static final LoyaltySignInService loyaltySignInService;
    private static final LoyaltyRedeemVouchers loyaltyRedeemVouchers;
    private static final LoyaltyExternal loyaltyExternal;
    private static final LoyaltyMissions loyaltyMissions;
    private static final LoyaltySubmit loyaltySubmit;
    private static final VimeoService vimeoService;

    private static final LoyaltyCarregaCep loyaltyCep;

    private static final String content = "application/json";

    static {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_SIGN_IN_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        loyaltySignInService = retrofit.create(LoyaltySignInService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltyService = retrofit.create(LoyaltyService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_API_BASE_URL_VOUCHER)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltyRedeemVouchers = retrofit.create(LoyaltyRedeemVouchers.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(CLOUD_STORAGE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        cloudStorageService = retrofit.create(CloudStorageService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_API_BASE_URL_MISSIONS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltyMissions = retrofit.create(LoyaltyMissions.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_API_BASE_URL_SUBMIT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltySubmit = retrofit.create(LoyaltySubmit.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_API_EXTERNAL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltyExternal = retrofit.create(LoyaltyExternal.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(LOYALTY_CEP)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        loyaltyCep = retrofit.create(LoyaltyCarregaCep.class);


        retrofit = new Retrofit.Builder()
                .baseUrl(VIMEO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        vimeoService = retrofit.create(VimeoService.class);



    }




    private static void getExternalAuthorizationToken(SimpleCallback<String> callback) {
        String accessToken = Prefs.getExternalAccessToken();
        if (accessToken == null) {
            getExternalToken(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Prefs.saveExternalAccessToken(response.body());
                    callback.onResponse("bearer " + response.body().getAccessToken());
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    callback.onError(t);
                }
            });
        } else callback.onResponse("bearer " + accessToken);
    }

    private static void getAuthorizationToken(SimpleCallback<String> callback) {
        String accessToken = Prefs.getAccessToken();
        if (accessToken == null) {
            getInternalToken(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Prefs.saveAccessToken(response.body());
                    callback.onResponse("bearer " + response.body().getAccessToken());
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    callback.onError(t);
                }
            });
        } else callback.onResponse("bearer " + accessToken);
    }

    public static void signIn(String username, String password, Callback<AccessToken> callback) {
        Prefs.saveLoginInfo(new Pair<>(username, password));
        getInternalToken(callback);
    }

    public static void getInternalToken(Callback<AccessToken> callback) {
        Pair<String, String> loginInfo = Prefs.getLoginInfo();
        Map<String, String> params = new HashMap<String, String>();

        params.put("client_id", "loyalty-webpage");
        params.put("client_secret", "6e70bd68-67e8-4b24-885f-3657c385e136");//"client_secret", "962e3334-b8ae-41d5-9162-949332fc5445",
        params.put("grant_type", "password");
        params.put("username", loginInfo.first);
        params.put("password", loginInfo.second);
        Call<AccessToken> call = loyaltySignInService.getAccessToken(params);
        call.enqueue(callback);
    }

    public static void getExternalToken(Callback<AccessToken> callback) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("client_secret", "231efff5-be64-4c39-81d8-7491e2c0a87b");
        params.put("grant_type", "client_credentials");
        params.put("client_id", "loyalty-api-external");
        Call<AccessToken> call = loyaltySignInService.getExternalAccessToken(params);
        call.enqueue(callback);
    }

    public static void getProgress(Callback<Progress> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Progress> call = loyaltyService.getProgress(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getProgress(null), t);
            }
        });
    }

    public static void getPoints(Callback<Points> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Points> call = loyaltyService.getPoints(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getPoints(null), t);
            }
        });
    }

    public static void getBadges(Callback<List<Badge>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Badge>> call = loyaltyService.getBadges(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getBadges(null), t);
            }
        });
    }

    public static void getPromotions(Callback<List<Promotion>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Promotion>> call = loyaltyService.getPromotions(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getPromotions(null), t);
            }
        });
    }

    public static void getProfile(Callback<Profile> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Profile> call = loyaltyService.getProfile(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getProfile(null), t);
            }
        });
    }

    public static void getStores(Callback<List<Store>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Store>> call = loyaltyService.getStores(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getStores(null), t);
            }
        });
    }

    public static void getChallenges(Callback<List<Challenge>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Challenge>> call = loyaltyService.getChallenges(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getChallenges(null), t);
            }
        });
    }

    public static void getChallengesGames(Callback<List<Challenge>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Challenge>> call = loyaltyService.getChallengesGames(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getChallengesGames(null), t);
            }
        });
    }



    public static void getTopics(Callback<TopicsResponse> callback) {

        Map<String, String> map = new HashMap<>();
        map.put("User-Agent", "Android");
        map.put("Accept","application/json");
        map.put("Cache-Control","max-age=640000");
        map.put("Content-Type","application/json");

        Call<TopicsResponse> call = loyaltyMissions.getTopics(map);
        call.enqueue(callback);


    }

    public static void getTopicsMission(String idMiembro, String idTopic, String idMission, Callback<TopicsMissionsResponse> callback) {

        Map<String, String> map = new HashMap<>();
        map.put("ID_MIEMBRO", idMiembro);
        Call<TopicsMissionsResponse> call = loyaltyMissions.getTopicsMission(map, idTopic, idMission);
        call.enqueue(callback);

//        getAuthorizationToken(new SimpleCallback<String>() {
//            @Override
//            public void onResponse(String token) {
//
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                callback.onFailure(loyaltyMissions.getTopicsMission(null, null, null),t);
//            }
//        });
    }

    public static void getActivityInfo(String idMiembro, String idActivity, Callback<MissionsActivityResponse> callback) {

        Map<String, String> map = new HashMap<>();
        map.put("ID_MIEMBRO", idMiembro);
        Call<MissionsActivityResponse> call = loyaltyMissions.getActivityInfo(map, idActivity);
        call.enqueue(callback);

//        getAuthorizationToken(new SimpleCallback<String>() {
//            @Override
//            public void onResponse(String token) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                callback.onFailure(loyaltyMissions.getActivityInfo(null, null),t);
//            }
//        });
    }

    public static void setChallengeCorrect(SubmitAnswerChallenge submitAnswerChallenge, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltySubmit.setChallengeAnswer(map, submitAnswerChallenge);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltySubmit.setChallengeAnswer(null, null), t);
            }
        });
    }

    public static void setChallengeWrong(SubmitAnswerChallenge submitAnswerChallenge, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltySubmit.setChallengeAnswer(map, submitAnswerChallenge);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltySubmit.setChallengeAnswer(null, null), t);
            }
        });
    }

    public static void getChallengesSecuencyClose(Callback<List<SecuencyClose>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<SecuencyClose>> call = loyaltyService.getChallengesSecuencyClose(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getChallengesSecuencyClose(null), t);
            }
        });
    }

    public static void getChallengesSecuencyOpen(Callback<List<Secuency>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Secuency>> call = loyaltyService.getChallengesSecuencyOpen(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getChallengesSecuencyOpen(null), t);
            }
        });
    }


    public static void getHistoryPositive(Callback<List<History>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<History>> call = loyaltyService.getHistoryPositive(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getHistoryPositive(null), t);
            }
        });
    }

    public static void getHistoryNegative(Callback<List<HistoryNegative>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<HistoryNegative>> call = loyaltyService.getHistoryNegative(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getHistoryNegative(null), t);
            }
        });
    }

    public static void getRewardsWithoutReedem(Callback<List<Reward>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Reward>> call = loyaltyService.getRewardsWithoutRedeem(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getRewardsWithoutRedeem(null), t);
            }
        });
    }

    public static void getRedeemedRewards(Callback<List<RewardRedeem>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<RewardRedeem>> call = loyaltyService.getRedeemedRewards(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getRedeemedRewards(null), t);
            }
        });
    }

    public static void redeemReward(Reward reward, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.redeemReward(map, reward.getIdPremio());
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.redeemReward(null, reward.getIdPremio()), t);
            }
        });
    }

    public static void getChallengePerSecuency(String id, Callback<List<Challenge>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Challenge>> call = loyaltyService.getChallengePerSecuency(map, id);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getChallengePerSecuency(null, id), t);
            }
        });
    }

    public static void getProductCategories(Callback<List<ProductCategory>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<ProductCategory>> call = loyaltyService.getProductCategories(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getProductCategories(null), t);
            }
        });
    }

    public static void getProducts(Callback<List<Product>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Product>> call = loyaltyService.getProducts(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getProducts(null), t);
            }
        });
    }

    public static void getProducts(ProductSubCategory subcategory, Callback<List<Product>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Product>> call = loyaltyService.getProducts(map, subcategory.getIdSubcategoria());
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getProducts(null, subcategory.getIdSubcategoria()), t);
            }
        });
    }

    public static void updateProfilePictureToken(Callback<Void> callback) {
        Map<String, String> header = new HashMap<>();
        header.put("X-Storage-User", "Storage-winspirecs:loysci");
        header.put("X-Storage-Pass", "Loysc1.@3418##");
        Call<Void> call = cloudStorageService.getAccessToken(header);
        call.enqueue(callback);
    }

    public static void uploadProfilePicture(RequestBody bytes, String fileName, String token, Callback<Void> callback) {
        Map<String, String> header = new HashMap<>();
        header.put("X-Auth-Token", token);
        Call<Void> call = cloudStorageService.uploadProfilePicture(header, fileName, bytes);
        call.enqueue(callback);
    }

    public static void updateProfile(Profile profile, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.updateProfile(map, profile);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.updateProfile(null, profile), t);
            }
        });
    }

    public static void submitChallenge(Challenge challenge, ChallengeSubmitAnswers answers, Callback<ChallengeSubmitResponse> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<ChallengeSubmitResponse> call = loyaltyService.submitChallenge(map, challenge.getIdMision(), answers);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.submitChallenge(null, challenge.getIdMision(), answers), t);
            }
        });
    }

    public static void getLeaderboards(Callback<Leaderboard> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Leaderboard> call = loyaltyService.getLeaderboards(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getLeaderboards(null), t);
            }
        });
    }

    public static void getHistory(Callback<List<Historial>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Historial>> call = loyaltyService.getHistory(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getHistory(null), t);
            }
        });
    }

    public static void setRegistrarVistaPromocion(String idPromocion, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.setRegistrarVistaPromocion(map, idPromocion);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setRegistrarVistaPromocion(null, idPromocion), t);
            }
        });
    }

    public static void updateMarcarFavorito(String idPromocion, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.updateMarcarFavorito(map, idPromocion);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.updateMarcarFavorito(null, idPromocion), t);
            }
        });
    }

    public static void updateTokenId(String tokenId, Callback<Void> callback) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                body.put("token", tokenId);
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.updateTokenId(map, body);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.updateTokenId(null, body), t);
            }
        });
    }

    public static void setReferralsCode(String code, Callback<Void> callback) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                body.put("idMiembro", code);
                map.put("Authorization", token);
                map.put("Content-Type", "application/json");
                map.put("User-Agent", "Android");
                Log.e("sdfds", code);
                Call<Void> call = loyaltyService.setReferir(map, body);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setReferir(null, body), t);
            }
        });
    }

    public static void getNotification(String idNotification, Callback<Notification> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Notification> call = loyaltyService.getNotification(map, idNotification);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getNotification(null, idNotification), t);
            }
        });
    }

    public static void getNotificationOpen(Callback<List<Notification>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Notification>> call = loyaltyService.getNotificationOpen(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getNotificationOpen(null), t);
            }
        });
    }

    public static void getNotificationClose(Callback<List<Notification>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Notification>> call = loyaltyService.getNotificationClose(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getNotificationClose(null), t);
            }
        });
    }

    public static void getNotifications(Callback<List<Notification>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Notification>> call = loyaltyService.getNotifications(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getNotifications(null), t);
            }
        });
    }

    public static void setRegister(Register register, Callback<Void> callback) {
        Call<Void> call = loyaltyService.setRegister(content, register);
        call.enqueue(callback);
    }

    public static void setRegistrarVistaMision(String idMision, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.setRegistrarVistaMision(map, idMision);
                call.enqueue(callback);

            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setRegistrarVistaMision(null, idMision), t);
            }
        });
    }

    public static void setForgotPassword(String email, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.setPassword(map, email);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setPassword(null, email), t);
            }
        });
    }

    public static void canjear(String idPremio, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.canjearCertificado(map, idPremio);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setPassword(null, idPremio), t);
            }
        });
    }

    public static void probarSuerte(String idPromocion, String region, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.probarSuerte(map, idPromocion, region);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.probarSuerte(null, idPromocion, region), t);
            }
        });
    }

    public static void setMarcarVisto(String idNotificacion, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.updateMarcarNotificacionVista(map, idNotificacion);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.updateMarcarNotificacionVista(null, idNotificacion), t);
            }
        });
    }

    public static void setForgot(String email, Callback<Void> callback) {
        Map<String, String> map = new HashMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        map.put("User-Agent", "Android");
        map.put("Content-Type", "application/json");
        Call<Void> call = loyaltyService.setForgot(map, body);
        call.enqueue(callback);
    }

    public static void getGamesCuatro(String idTorneo, Callback<List<GameLeague>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<GameLeague>> call = loyaltyService.getGamesCuatro(map, idTorneo);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getGamesCuatro(null, idTorneo), t);
            }
        });
    }

    public static void getGamesUno(String idTorneo, Callback<List<GameLeagueUno>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<GameLeagueUno>> call = loyaltyService.getGamesUno(map, idTorneo);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getGamesUno(null, idTorneo), t);
            }
        });
    }

    public static void getGamesDos(String idTorneo, Callback<List<GameLeagueDos>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<GameLeagueDos>> call = loyaltyService.getGamesDos(map, idTorneo);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getGamesDos(null, idTorneo), t);
            }
        });
    }

    public static void getGamesTres(String idTorneo, Callback<List<GameLeagueTres>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<GameLeagueTres>> call = loyaltyService.getGamesTres(map, idTorneo);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getGamesTres(null, idTorneo), t);
            }
        });
    }

    public static void getTournaments(Callback<List<Tournament>> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<List<Tournament>> call = loyaltyService.getTournament(map);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.getTournament(null), t);
            }
        });
    }

    public static void setApuesta(String idJuego, SendMondoviAnswer gameLeagueUno, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.setApuestaMondovi(map, idJuego, gameLeagueUno);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setApuestaMondovi(null, idJuego, gameLeagueUno), t);
            }
        });
    }

    public static void setApuestas(List<SendMondoviAnswer> sendMondoviAnswers, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.setApuestas(map, sendMondoviAnswers);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.setApuestas(null, sendMondoviAnswers), t);
            }
        });
    }


    public static void setProfile(Profile profile, Callback<Void> callback) {
        getAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String token) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", token);
                map.put("User-Agent", "Android");
                Call<Void> call = loyaltyService.updateProfile(map, profile);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyService.updateProfile(null, profile), t);
            }
        });
    }

    public static void redeemVouchers(VoucherBackend voucherBackend, Callback<VoucherRequestEnvelope> callback) {
        Call<VoucherRequestEnvelope> call = loyaltyRedeemVouchers.redeemVouchers(voucherBackend);
        call.enqueue(callback);
    }

    public static void transactVouchers(VoucherTransactionRequest transactionRequest, Callback<VoucherTransactionResponse> callback) {
        getExternalAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String bearer) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", bearer);
                Call<VoucherTransactionResponse> call2 = loyaltyExternal.voucherTransaction(transactionRequest, map);
                call2.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyExternal.voucherTransaction(transactionRequest, null), t);
            }
        });
    }

    public static void getMemberHistory(String memberName, Callback<List<History>> callback) {
        getExternalAuthorizationToken(new SimpleCallback<String>() {
            @Override
            public void onResponse(String bearer) {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", bearer);
                Call<List<History>> call = loyaltyExternal.getMemberHistory(map, memberName);
                call.enqueue(callback);
            }

            @Override
            public void onError(Throwable t) {
                callback.onFailure(loyaltyExternal.getMemberHistory(null, memberName), t);
            }
        });
    }

    public static void getCEP(String cep, Callback<CepModel> callback) {
        try {
            Map<String, String> map = new HashMap<>();
            Call<CepModel> call = loyaltyCep.getCEP(cep, "json");
            call.enqueue(callback);
        }
        catch (Exception ex){
            String teste= "";
        }
    }


    public static void getVimeoVideo(String videoCode, Callback<GetVideoResponse> callback){
        try {


            getVimeoToken(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {

                    String accessToke = response.body().getAccessToke();


                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization", "bearer "+ accessToke);
                    Call<GetVideoResponse> newCall = vimeoService.getVideoVimeo(videoCode,map);
                    newCall.enqueue(callback);


                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {

                    callback.onFailure(vimeoService.getVideoVimeo(videoCode,null),t);
                }
            });




        }
        catch (Exception ex){
            String teste= "";
        }

    }


    private static void getVimeoToken(Callback<GetTokenResponse>callback){


        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Basic " + getTokenBase64());

        Call<GetTokenResponse> call = vimeoService.getTokenVimeo(new GetTokenRequest(),map);
        call.enqueue(callback);

    }

    private static String getTokenBase64() {


        String clientIdentifier = "93b6963fa55ef92f91ce32622039d8c761c183e1";
        String apiSecret = "le0KmbIUZ03ZfKbiHfetrYJMPqiiV4+ZXtqQcpDBInPZJIqcXKXgvVq0k2myHSXDJSXnhXNhiLp+R41Ou7Hh5O6nejmsxpHacXicDx931+6k0m93+botsEwxVmTfNGCI";
        String base64 = Base64.encodeToString((clientIdentifier + ":" + apiSecret).getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
        return base64;
    }

}
