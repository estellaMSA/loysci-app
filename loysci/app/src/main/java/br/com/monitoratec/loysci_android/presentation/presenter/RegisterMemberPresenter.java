package br.com.monitoratec.loysci_android.presentation.presenter;

import android.util.Log;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.AccessToken;
import br.com.monitoratec.loysci_android.model.Register;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.activities.RegisterConditionsActivity;
import br.com.monitoratec.loysci_android.util.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterMemberPresenter {

    RegisterConditionsActivity activity;
    private String codigo;

    public RegisterMemberPresenter(RegisterConditionsActivity activity) {
        this.activity = activity;
    }

    public void registerMember(Register register) {

        codigo = register.getCodigoIndicacao();

        register.setCodigoIndicacao(null);

            callRegister(register);



    }

    private void callRegister(Register register) {
        LoyaltyApi.setRegister(register, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e("r", String.valueOf(response.code()));
                if (response.code() == 204) {
                    executeLogin(register);
                } else {
                    if (response.code() == 104 || (response.errorBody() != null && response.errorBody().source() != null && response.errorBody().source().toString().contains("104")))
                        activity.showFailedMessage(activity.getString(R.string.profile_exist));
                    else
                        activity.showFailedMessage(activity.getString(R.string.register_error));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                activity.showFailedMessage(activity.getString(R.string.register_error));
            }
        });
    }

    private void executeLogin(Register register) {
        LoyaltyApi.signIn(register.getDocIdentificacion(), register.getContrasena(), new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Prefs.saveAccessToken(response.body());



                    if(codigo != null && !codigo.isEmpty())
                        referUser(register);
                    else
                        activity.registerSuccess();



                } else {
                    activity.showFailedLogin(activity.getString(R.string.auto_login_failed));
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                activity.showFailedLogin(activity.getString(R.string.auto_login_failed));
            }
        });

        LoyaltyApi.getExternalToken(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Prefs.saveExternalAccessToken(response.body());
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {

            }
        });
    }

    private void referUser(Register register) {




        LoyaltyApi.setReferralsCode(codigo, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code() <=250 && response.code() > 200){

                    activity.registerSuccess();
                    register.setCodigoIndicacao(null);
                }
                else{
                    activity.showFailedMessage(activity.getString(R.string.register_error));
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {


                activity.showFailedMessage(activity.getString(R.string.register_error));
            }
        });

    }
}
