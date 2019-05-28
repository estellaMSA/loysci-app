package br.com.monitoratec.loysci_android.model.VoucherBackendResponse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap:Body")
public class Body
{
    @Element(name = "CouponInsRS", required = false)
    public VoucherBackendResponse voucherBackendResponse;
}
	