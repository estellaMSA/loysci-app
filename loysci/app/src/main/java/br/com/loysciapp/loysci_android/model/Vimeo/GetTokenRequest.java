package br.com.loysciapp.loysci_android.model.Vimeo;

public class GetTokenRequest {

    private String grant_type;
    private String scope;

    public GetTokenRequest() {

        this.grant_type = "client_credentials";
        this.scope = "private";

    }
}
