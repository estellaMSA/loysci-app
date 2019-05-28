package br.com.monitoratec.loysci_android.presentation.ui.presenters;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityChangePasswordBinding;
import br.com.monitoratec.loysci_android.model.AccessToken;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.activities.ChangePasswordActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pedro Mazarini on 23/Oct/2018
 */
public class ChangePasswordPresenter {

    ChangePasswordActivity activity;
    ActivityChangePasswordBinding binding;

    public ChangePasswordPresenter(ChangePasswordActivity activity, ActivityChangePasswordBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void changePassword(String currentPass, final String newPass, final Profile profile) {
        LoyaltyApi.signIn(profile.getDocIdentificacion(), "Mvd$" +currentPass, new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    profile.setContrasena("Mvd$"+newPass);
                    saveNewPass(profile);
                } else {
                    showWrongPassMessage();
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                showWrongPassMessage();
            }
        });
    }

    private void saveNewPass(Profile profile) {
        LoyaltyApi.updateProfile(profile, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccesfullyChangePassMessage();
                    activity.finish();
                } else {
                    showFailedChangePassMessage();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showFailedChangePassMessage();
            }
        });
    }

    private void showFailedChangePassMessage() {
        binding.changePassLayout.setAlpha(1f);
        binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
        Snackbar.make(binding.tilRepeatPass,activity.getString(R.string.failed_change_pass), Snackbar.LENGTH_LONG).show();
    }

    private void showWrongPassMessage() {
        binding.changePassLayout.setAlpha(1f);
        binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
        Snackbar.make(binding.tilRepeatPass,activity.getString(R.string.wrong_pass), Snackbar.LENGTH_LONG).show();
    }
    private void showSuccesfullyChangePassMessage() {
        Toast.makeText(activity, activity.getString(R.string.success_change_pass), Toast.LENGTH_LONG).show();
    }
}
