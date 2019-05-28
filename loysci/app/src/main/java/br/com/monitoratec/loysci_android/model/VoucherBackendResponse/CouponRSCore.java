package br.com.monitoratec.loysci_android.model.VoucherBackendResponse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "CouponRSCore")
public class CouponRSCore
{
    @ElementList(name = "CouponItems")
    private List<Item> CouponItems;

    @Element(name = "CouponData")
    private CouponData CouponData;

    public List<Item> getCouponItems ()
    {
        return CouponItems;
    }

    public void setCouponItems (List<Item> CouponItems)
    {
        this.CouponItems = CouponItems;
    }

    public CouponData getCouponData ()
    {
        return CouponData;
    }

    public void setCouponData (CouponData CouponData)
    {
        this.CouponData = CouponData;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CouponItems = "+CouponItems+", CouponData = "+CouponData+"]";
    }
}
	