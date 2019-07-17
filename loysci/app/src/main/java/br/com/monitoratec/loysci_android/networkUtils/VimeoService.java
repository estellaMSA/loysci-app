package br.com.monitoratec.loysci_android.networkUtils;

import java.util.Map;

import br.com.monitoratec.loysci_android.model.Vimeo.GetTokenRequest;
import br.com.monitoratec.loysci_android.model.Vimeo.GetTokenResponse;
import br.com.monitoratec.loysci_android.model.Vimeo.GetVideoResponse;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionRequest;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VimeoService {


    @Headers({"Content-Type: application/json","Accept: application/vnd.vimeo.*+json;version=3.4"})
    @POST("oauth/authorize/client")
    Call<GetTokenResponse> getTokenVimeo(@Body GetTokenRequest request, @HeaderMap Map<String, String> headers);

    @Headers({"Content-Type: application/json","Accept: application/vnd.vimeo.*+json;version=3.4"})
    @GET("videos/{id}")
    Call<GetVideoResponse> getVideoVimeo(@Path("id") String id, @HeaderMap Map<String, String> headers);


}
