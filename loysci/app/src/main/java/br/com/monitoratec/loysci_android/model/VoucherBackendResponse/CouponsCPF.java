package br.com.monitoratec.loysci_android.model.VoucherBackendResponse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CouponsCPF")
public class CouponsCPF
{
    @Element(name = "CPF")
    private String CPF;

    public String getCPF ()
    {
        return CPF;
    }

    public void setCPF (String CPF)
    {
        this.CPF = CPF;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CPF = "+CPF+"]";
    }
}
			
	