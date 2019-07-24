package br.com.loysciapp.loysci_android.networkUtils;

import java.util.Map;

import br.com.loysciapp.loysci_android.model.AccessToken;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface LoyaltySignInService {
    @FormUrlEncoded
    @POST("auth/realms/loyalty/protocol/openid-connect/token")
    Call<AccessToken> getAccessToken(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("auth/realms/externo/protocol/openid-connect/token")
    Call<AccessToken> getExternalAccessToken(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("auth/realms/externo/protocol/openid-connect/token")
    Call<AccessToken> getExternalAccessTokenHomolog(@FieldMap Map<String, String> fields);
}
