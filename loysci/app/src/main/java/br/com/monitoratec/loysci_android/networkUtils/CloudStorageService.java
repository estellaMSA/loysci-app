package br.com.monitoratec.loysci_android.networkUtils;

import android.graphics.Bitmap;

import com.google.zxing.BinaryBitmap;

import java.util.Map;

import br.com.monitoratec.loysci_android.model.AccessToken;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

interface CloudStorageService {

    @GET("auth/v1.0")
    Call<Void> getAccessToken(@HeaderMap Map<String, String> fields);

    @PUT("v1/Storage-winspirecs/movida/images/client/{fileName}")
    Call<Void> uploadProfilePicture(@HeaderMap Map<String, String> fields, @Path("fileName") String fileName, @Body RequestBody body);
}