package br.com.loysciapp.loysci_android.presentation.ui.presenters;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.model.Items;
import br.com.loysciapp.loysci_android.model.Points;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.model.VoucherBackendRequest.VoucherBackend;
import br.com.loysciapp.loysci_android.model.VoucherBackendResponse.CouponRSCore;
import br.com.loysciapp.loysci_android.model.VoucherBackendResponse.VoucherRequestEnvelope;
import br.com.loysciapp.loysci_android.model.VoucherTransaction.VoucherTransactionRequest;
import br.com.loysciapp.loysci_android.model.VoucherTransaction.VoucherTransactionResponse;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.activities.CartActivity;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.SimpleCallback;
import br.com.loysciapp.loysci_android.util.ErrorDialog;
import br.com.loysciapp.loysci_android.util.Prefs;
import br.com.loysciapp.loysci_android.util.PurchaseVoucherMissingCarAlertDialog;
import br.com.loysciapp.loysci_android.util.ReceiptDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPresenter {

    private CartActivity cartActivity;
    private PurchaseVoucherMissingCarAlertDialog purchaseVoucherAlertDialog;
    private ReceiptDialog receiptDialog;

    public CartPresenter(CartActivity cartActivity) {
        this.cartActivity = cartActivity;
    }

    public int calculatePoints(List<Reward> voucherList) {
        int points = 0;

        if (voucherList != null) {
            for (Reward voucher : voucherList) {
                points = (int) (points + (voucher.getValorMoneda() * voucher.getQuantity()));
            }
        }

        return points;
    }

    public void proceedWithPurchaseRequest(List<Reward> voucherList, SimpleCallback<Void> callback) {
        if (voucherList != null) {

            if ((containsContract(voucherList) && !containsGroup(voucherList)) || (!containsDiary(voucherList) && !containsGroup(voucherList))) {
                showMissingCarDialog(voucherList);
                callback.onResponse(null);
                return;
            }

            hasEnoughPoints(voucherList, new SimpleCallback<Boolean>() {
                @Override
                public void onResponse(Boolean hasEnoughPoints) {
                    if (!hasEnoughPoints) {
                        showNotEnoughPointsDialog();
                        callback.onResponse(null);
                    } else {
                        claimVouchers(voucherList, new SimpleCallback<CouponRSCore>() {
                            @Override
                            public void onResponse(CouponRSCore response) {
                                callback.onResponse(null);
                                final String code = response.getCouponData().getCouponCodigo();
                                final Long expirationDate = response.getCouponData().getFimVigencia();
                                showReceiptDialog(voucherList, code, expirationDate);
                            }

                            @Override
                            public void onError(Throwable t) {
                                callback.onResponse(null);
                                if (t.getMessage().equals("REQUEST")) {
                                    showRequestError();
                                } else if (t.getMessage().equals("CONCLUSION")) {
                                    showConclusionError();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    callback.onResponse(null);
                    showRequestError();
                }
            });
        }
    }

    private boolean containsDiary(List<Reward> voucherList) {
        for (Reward r : voucherList) {
            if (r.getDetalleArte().contains(cartActivity.getString(R.string.item_per_daily))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsGroup(List<Reward> voucherList) {
        for (Reward r : voucherList) {
            if (r.getEncabezadoArte().contains(cartActivity.getString(R.string.group))) {
                return true;
            }
        }
        return false;
    }

    private boolean containsContract(List<Reward> voucherList) {
        for (Reward r : voucherList) {
            if (r.getDetalleArte().contains(cartActivity.getString(R.string.item_per_contract))) {
                return true;
            }
        }
        return false;
    }

    private void hasEnoughPoints(List<Reward> voucherList, SimpleCallback<Boolean> callback) {
        LoyaltyApi.getPoints(new Callback<Points>() {
            @Override
            public void onResponse(Call<Points> call, Response<Points> response) {
                callback.onResponse(
                        response.body() != null && response.body().getDisponible() > calculatePoints(voucherList)
                );
            }

            @Override
            public void onFailure(Call<Points> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void showMissingCarDialog(List<Reward> voucherList) {
        purchaseVoucherAlertDialog = new PurchaseVoucherMissingCarAlertDialog(cartActivity, voucherList, () -> {
            purchaseVoucherAlertDialog.dismiss();
            cartActivity.finish();
        });
        purchaseVoucherAlertDialog.show();
    }

    public void showNotEnoughPointsDialog() {
        ErrorDialog notEnoughPointsAlertDialog = new ErrorDialog(cartActivity, R.string.purchase_voucher_alert_not_enough_points, () -> {
        });
        notEnoughPointsAlertDialog.show();
    }

    public void showRequestError() {
        ErrorDialog errorDialog = new ErrorDialog(cartActivity, R.string.error_request_not_complete, () -> {
        });
        errorDialog.show();
    }

    public void showConclusionError() {
        ErrorDialog errorDialog = new ErrorDialog(cartActivity, R.string.erro_process_conclusion, () -> {
        });
        errorDialog.show();
    }

    public void showReceiptDialog(List<Reward> voucherList, String code, Long expirationDate) {
        receiptDialog = new ReceiptDialog(cartActivity, voucherList, calculatePoints(voucherList), code, expirationDate, () -> {
            Prefs.clearCart();
            cartActivity.finish();
        });
        receiptDialog.show();
//        receiptDialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    public void claimVouchers(List<Reward> voucherList, SimpleCallback<CouponRSCore> simpleCallback) {
        if (voucherList != null && !voucherList.isEmpty()) {
            List<Items> items = new ArrayList<>();
            String groupDaily = "";
            for (Reward r : voucherList) {
                if (r.getEncabezadoArte().contains(cartActivity.getString(R.string.group))) {
                    groupDaily = r.getTrackingCode();
                }
                Items item = new Items(r);
                items.add(item);
            }

            LoyaltyApi.redeemVouchers(setVoucherBackend(items, groupDaily), new Callback<VoucherRequestEnvelope>() {
                @Override
                public void onResponse(Call<VoucherRequestEnvelope> call, Response<VoucherRequestEnvelope> response) {
                    VoucherRequestEnvelope voucherBackendResponse = response.body();

                    if (voucherBackendResponse != null) {
                        VoucherTransactionRequest voucherTransactionRequest;
                        voucherTransactionRequest = new VoucherTransactionRequest();
                        try {
                            String codigo = voucherBackendResponse.getBody().voucherBackendResponse.getCouponRSCore().getCouponData().getCouponCodigo();
                            voucherTransactionRequest.setDate(System.currentTimeMillis());
                            voucherTransactionRequest.setTransactionDesc(codigo);
                            voucherTransactionRequest.setIdMember(voucherBackendResponse.getBody().voucherBackendResponse.getCouponRSCore().getCouponData().getCouponsCPF().getCPF());
                            voucherTransactionRequest.setSubtotal(-calculatePoints(voucherList));
                            voucherTransactionRequest.setTotal(-calculatePoints(voucherList));
                            voucherTransactionRequest.setTaxes(0);
                            voucherTransactionRequest.setIdTransaction("R1 - Resgate Loysci");
                        } catch (Exception e) {
                            simpleCallback.onError(new Throwable("REQUEST"));
                            return;
                        }

                        LoyaltyApi.transactVouchers(voucherTransactionRequest, new Callback<VoucherTransactionResponse>() {
                            @Override
                            public void onResponse(Call<VoucherTransactionResponse> call, Response<VoucherTransactionResponse> response) {
                                simpleCallback.onResponse(voucherBackendResponse.getBody().voucherBackendResponse.getCouponRSCore());
                            }

                            @Override
                            public void onFailure(Call<VoucherTransactionResponse> call, Throwable t) {
                                t.printStackTrace();
                                simpleCallback.onError(new Throwable("CONCLUSION"));
                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<VoucherRequestEnvelope> call, Throwable t) {
                    t.printStackTrace();
                    simpleCallback.onError(new Throwable("REQUEST"));
                }
            });
        }
    }

    private VoucherBackend setVoucherBackend(List<Items> items, String groupDaily) {
        Profile profile = new Profile();
        String jsonProfile = Prefs.getProfile();

        if (jsonProfile != null) {
            profile = new Gson().fromJson(jsonProfile, Profile.class);
        }

        return new VoucherBackend(profile.getDocIdentificacion(), groupDaily, items);
    }
}
