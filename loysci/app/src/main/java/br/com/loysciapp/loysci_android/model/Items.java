package br.com.loysciapp.loysci_android.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "Items")
public class Items {

    @Attribute(name = "Produto")
    private String Produto;

    @Attribute (name = "Protecao")
    private String Protecao = "";

    @Attribute(name = "QtdeMinima")
    private String QtdeMinima;

    @Attribute(name = "QtdeOfertada")
    private String QtdeOfertada;

    @Text
    private String text = "0";

    public String getProduto() {
        return Produto;
    }

    public void setProduto(String produto) {
        Produto = produto;
    }

    public String getProtecao() {
        return Protecao;
    }

    public void setProtecao(String protecao) {
        Protecao = protecao;
    }

    public String getQtdeMinima() {
        return QtdeMinima;
    }

    public void setQtdeMinima(String qtdeMinima) {
        QtdeMinima = qtdeMinima;
    }

    public String getQtdeOfertada() {
        return QtdeOfertada;
    }

    public void setQtdeOfertada(String qtdeOfertada) {
        QtdeOfertada = qtdeOfertada;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Items(Reward voucher) {
        this.Produto = voucher.getCodPremio();
        this.Protecao = "";
        this.QtdeMinima = "1";
        this.QtdeOfertada = String.valueOf(voucher.getQuantity());
        this.text = "0";
    }
}
