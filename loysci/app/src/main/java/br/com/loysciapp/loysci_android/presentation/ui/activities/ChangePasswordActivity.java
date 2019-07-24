package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityChangePasswordBinding;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.presentation.ui.presenters.ChangePasswordPresenter;

import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    ChangePasswordPresenter presenter;
    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        presenter = new ChangePasswordPresenter(this, binding);
        binding.includeToolbar.toolbarTitle.setText(R.string.update_password);

        Bundle bundle = getIntent().getExtras();

        profile = bundle.getParcelable(PROFILE_PARCELABLE);

        binding.endButtons.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.endButtons.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private boolean validateFields() {
        String currentPass = binding.tilCurrentPass.getEditText().getText().toString();
        String newPass = binding.tilNewPass.getEditText().getText().toString();
        String repeatPass = binding.tilRepeatPass.getEditText().getText().toString();

        if (currentPass.isEmpty()) {
            binding.tilCurrentPass.getEditText().setError(getString(R.string.fill_current_pass));
            binding.tilCurrentPass.getEditText().requestFocus();
        } else if (newPass.equals(repeatPass)) {
            if (newPass.length() == 6) {
                binding.tilRepeatPass.getEditText().setError(null);
                binding.changePassLayout.setAlpha(0.2f);
                binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);
                presenter.changePassword(currentPass, newPass, profile);
            } else {
                binding.tilRepeatPass.getEditText().setError(getString(R.string.pass_too_small));
                binding.tilRepeatPass.getEditText().requestFocus();
            }
        } else {
            binding.tilRepeatPass.getEditText().setError(getString(R.string.pass_doesnt_match));
            binding.tilRepeatPass.getEditText().requestFocus();
        }
        return false;
    }
}
