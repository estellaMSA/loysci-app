package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityEditAddressBinding;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.presentation.presenter.EditAddressPresenter;
import br.com.loysciapp.loysci_android.util.AddressUtil;
import br.com.loysciapp.loysci_android.util.MaskWatcher;
import br.com.loysciapp.loysci_android.util.Utilities;

import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;

public class EditAddressActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public ActivityEditAddressBinding binding;
    private EditAddressPresenter presenter;
    private Profile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_address);

        configToolbar();
        configStateSpinner();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getParcelable(PROFILE_PARCELABLE) != null) {
                userProfile = bundle.getParcelable(PROFILE_PARCELABLE);
                populateViewsWithUserProfile();
            }
        }

        //presenter = new EditAddressPresenter(this, binding);
        //presenter = new EditAddressPresenter(this, binding);

        binding.endButtons.btnSave.setOnClickListener(this);
        binding.endButtons.btnCancel.setOnClickListener(this);

        binding.zipcode.getEditText().setFilters(Utilities.getCepInputFilter());
        binding.zipcode.getEditText().addTextChangedListener(MaskWatcher.buildZipcode());
    }

    private void populateViewsWithUserProfile() {

        binding.zipcode.getEditText().setText(MaskWatcher.addMask(userProfile.recoverZipcode(),"#####-###"));

        binding.thoroughfare.getEditText().setText(userProfile.recoverThoroughfare());

        binding.number.getEditText().setText(userProfile.recoverNumber());

        binding.complement.getEditText().setText(userProfile.recoverComplement());

        binding.neighborhood.getEditText().setText(userProfile.recoverNeighborhood());

        binding.city.getEditText().setText(userProfile.recoverCity());

        if (userProfile.recoverState() != null) {
            binding.state.setSelection(AddressUtil
                    .getArrayPositionByInitials(userProfile.recoverState(), this));
        }
    }

    private void configStateSpinner() {

        binding.state.setOnItemSelectedListener(this);

        ArrayAdapter<String> spinnerArrayAdapter;
        spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.states)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(0, 0, 0, 0);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.inactive_text));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.primary_text));
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.state.setAdapter(spinnerArrayAdapter);
    }

    private void configToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveAddress();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void saveAddress() {
        presenter.saveAddress();
    }

    public Profile populateUserProfileWithInputs(Profile profile) {
        profile.defineZipcode(binding.zipcode.getEditText().getText().toString().replace("-",""));
        profile.defineThoroughfare(binding.thoroughfare.getEditText().getText().toString());
        profile.defineNumber(binding.number.getEditText().getText().toString());
        profile.defineComplement(binding.complement.getEditText().getText().toString());
        profile.defineNeighborhood(binding.neighborhood.getEditText().getText().toString());
        profile.defineCity(binding.city.getEditText().getText().toString());
        String state = AddressUtil.convertFullStateNameToInitials(binding.state.getSelectedItem().toString(), this);
        profile.defineState(state);
        return profile;
    }

}
