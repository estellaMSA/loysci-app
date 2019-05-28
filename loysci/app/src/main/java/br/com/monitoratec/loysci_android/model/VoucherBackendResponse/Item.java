package br.com.monitoratec.loysci_android.model.VoucherBackendResponse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Item")
public class Item
{
    @Attribute
    private String QtdeOfertada;

    @Attribute
    private String QtdeMaxima;

    @Attribute
    private String QtdeMinima;

    @Attribute
    private String TipoCobranca;

    @Attribute
    private String QtdeMaximaCobranca;

    @Attribute
    private String Produto;

    @Attribute
    private String value;

    @Attribute
    private String Protecao;

    public String getQtdeOfertada ()
    {
        return QtdeOfertada;
    }

    public void setQtdeOfertada (String QtdeOfertada)
    {
        this.QtdeOfertada = QtdeOfertada;
    }

    public String getQtdeMaxima ()
    {
        return QtdeMaxima;
    }

    public void setQtdeMaxima (String QtdeMaxima)
    {
        this.QtdeMaxima = QtdeMaxima;
    }

    public String getQtdeMinima ()
    {
        return QtdeMinima;
    }

    public void setQtdeMinima (String QtdeMinima)
    {
        this.QtdeMinima = QtdeMinima;
    }

    public String getTipoCobranca ()
    {
        return TipoCobranca;
    }

    public void setTipoCobranca (String TipoCobranca)
    {
        this.TipoCobranca = TipoCobranca;
    }

    public String getQtdeMaximaCobranca ()
    {
        return QtdeMaximaCobranca;
    }

    public void setQtdeMaximaCobranca (String QtdeMaximaCobranca)
    {
        this.QtdeMaximaCobranca = QtdeMaximaCobranca;
    }

    public String getProduto ()
    {
        return Produto;
    }

    public void setProduto (String Produto)
    {
        this.Produto = Produto;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public String getProtecao ()
    {
        return Protecao;
    }

    public void setProtecao (String Protecao)
    {
        this.Protecao = Protecao;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [QtdeOfertada = "+QtdeOfertada+", QtdeMaxima = "+QtdeMaxima+", QtdeMinima = "+QtdeMinima+", TipoCobranca = "+TipoCobranca+", QtdeMaximaCobranca = "+QtdeMaximaCobranca+", Produto = "+Produto+", value = "+value+", Protecao = "+Protecao+"]";
    }
}
	