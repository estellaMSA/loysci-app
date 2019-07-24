package br.com.loysciapp.loysci_android.model.VoucherBackendResponse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "CouponData")
public class CouponData
{
    @Attribute(name = "CouponCodigo")
    private String CouponCodigo;

    @Element(name = "Grupos", required = false)
    private String Grupos;

    @Element(name = "DataInicial")
    private Long DataInicial;

    @Element(name = "DataFinal")
    private Long DataFinal;

    @Element(name = "QtdePorCPF")
    private Integer QtdePorCPF;

    @Element(name = "Qtde")
    private Integer Qtde;

    @Element(name = "FimVigencia")
    private Long FimVigencia;

    @Element(name = "IniVigencia")
    private Long IniVigencia;

    @Element(name = "CouponsCPF")
    private CouponsCPF CouponsCPF;

    public String getGrupos() {
        return Grupos;
    }

    public void setGrupos(String grupos) {
        Grupos = grupos;
    }

    public String getCouponCodigo ()
    {
        return CouponCodigo;
    }

    public void setCouponCodigo (String CouponCodigo)
    {
        this.CouponCodigo = CouponCodigo;
    }

    public Long getDataInicial ()
    {
        return DataInicial;
    }

    public void setDataInicial (Long DataInicial)
    {
        this.DataInicial = DataInicial;
    }

    public Long getDataFinal ()
    {
        return DataFinal;
    }

    public void setDataFinal (Long DataFinal)
    {
        this.DataFinal = DataFinal;
    }

    public Integer getQtdePorCPF ()
    {
        return QtdePorCPF;
    }

    public void setQtdePorCPF (Integer QtdePorCPF)
    {
        this.QtdePorCPF = QtdePorCPF;
    }

    public Integer getQtde ()
    {
        return Qtde;
    }

    public void setQtde (Integer Qtde)
    {
        this.Qtde = Qtde;
    }

    public Long getFimVigencia ()
    {
        return FimVigencia;
    }

    public void setFimVigencia (Long FimVigencia)
    {
        this.FimVigencia = FimVigencia;
    }

    public Long getIniVigencia ()
    {
        return IniVigencia;
    }

    public void setIniVigencia (Long IniVigencia)
    {
        this.IniVigencia = IniVigencia;
    }

    public CouponsCPF getCouponsCPF ()
    {
        return CouponsCPF;
    }

    public void setCouponsCPF (CouponsCPF CouponsCPF)
    {
        this.CouponsCPF = CouponsCPF;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CouponCodigo = "+CouponCodigo+", DataInicial = "+DataInicial+", DataFinal = "+DataFinal+", QtdePorCPF = "+QtdePorCPF+", Qtde = "+Qtde+", FimVigencia = "+FimVigencia+", IniVigencia = "+IniVigencia+", CouponsCPF = "+CouponsCPF+"]";
    }
}
	