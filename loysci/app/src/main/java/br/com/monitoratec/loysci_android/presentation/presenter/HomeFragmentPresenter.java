package br.com.monitoratec.loysci_android.presentation.presenter;

import android.app.Activity;
import android.content.Intent;

import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.presentation.ui.activities.EditAddressActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.MainActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.ProfileActivity;
import br.com.monitoratec.loysci_android.util.Constants;

public class HomeFragmentPresenter {


    public void goToUserData(MainActivity activity, Profile profile) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        intent.putExtra(Constants.PROFILE_PARCELABLE, profile);
        activity.startActivity(intent);
    }

    public void goToUserData(Activity activity) {
        Intent intent = new Intent(activity, ProfileActivity.class);
        activity.startActivity(intent);
    }

    public void goToUserAddress(Activity activity, Profile profile){
        Intent intent = new Intent(activity, EditAddressActivity.class);
        intent.putExtra(Constants.PROFILE_PARCELABLE, profile);
        activity.startActivity(intent);
    }
}
