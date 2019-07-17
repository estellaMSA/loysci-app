package br.com.monitoratec.loysci_android.model.Vimeo;

import com.google.gson.annotations.SerializedName;

public class GetTokenResponse {


    @SerializedName("access_token")
    private String accessToke;

    public String getAccessToke() {
        return accessToke;
    }
}
