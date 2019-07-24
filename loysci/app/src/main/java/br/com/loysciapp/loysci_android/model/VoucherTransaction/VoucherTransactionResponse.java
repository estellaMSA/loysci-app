package br.com.loysciapp.loysci_android.model.VoucherTransaction;

public class VoucherTransactionResponse {

    private String internalIdCreated;
    private Long dateCreation;

    public VoucherTransactionResponse(String internalIdCreated, Long dateCreation) {
        this.internalIdCreated = internalIdCreated;
        this.dateCreation = dateCreation;
    }

    public VoucherTransactionResponse() {
    }

    public String getInternalIdCreated() {
        return internalIdCreated;
    }

    public void setInternalIdCreated(String internalIdCreated) {
        this.internalIdCreated = internalIdCreated;
    }

    public Long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Long dateCreation) {
        this.dateCreation = dateCreation;
    }
}
