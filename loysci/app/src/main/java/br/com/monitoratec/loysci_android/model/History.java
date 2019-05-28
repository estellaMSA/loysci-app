package br.com.monitoratec.loysci_android.model;

public class History {


    public static final String TRANSACCION = "T"; //positivo
    public static final String REVERSION = "V"; //positivo

    private String idInternalTransaction;
    private String idTransaction;
    private String idLocation;
    private long date;
    private MetricEntry metricEntry;
    private String transactionDesc;

    public String getIdInternalTransaction() {
        return idInternalTransaction;
    }

    public void setIdInternalTransaction(String idInternalTransaction) {
        this.idInternalTransaction = idInternalTransaction;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public MetricEntry getMetricEntry() {
        return metricEntry;
    }

    public void setMetricEntry(MetricEntry metricEntry) {
        this.metricEntry = metricEntry;
    }

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }
}

