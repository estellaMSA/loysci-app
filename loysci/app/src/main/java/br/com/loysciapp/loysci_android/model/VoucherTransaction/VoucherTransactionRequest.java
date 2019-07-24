package br.com.loysciapp.loysci_android.model.VoucherTransaction;

public class VoucherTransactionRequest {
    private Long date;
    private String transactionDesc;
    private String idMember;
    private int subtotal;
    private int taxes;
    private int total;
    private String idTransaction;

    public VoucherTransactionRequest(Long date, String transactionDesc, String idMember, int subtotal, int taxes, int total, String idTransaction) {
        this.date = date;
        this.transactionDesc = transactionDesc;
        this.idMember = idMember;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.idTransaction = idTransaction;
    }

    public VoucherTransactionRequest() {
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String idMember) {
        this.idMember = idMember;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }
}
