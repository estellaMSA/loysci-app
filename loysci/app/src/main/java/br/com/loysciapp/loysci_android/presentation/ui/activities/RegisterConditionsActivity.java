package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityRegisterConditionsBinding;
import br.com.loysciapp.loysci_android.model.Register;
import br.com.loysciapp.loysci_android.presentation.presenter.RegisterMemberPresenter;

import static br.com.loysciapp.loysci_android.util.Constants.AUTHORIZE_COMMUNICATION;
import static br.com.loysciapp.loysci_android.util.Constants.CARD_TYPE;
import static br.com.loysciapp.loysci_android.util.Constants.IS_NEW_MEMBER;
import static br.com.loysciapp.loysci_android.util.Constants.REGISTER_PARCELABLE;
import static br.com.loysciapp.loysci_android.util.Constants.TELEFONO_MOVIL;

public class RegisterConditionsActivity extends AppCompatActivity {

    ActivityRegisterConditionsBinding binding;
    Register register;
    RegisterMemberPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_conditions);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.register_new_account);

        register = getIntent().getExtras().getParcelable(REGISTER_PARCELABLE);


        binding.txtregistrarcondicoestermos.setText(getTermsString());

        binding.btnSave.setAlpha(0.5f);
        binding.btnSave.setOnClickListener(v -> completeRegistration());
        binding.checkConditions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.btnSave.setAlpha(1f);
            }else{
                binding.btnSave.setAlpha(0.5f);
            }
        });
        binding.btnCancel.setOnClickListener(v -> {
            cancelRegistration();
        });
    }

    private String getTermsString() {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("termos.txt")));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str + "\n");
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private void  completeRegistration() {
        if(binding.checkConditions.isChecked()){
            register.setIndContactoEmail(binding.checkEmail.isChecked());
            register.setIndContactoNotificacion(binding.checkEmail.isChecked());
            register.setIndContactoSms(binding.checkEmail.isChecked());
            presenter = new RegisterMemberPresenter(this);
            binding.loadingRegister.setVisibility(View.VISIBLE);
            binding.termsLayout.setAlpha(0.5f);
            presenter.registerMember(register);
        }else{
            Toast.makeText(this, R.string.terms_needed, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setResult(RESULT_OK);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void showFailedMessage(String message) {
        binding.loadingRegister.setVisibility(View.GONE);
        binding.termsLayout.setAlpha(1f);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showFailedLogin(String message) {
        binding.loadingRegister.setVisibility(View.GONE);
        binding.termsLayout.setAlpha(1f);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED);
        finish();
    }

    public void registerSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CARD_TYPE,getIntent().getExtras().getString(CARD_TYPE));
        intent.putExtra(AUTHORIZE_COMMUNICATION, binding.checkEmail.isChecked());
        intent.putExtra(IS_NEW_MEMBER, true);
        intent.putExtra(TELEFONO_MOVIL, register.getTelefonoMovil());
        startActivity(intent);
        finish();
    }
}
