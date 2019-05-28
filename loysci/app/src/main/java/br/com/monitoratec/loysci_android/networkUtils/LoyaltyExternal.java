package br.com.monitoratec.loysci_android.networkUtils;

import java.util.List;
import java.util.Map;

import br.com.monitoratec.loysci_android.model.History;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionRequest;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoyaltyExternal {

    @Headers("Content-Type: application/json")
    @POST("v0/transactions")
    Call<VoucherTransactionResponse> voucherTransaction(@Body VoucherTransactionRequest voucherTransactionRequest, @HeaderMap Map<String, String> headers);

    @GET("v0/member-history/transactions/member-by-username/{username}")
    Call<List<History>> getMemberHistory(@HeaderMap Map<String, String> headers, @Path("username") String username);
}
