package br.com.loysciapp.loysci_android.presentation.presenter;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.model.Points;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.activities.VoucherActivity;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.SimpleCallback;
import br.com.loysciapp.loysci_android.util.ErrorDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherActivityPresenter {

    private VoucherActivity view;

    public VoucherActivityPresenter(VoucherActivity view) {
        this.view = view;
    }


    public void setReedem(Reward reward, SimpleCallback<Boolean> callback){


        hasEnoughPoints(reward, new SimpleCallback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {


                if(response){

                    LoyaltyApi.redeemReward(reward, new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {

                                callback.onResponse(true);
                            }
                            else {

                                ErrorDialog errorDialog = new ErrorDialog(view, R.string.error_request_not_complete, () -> {
                                });
                                errorDialog.show();

                                callback.onResponse(false);

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                            ErrorDialog errorDialog = new ErrorDialog(view, R.string.error_request_not_complete, () -> {
                            });
                            errorDialog.show();

                            callback.onResponse(false);

                        }
                    });

                }
                else{

                    ErrorDialog errorDialog = new ErrorDialog(view, R.string.purchase_voucher_alert_not_enough_points, () -> {
                    });
                    errorDialog.show();

                    callback.onResponse(false);
                }

            }

            @Override
            public void onError(Throwable t) {


                ErrorDialog errorDialog = new ErrorDialog(view, R.string.error_request_not_complete, () -> {
                });
                errorDialog.show();

            }
        });


    }


    private void hasEnoughPoints(Reward voucher, SimpleCallback<Boolean> callback) {
        LoyaltyApi.getPoints(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {
                callback.onResponse(
                        response.body() != null && response.body().getDisponible() > voucher.getValorMoneda()
                );
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

}
