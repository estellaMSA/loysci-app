package br.com.loysciapp.loysci_android.model;

public class Historial {


    public static final String TRANSACCION = "T"; //positivo
    public static final String REVERSION = "V"; //positivo

    private String indFormaGanada;
    private String nombreMision;
    private String idLocation;
    private long fecha;
    private int cantidadGanada;



    public int getCantidadGanada() {
        return cantidadGanada;
    }

    private MetricEntry metricEntry;
    private String transactionDesc;

    public String getIdInternalTransaction() {
        return indFormaGanada;
    }

    public void setIdInternalTransaction(String idInternalTransaction) {
        this.indFormaGanada = idInternalTransaction;
    }

    public String getIdTransaction() {
        return nombreMision;
    }

    public void setIdTransaction(String idTransaction) {
        this.nombreMision = idTransaction;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public long getDate() {
        return fecha;
    }

    public void setDate(long date) {
        this.fecha = date;
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

