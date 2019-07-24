package br.com.loysciapp.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;

class Login {

    @Element(name = "Source")
    private Source source;

    public Login() {
        this.source = new Source();
    }

    public Source getSource() {
        return source;
    }



}