package br.com.loysciapp.loysci_android.model;

/**
 * Created by Felipe Galeote on 09,Novembro,2018
 */
public class MetricEntry {

    private String indType;
    private double amount;
    private double metricAmountAvailable;

    public String getIndType() {
        return indType;
    }

    public void setIndType(String indType) {
        this.indType = indType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getMetricAmountAvailable() {
        return metricAmountAvailable;
    }

    public void setMetricAmountAvailable(int metricAmountAvailable) {
        this.metricAmountAvailable = metricAmountAvailable;
    }
}
