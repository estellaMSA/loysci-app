package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityUpdateDriversLicenseBinding;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.model.ProfileDynamicAttribute;
import br.com.loysciapp.loysci_android.presentation.ui.presenters.UpdateDriversLicensePresenter;

import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;

public class UpdateDriversLicenseActivity extends AppCompatActivity {

    ActivityUpdateDriversLicenseBinding binding;
    Profile profile;
    UpdateDriversLicensePresenter presenter;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_drivers_license);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        presenter = new UpdateDriversLicensePresenter(this, binding);
        binding.includeToolbar.toolbarTitle.setText(R.string.update_drivers_license);
        Bundle bundle = getIntent().getExtras();

        profile = bundle.getParcelable(PROFILE_PARCELABLE);

        populateData();


        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    binding.tilExpiration.getEditText().setText(current);
                    binding.tilExpiration.getEditText().setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        binding.tilExpiration.getEditText().addTextChangedListener(tw);
        binding.endButtons.btnCancel.setOnClickListener(v -> finish());
        binding.endButtons.btnSave.setOnClickListener(v -> validateFields());

        binding.tilExpiration.getEditText().setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateDriversLicenseActivity.this, dateDialog, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
    }

    private void populateData() {
        for (ProfileDynamicAttribute profileDynamicAttribute : profile.getAtributoDinamicos()) {
            if (profileDynamicAttribute.getValorAtributoMiembro() != null) {
                String name = profileDynamicAttribute.getNombre();
                if (name != null && name.equals("CNH")) {
                    binding.tilLicenseNumber.getEditText().setText(profileDynamicAttribute.getValorAtributoMiembro());
                } else if (name != null && name.equals("CNH Validade")) {
                    Calendar expiration = Calendar.getInstance();
                    expiration.setTime(new Timestamp(Long.valueOf(profileDynamicAttribute.getValorAtributoMiembro())));
                    binding.tilExpiration.getEditText().setText(sdf.format(expiration.getTime()));
                }
            }
        }

        myCalendar = Calendar.getInstance();
        dateDialog = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            binding.tilExpiration.getEditText().setText(sdf.format(calendar.getTime()));
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void validateFields() {
        String license = binding.tilLicenseNumber.getEditText().getText().toString();

        if (license.isEmpty()) {
            binding.tilLicenseNumber.getEditText().setError(getString(R.string.empty_field_error));
            binding.tilLicenseNumber.getEditText().requestFocus();
        }
        else if (binding.tilExpiration.getEditText().getText().length() > 0 && binding.tilLicenseNumber.getEditText().getText().length() > 0) {

            String expiration = binding.tilExpiration.getEditText().getText().toString();

            Calendar licenseCalendar = Calendar.getInstance();

            licenseCalendar.setTimeZone(TimeZone.getDefault());

            try {
                binding.licenseLayout.setAlpha(0.2f);
                binding.loadingLayout.loadingText.setText(getString(R.string.updating_data));
                binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);
                licenseCalendar.setTime(new SimpleDateFormat("dd/MM/yyyy")
                        .parse(expiration));

                licenseCalendar.set(Calendar.HOUR_OF_DAY, licenseCalendar.getMinimum(Calendar.HOUR_OF_DAY));
                licenseCalendar.set(Calendar.MINUTE, licenseCalendar.getMinimum(Calendar.MINUTE));
                licenseCalendar.set(Calendar.SECOND, licenseCalendar.getMinimum(Calendar.SECOND));
                licenseCalendar.set(Calendar.MILLISECOND, licenseCalendar.getMinimum(Calendar.MILLISECOND));

                presenter.updateDriversLicense(license, String.valueOf(licenseCalendar.getTimeInMillis()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
