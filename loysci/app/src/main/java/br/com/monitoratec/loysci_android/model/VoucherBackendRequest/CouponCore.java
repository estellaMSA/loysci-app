package br.com.monitoratec.loysci_android.model.VoucherBackendRequest;

import org.simpleframework.xml.Element;

import java.util.List;

import br.com.monitoratec.loysci_android.model.Items;

class CouponCore {

    @Element(name = "CouponData")
    private CouponData couponData;
    @Element(name = "CouponItems")
    private CouponItems couponItems;

    CouponCore(String cpf, String grupos, List<Items> items) {
        couponData = new CouponData(cpf, grupos);
        couponItems = new CouponItems(items);
    }

    public CouponData getCouponData() {
        return couponData;
    }

    public CouponItems getCouponItems() {
        return couponItems;
    }





}