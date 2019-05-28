package br.com.monitoratec.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.List;

import br.com.monitoratec.loysci_android.model.Items;

@Root(name = "CouponInsRQ", strict = false)
public class VoucherBackend {

    @Element(name = "Login")
    private Login login;

    @Element(name = "CouponCore")
    private CouponCore couponCore;

    public Login getLogin() {
        return login;
    }

    public CouponCore getCouponCore() {
        return couponCore;
    }

    public VoucherBackend(String cpf, String grupos, List<Items> items) {
        this.login = new Login();
        this.couponCore = new CouponCore(cpf, grupos, items);
    }
}




