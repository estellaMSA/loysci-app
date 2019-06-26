package br.com.monitoratec.loysci_android.presentation.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;

import br.com.monitoratec.loysci_android.databinding.ActivityProfileBinding;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.activities.ProfileActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.PROFILE_PARCELABLE;

public class EditAddressPresenter {

    private ProfileActivity activity;
    ActivityProfileBinding binding;

    public EditAddressPresenter(ProfileActivity activity, ActivityProfileBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }

    public void saveAddress() {


        //binding.addressLayout.setAlpha(0.2f);
        //binding.addressLayout.setAlpha(0.2f);
        binding.loadingLayout.loadingText.setText(activity.getString(R.string.updating_data));
        binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);

        LoyaltyApi.getProfile(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()) {
                    Profile profile = activity.populateUserProfileWithInputs(response.body());
                    LoyaltyApi.updateProfile(profile, new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            Intent intent = new Intent();
                            intent.putExtra(PROFILE_PARCELABLE, profile);
                            activity.setResult(Activity.RESULT_OK, intent);
                            activity.finish();

                            Toast.makeText(activity, "Endereço atualizado com sucesso!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(activity, "Algum erro ocorreu", Toast.LENGTH_SHORT).show();

                            //binding.addressLayout.setAlpha(1f);
                            //binding.addressLayout.setAlpha(1f);
                            binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
               // Snackbar.make(activity.binding.toolbar, R.string.failed_load_profile, Snackbar.LENGTH_SHORT).show();
                //Snackbar.make(activity.binding.toolbar, R.string.failed_load_profile, Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}
