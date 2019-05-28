package br.com.monitoratec.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;

public class CouponsCPF {

    @Element(name = "CPF")
    private String CPF;

    public CouponsCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}