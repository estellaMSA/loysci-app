package br.com.loysciapp.loysci_android.networkUtils;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Path;

interface CloudStorageService {

    @GET("auth/v1.0")
    Call<Void> getAccessToken(@HeaderMap Map<String, String> fields);

    @PUT("v1/Storage-winspirecs/movida/images/client/{fileName}")
    Call<Void> uploadProfilePicture(@HeaderMap Map<String, String> fields, @Path("fileName") String fileName, @Body RequestBody body);
}