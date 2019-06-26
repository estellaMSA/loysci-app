package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityRegisterProfileBinding;
import br.com.monitoratec.loysci_android.model.Register;
import br.com.monitoratec.loysci_android.util.Cpf;
import br.com.monitoratec.loysci_android.util.MaskWatcher;
import br.com.monitoratec.loysci_android.util.Utilities;

import static br.com.monitoratec.loysci_android.util.Constants.REGISTER_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Utilities.isValidEmail;

public class RegisterProfileActivity extends AppCompatActivity {

    ActivityRegisterProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_profile);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.register_new_account);

        binding.tilCpf.getEditText().addTextChangedListener(MaskWatcher.buildCpf());
        binding.tilCpf.getEditText().setFilters(Utilities.getCpfInputFilter());
        binding.tilCellphone.getEditText().addTextChangedListener(MaskWatcher.buildPhone());
        binding.tilCellphone.getEditText().setFilters(Utilities.getCellphoneInputFilter());

        binding.btnSave.setOnClickListener(v -> continueRegistration());
        binding.btnCancel.setOnClickListener(v -> cancelRegistration());
    }

    private void continueRegistration() {
        if(!Cpf.isValid(binding.tietCpf.getText().toString())){
            binding.tietCpf.requestFocus();
            binding.tietCpf.setError(getString(R.string.invalid_doc));
        }else if(binding.tietName.getText().toString().isEmpty()){
            binding.tietName.setError(getString(R.string.invalid_name));
            binding.tietName.requestFocus();
        }else if(binding.tietLastname.getText().toString().isEmpty()){
            binding.tietLastname.setError(getString(R.string.invalid_lastname));
            binding.tietLastname.requestFocus();
        }else if(binding.tietEmail.getText().toString().isEmpty() || !isValidEmail(binding.tietEmail.getText().toString())){
            binding.tietEmail.setError(getString(R.string.invalid_email));
            binding.tietEmail.requestFocus();
        }else if(binding.tietCellphone.getText().toString().isEmpty()) {
            binding.tietCellphone.setError(getString(R.string.invalid_phone));
            binding.tietCellphone.requestFocus();
        }else{
            Register register = new Register();
            register.setNombre(binding.tietName.getText().toString());
            register.setApellido(binding.tietLastname.getText().toString());
            register.setDocIdentificacion(binding.tietCpf.getText().toString().replaceAll("\\D+",""));
            register.setNombreUsuario(binding.tietCpf.getText().toString().replaceAll("\\D+",""));
            register.setCorreo(binding.tietEmail.getText().toString());
            register.setTelefonoMovil(binding.tietCellphone.getText().toString().replaceAll("\\D+",""));
            register.setCodigoIndicacao(binding.tietCodigo.getText().toString());
            Intent intent = new Intent(this, RegisterPasswordActivity.class);
            intent.putExtra(REGISTER_PARCELABLE,register);
            startActivityForResult(intent,0);
        }
    }

    private void cancelRegistration() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.leave)
                .setMessage(R.string.cancel_registration)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            setResult(RESULT_CANCELED);
            finish();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
