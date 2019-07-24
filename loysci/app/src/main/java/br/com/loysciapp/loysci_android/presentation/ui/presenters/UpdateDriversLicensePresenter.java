package br.com.loysciapp.loysci_android.presentation.ui.presenters;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityUpdateDriversLicenseBinding;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.model.ProfileDynamicAttribute;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.activities.UpdateDriversLicenseActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;

/**
 * Created by Pedro Mazarini on 23/Oct/2018
 */
public class UpdateDriversLicensePresenter {

    UpdateDriversLicenseActivity activity;
    ActivityUpdateDriversLicenseBinding binding;

    public UpdateDriversLicensePresenter(UpdateDriversLicenseActivity activity, ActivityUpdateDriversLicenseBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void updateDriversLicense(final String license, final String expiration) {

        LoyaltyApi.getProfile(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile;
                if (response.isSuccessful()) {
                    profile = response.body();
                    Integer index = 0, licenseIndex = null, expirationIndex = null;
                    for (ProfileDynamicAttribute profileDynamicAttribute : profile.getAtributoDinamicos()) {
                        String name = profileDynamicAttribute.getNombre();
                        if (name != null && name.equals("CNH"))
                            licenseIndex = index;
                        else if (name != null && name.equals("CNH Validade"))
                            expirationIndex = index;
                        index++;
                    }
                    if (licenseIndex != null)
                        profile.getAtributoDinamicos().get(licenseIndex).setValorAtributoMiembro(license);
                    if (expirationIndex != null)
                        profile.getAtributoDinamicos().get(expirationIndex).setValorAtributoMiembro(expiration);
                    updateProfile(profile);
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                showFailedChangePassMessage();
            }
        });
    }

    private void updateProfile(final Profile profile) {
        LoyaltyApi.updateProfile(profile, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccesfullyChangePassMessage();
                    Intent intent = new Intent();
                    intent.putExtra(PROFILE_PARCELABLE, profile);
                    activity.setResult(Activity.RESULT_OK, intent);
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
        binding.licenseLayout.setAlpha(1f);
        binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
        Snackbar.make(binding.tilExpiration, R.string.failed_update_license, Snackbar.LENGTH_LONG).show();
    }

    private void showSuccesfullyChangePassMessage() {
        Toast.makeText(activity, R.string.license_successfully_updated, Toast.LENGTH_LONG).show();
    }
}
