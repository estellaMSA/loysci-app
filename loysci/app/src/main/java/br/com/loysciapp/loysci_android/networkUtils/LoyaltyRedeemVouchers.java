package br.com.loysciapp.loysci_android.networkUtils;

import br.com.loysciapp.loysci_android.model.VoucherBackendRequest.VoucherBackend;
import br.com.loysciapp.loysci_android.model.VoucherBackendResponse.VoucherRequestEnvelope;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface LoyaltyRedeemVouchers {

    @Headers({
            "Content-Type: application/xml",
            "Accept: application/json"
    })
    @POST("wsfidelidade/index.php")
    Call<VoucherRequestEnvelope> redeemVouchers(@Body VoucherBackend voucherBackend);
}
