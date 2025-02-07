package br.com.loysciapp.loysci_android.presentation.ui.presenters;


import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.model.AccessToken;
import br.com.loysciapp.loysci_android.model.Historial;
import br.com.loysciapp.loysci_android.model.History;
import br.com.loysciapp.loysci_android.model.MetricEntry;
import br.com.loysciapp.loysci_android.model.RewardRedeem;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.ExtractLoadListener;
import br.com.loysciapp.loysci_android.util.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Felipe Galeote on 29,Outubro,2018
 */
public class ExtractPresenter {
    private ExtractLoadListener loadListener;
    private List<History> extracts = new ArrayList<>();

    public ExtractPresenter(ExtractLoadListener listener) {
        this.loadListener = listener;
    }

    public void loadAllHistory(String memberName) {

        if (Prefs.getExternalAccessToken() != null && !Prefs.getExternalAccessToken().isEmpty()) {
            getMemberHistory(memberName);
        } else {
            LoyaltyApi.getExternalToken(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Prefs.saveExternalAccessToken(response.body());
                        getMemberHistory(memberName);
                    } else {
                        loadListener.onLoadExtractFailed();
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    loadListener.onLoadExtractFailed();
                }
            });
        }

    }

    private void getMemberHistory(String memberName) {
        LoyaltyApi.getMemberHistory(memberName, new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    extracts.clear();
                    List<History> allExtracts = response.body();
                    for (History history : allExtracts) {
                        String transactionId = history.getIdTransaction();
                        if (transactionId.contains("R1") || transactionId.contains("B1") || transactionId.contains("B2") || transactionId.contains("E1")
                                || transactionId.contains("E2") || transactionId.contains("B3") || transactionId.contains("A1") || transactionId.contains("R2")
                                || transactionId.contains("R3") || transactionId.contains("E3") || transactionId.contains("A2") || transactionId.contains("M1")
                                || transactionId.contains("M2")) {
                            if (history.getMetricEntry() != null) {
                                extracts.add(history);
                            }
                        }
                    }

                    LoyaltyApi.getRedeemedRewards(new Callback<List<RewardRedeem>>() {
                        @Override
                        public void onResponse(Call<List<RewardRedeem>> call, Response<List<RewardRedeem>> response) {

                            for (RewardRedeem r : response.body()){

                                History h = new History();
                                h.setIdTransaction("Resgate");
                                h.setDate(Long.parseLong(r.getFechaRedencion()));
                                h.setTransactionDesc(r.getCodigoCertificado());
                                MetricEntry metricEntry = new MetricEntry();
                                metricEntry.setAmount(Math.toIntExact((r.getValorMoneda()  * -1)));
                                h.setMetricEntry(metricEntry);


                                extracts.add(h);
                            }


                            LoyaltyApi.getHistory(new Callback<List<Historial>>() {
                                @Override
                                public void onResponse(Call<List<Historial>> call, Response<List<Historial>> response) {


                                    for (Historial h : response.body()){

                                        if(h.getIdInternalTransaction().equals("M")){


                                            History nH = new History();
                                            nH.setIdTransaction(h.getIdTransaction());
                                            nH.setDate(h.getDate());
                                            nH.setTransactionDesc(h.getIdTransaction());
                                            MetricEntry metricEntry = new MetricEntry();
                                            metricEntry.setAmount(h.getCantidadGanada());
                                            nH.setMetricEntry(metricEntry);

                                            extracts.add(nH);
                                        }
                                    }

                                    loadListener.onExtractLoaded(extracts);

                                }

                                @Override
                                public void onFailure(Call<List<Historial>> call, Throwable t) {

                                    loadListener.onLoadExtractFailed();
                                }
                            });






                        }

                        @Override
                        public void onFailure(Call<List<RewardRedeem>> call, Throwable t) {

                            loadListener.onLoadExtractFailed();
                        }
                    });



                } else {
                    loadListener.onLoadExtractFailed();
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                loadListener.onLoadExtractFailed();
            }
        });
    }

}

