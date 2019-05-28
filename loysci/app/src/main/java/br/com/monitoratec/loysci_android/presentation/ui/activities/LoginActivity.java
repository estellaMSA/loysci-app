package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityLoginBinding;
import br.com.monitoratec.loysci_android.model.AccessToken;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.util.Cpf;
import br.com.monitoratec.loysci_android.util.CustomRetrievePasswordDialog;
import br.com.monitoratec.loysci_android.util.DoneOnEditorActionListener;
import br.com.monitoratec.loysci_android.util.MaskWatcher;
import br.com.monitoratec.loysci_android.util.Prefs;
import br.com.monitoratec.loysci_android.util.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iago Aleksander on 16/10/18.
 */
public class LoginActivity extends AppCompatActivity implements CustomRetrievePasswordDialog.RetrievePasswordListener {

    private final String TAG = this.getClass().getName();
    private ActivityLoginBinding binding;
    private CustomRetrievePasswordDialog retrievePasswordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Prefs.init(this);

        binding.tilUsername.getEditText().setFilters(Utilities.getCpfInputFilter());
        binding.tilUsername.getEditText().addTextChangedListener(MaskWatcher.buildCpf());
        binding.tilUsername.getEditText().setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.tilPassword.requestFocus();
                return true;
            }
            return false;
        });

        binding.loading.loadingText.setText("");

        binding.tilPassword.getEditText().setOnEditorActionListener(new DoneOnEditorActionListener());
        binding.btLogin.setOnClickListener(view -> {

            if (!Cpf.isValid(binding.tilUsername.getEditText().getText().toString())) {
                Toast.makeText(getApplicationContext(), getString(R.string.cpf_error), Toast.LENGTH_SHORT).show();
            }
            else if (binding.tilPassword.getEditText().getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), getString(R.string.password_error), Toast.LENGTH_SHORT).show();
            }
            else {
                signIn(view);
            }

        });
        binding.btRetrievePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrievePasswordDialog = new CustomRetrievePasswordDialog(LoginActivity.this, LoginActivity.this);
                Window window = retrievePasswordDialog.getWindow();
                retrievePasswordDialog.show();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });

        binding.btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterProfileActivity.class));
        });


    }

    private void signIn(View view) {
//            "iceman2@example.com", "Password1$"
        String username = binding.tilUsername.getEditText().getText().toString().replace(".", "").replace("-", "");

        view.setClickable(false);
        setLoadingVisible(true);
        LoyaltyApi.signIn(username, "Mvd$" + binding.tilPassword.getEditText().getText().toString(), new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    Prefs.saveAccessToken(response.body());
//                    updateToken();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Log.e(TAG, "Error signing in - code " + response.code());
                    Toast.makeText(getApplicationContext(), getString(R.string.login_errorlogin), Toast.LENGTH_SHORT).show();
                    view.setClickable(true);
                    setLoadingVisible(false);
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e(TAG, "Error signing in: " + t.getMessage(), t);
                Toast.makeText(getApplicationContext(), getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                view.setClickable(true);
                setLoadingVisible(false);
            }
        });
    }

    public void setLoadingVisible(boolean isVisible){
        if (isVisible) {
            binding.loginContent.setAlpha(0.2f);
            binding.loading.loadingLayout.setVisibility(View.VISIBLE);
        } else {
            binding.loginContent.setAlpha(1f);
            binding.loading.loadingLayout.setVisibility(View.GONE);
        }
    }

    private void updateToken() {
        String inicio = "/************************************UPDATE****************************/";
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, inicio + "\n Token: " + token);
        LoyaltyApi.updateTokenId(token, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailed() {
        retrievePasswordDialog.dismiss();
        Toast.makeText(this, R.string.retrieve_password_error, Toast.LENGTH_SHORT).show();
    }
}
