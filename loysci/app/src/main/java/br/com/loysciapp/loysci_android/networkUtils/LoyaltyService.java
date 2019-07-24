package br.com.loysciapp.loysci_android.networkUtils;

import java.util.List;
import java.util.Map;

import br.com.loysciapp.loysci_android.model.Badge;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitAnswers;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitResponse;
import br.com.loysciapp.loysci_android.model.GameLeague;
import br.com.loysciapp.loysci_android.model.GameLeagueDos;
import br.com.loysciapp.loysci_android.model.GameLeagueTres;
import br.com.loysciapp.loysci_android.model.GameLeagueUno;
import br.com.loysciapp.loysci_android.model.Historial;
import br.com.loysciapp.loysci_android.model.History;
import br.com.loysciapp.loysci_android.model.HistoryNegative;
import br.com.loysciapp.loysci_android.model.Leaderboard;
import br.com.loysciapp.loysci_android.model.Notification;
import br.com.loysciapp.loysci_android.model.Points;
import br.com.loysciapp.loysci_android.model.Product;
import br.com.loysciapp.loysci_android.model.ProductCategory;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.model.Progress;
import br.com.loysciapp.loysci_android.model.Promotion;
import br.com.loysciapp.loysci_android.model.Register;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.model.RewardRedeem;
import br.com.loysciapp.loysci_android.model.Secuency;
import br.com.loysciapp.loysci_android.model.SecuencyClose;
import br.com.loysciapp.loysci_android.model.SendMondoviAnswer;
import br.com.loysciapp.loysci_android.model.Store;
import br.com.loysciapp.loysci_android.model.Tournament;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface LoyaltyService {
    @POST("v0/admin/registro")
    Call<Void> setRegister(@Header("Content-Type") String content, @Body Register register);

    @GET("v0/perfil/balance/progreso")
    Call<Progress> getProgress(@HeaderMap Map<String, String> headers);

    @GET("v0/perfil/balance")
    Call<Points> getPoints(@HeaderMap Map<String, String> headers);


    @GET("v0/perfil/insignias")
    Call<List<Badge>> getBadges(@HeaderMap Map<String, String> headers);

    @GET("v0/promocion")
    Call<List<Promotion>> getPromotions(@HeaderMap Map<String, String> headers);

    @GET("v0/perfil")
    Call<Profile> getProfile(@HeaderMap Map<String, String> headers);

    @PUT("v0/perfil")
    Call<Void> updateProfile(@HeaderMap Map<String, String> headers, @Body Profile profile);

    @GET("v0/ubicacion")
    Call<List<Store>> getStores(@HeaderMap Map<String, String> headers);

    @GET("v0/mision")
    Call<List<Challenge>> getChallenges(@HeaderMap Map<String, String> headers);

    @GET("v0/mision/?indTpo=G")
    Call<List<Challenge>> getChallengesGames(@HeaderMap Map<String, String> headers);

    @GET("v0/mision/secuencias/?indTipo=C")
    Call<List<SecuencyClose>> getChallengesSecuencyClose(@HeaderMap Map<String, String> headers);

    @GET("v0/mision/secuencias/?indTipo=D")
    Call<List<Secuency>> getChallengesSecuencyOpen(@HeaderMap Map<String, String> headers);

    @GET("v0/perfil/balance/historial/?indTipo=N")
    Call<List<HistoryNegative>> getHistoryNegative(@HeaderMap Map<String, String> headers);

    @GET("v0/perfil/balance/historial/?indTipo=P")
    Call<List<History>> getHistoryPositive(@HeaderMap Map<String, String> headers);

    @GET("v0/premio")
    Call<List<Reward>> getRewardsWithoutRedeem(@HeaderMap Map<String, String> headers);

    @GET("v0/premio/redimidos")
    Call<List<RewardRedeem>> getRedeemedRewards(@HeaderMap Map<String, String> headers);

    @PUT("v0/premio/{idPremio}/redimir")
    Call<Void> redeemReward(@HeaderMap Map<String, String> headers, @Path("idPremio") String idPremio);

    @GET("v0/producto/categoria")
    Call<List<ProductCategory>> getProductCategories(@HeaderMap Map<String, String> headers);

    @GET("v0/producto")
    Call<List<Product>> getProducts(@HeaderMap Map<String, String> headers);

    @GET("v0/mision/secuencia/{idSecuencia}/misiones")
    Call<List<Challenge>> getChallengePerSecuency(@HeaderMap Map<String, String> headers, @Path("idSecuencia") String idSubcategoria);

    @GET("v0/producto/categoria/{idSubcategoria}/productos")
    Call<List<Product>> getProducts(@HeaderMap Map<String, String> headers, @Path("idSubcategoria") String idSubcategoria);

    @POST("v0/mision/{idMision}/completar")
    Call<ChallengeSubmitResponse> submitChallenge(@HeaderMap Map<String, String> headers, @Path("idMision") String idMision, @Body ChallengeSubmitAnswers answers);

    @GET("v0/perfil/leaderboards/general")
    Call<Leaderboard> getLeaderboards(@HeaderMap Map<String, String> headers);

    @GET("v0/perfil/balance/historial")
    Call<List<Historial>> getHistory(@HeaderMap Map<String, String> headers);

    @POST("v0/promocion/{idPromocion}/registrar-vista")
    Call<Void> setRegistrarVistaPromocion(@HeaderMap Map<String, String> headers, @Path("idPromocion") String idPromocion);

    @PUT("v0/promocion/{idPromocion}/marcar-favorito")
    Call<Void> updateMarcarFavorito(@HeaderMap Map<String, String> headers, @Path("idPromocion") String idPromocion);

    @PUT("v0/notificacion/refrescar-fcm-token")
    Call<Void> updateTokenId(@HeaderMap Map<String, String> headers, @Body Map<String, String> tokenId);

    //MARCA EL VISTO
    @GET("v0/notificacion/{idNotificacion}")
    Call<Notification> getNotification(@HeaderMap Map<String, String> headers, @Path("idNotificacion") String idNotification);
    //TODAS
    @GET("v0/notificacion")
    Call<List<Notification>> getNotifications(@HeaderMap Map<String, String> headers);
    //NOOOOOOOOOOOOOO
    @GET("v0/notificacion?tipo=0")
    Call<List<Notification>> getNotificationClose(@HeaderMap Map<String, String> headers);
    //NOOOOOOOOOOOOOO
    @GET("v0/notificacion?tipo=1")
    Call<List<Notification>> getNotificationOpen(@HeaderMap Map<String, String> headers);

    @PUT("v0/notificacion/{idNotificacion}/marcar-visto")
    Call<Void> updateMarcarNotificacionVista(@HeaderMap Map<String, String> headers, @Path("idNotificacion") String idNotificacion);

    @POST("v0/mision/{idMision}/registrar-vista")
    Call<Void> setRegistrarVistaMision(@HeaderMap Map<String, String> headers, @Path("idMision") String idMision);

    @POST("v0/admin/recuperar-contrasena")
    Call<Void> setPassword(@HeaderMap Map<String, String> headers, @Path("correo") String correo);

    @POST("v0/admin/referir")
    Call<Void> setReferir(@HeaderMap Map<String, String> headers, @Body Map<String, String> correo);

    @POST("v0/admin/recuperar-contrasena")
    Call<Void> setForgot(@HeaderMap Map<String, String> headers, @Body Map<String, String> email);

    @PUT("v0/premio/{idPremio}/canjear")
    Call<Void> canjearCertificado(@HeaderMap Map<String, String> headers, @Path("idPremio") String idPremio);

    @PUT("v0/promocion/{idPromocion}/probar-suerte")
    Call<Void> probarSuerte(@HeaderMap Map<String, String> headers, @Path("idPromocion") String idPromocion, @Query("region") String region);

    @GET("v0/apuestas/torneos")
    Call<List<Tournament>> getTournament(@HeaderMap Map<String, String> headers);

    @GET("v0/apuestas/torneos/{idTorneo}/juegos")
    Call<List<GameLeagueUno>> getGamesUno(@HeaderMap Map<String, String> headers, @Path("idTorneo") String idTorneo);

    @GET("v0/apuestas/torneos/{idTorneo}/juegos")
    Call<List<GameLeagueDos>> getGamesDos(@HeaderMap Map<String, String> headers, @Path("idTorneo") String idTorneo);

    @GET("v0/apuestas/torneos/{idTorneo}/juegos")
    Call<List<GameLeagueTres>> getGamesTres(@HeaderMap Map<String, String> headers, @Path("idTorneo") String idTorneo);

    @GET("v0/apuestas/torneos/{idTorneo}/juegos")
    Call<List<GameLeague>> getGamesCuatro(@HeaderMap Map<String, String> headers, @Path("idTorneo") String idTorneo);

    //Fragment Uno
    @POST("v0/apuestas/juegos/{idJuego}/apuesta")
    Call<Void> setApuestaMondovi(@HeaderMap Map<String, String> headers, @Path("idJuego") String idJuego, @Body SendMondoviAnswer gameLeague);

    @POST("v0/apuestas/juegos/apuestas")
    Call<Void> setApuestas(@HeaderMap Map<String, String> headers, @Body List<SendMondoviAnswer> answers);

}

