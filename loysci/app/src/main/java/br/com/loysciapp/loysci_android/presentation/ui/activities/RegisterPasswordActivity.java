package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityRegisterPasswordBinding;
import br.com.loysciapp.loysci_android.model.Register;

import static br.com.loysciapp.loysci_android.util.Constants.CARD_TYPE;
import static br.com.loysciapp.loysci_android.util.Constants.CARD_TYPE_DIGITAL;
import static br.com.loysciapp.loysci_android.util.Constants.CARD_TYPE_FISICAL;
import static br.com.loysciapp.loysci_android.util.Constants.REGISTER_PARCELABLE;

public class RegisterPasswordActivity extends AppCompatActivity {

    ActivityRegisterPasswordBinding binding;
    Register register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_password);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.register_new_account);

        register = getIntent().getExtras().getParcelable(REGISTER_PARCELABLE);

        binding.btnSave.setOnClickListener(v -> continueRegistration());

        binding.btnCancel.setOnClickListener(v -> {
            cancelRegistration();
        });
    }

    private void cancelRegistration() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.leave)
                .setMessage(R.string.cancel_registration)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    setResult(RESULT_CANCELED);
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {})
                .setCancelable(false);
        android.app.AlertDialog dialog = builder.create();
        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    private void continueRegistration() {
        if(binding.tietPass.getText().toString().isEmpty()) {
            binding.tietPass.setError(getString(R.string.fill_pass));
            binding.tietPass.requestFocus();
        }else if(binding.tietPass.getText().toString().length()!=6){
            binding.tietPass.setError(getString(R.string.min_digits));
            binding.tietPass.requestFocus();
        }else if(binding.tietRepeatPass.getText().toString().isEmpty()){
            binding.tietRepeatPass.setError(getString(R.string.repeat_pass));
            binding.tietRepeatPass.requestFocus();
        }else if(!binding.tietRepeatPass.getText().toString().equals(binding.tietPass.getText().toString())) {
            binding.tietRepeatPass.setError(getString(R.string.pass_dont_match));
            binding.tietRepeatPass.requestFocus();
        }else{
            register.setContrasena("Mvd$"+binding.tietPass.getText().toString());
            Intent intent = new Intent(this, RegisterConditionsActivity.class);
            intent.putExtra(REGISTER_PARCELABLE,register);
            if(binding.rdbDigital.isChecked())
                intent.putExtra(CARD_TYPE,CARD_TYPE_DIGITAL);
            else
                intent.putExtra(CARD_TYPE,CARD_TYPE_FISICAL);
            startActivityForResult(intent,0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(RESULT_OK);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
