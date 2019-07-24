package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogRetrievePasswordBinding;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Felipe Galeote on 01,Novembro,2018
 */
public class CustomRetrievePasswordDialog extends Dialog {

    public interface RetrievePasswordListener {
        void onSuccess();

        void onFailed();
    }

    private DialogRetrievePasswordBinding binding;
    private RetrievePasswordListener listener;

    public CustomRetrievePasswordDialog(@NonNull Context context, RetrievePasswordListener listener) {
        super(context, R.style.MyDialogTheme);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_retrieve_password, null, false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        binding.retrievePasswordConfirmBtn.setOnClickListener(view -> {
            String email = binding.retrievePasswordEmail.getEditText().getText().toString().trim();
            if (!email.isEmpty()) {
                LoyaltyApi.setForgot(email, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            onSuccess();
                        } else {
                            listener.onFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        listener.onFailed();
                    }
                });
            } else {
                binding.retrievePasswordEmail.getEditText().setError(getContext().getString(R.string.email_error));
            }
        });
        binding.retrievePasswordGoBackBtn.setOnClickListener(view -> dismiss());
    }

    public void onSuccess() {
        binding.retrievePasswordInstructions.setVisibility(View.INVISIBLE);
        binding.retrievePasswordEmail.setVisibility(View.INVISIBLE);
        binding.retrievePasswordConfirmBtn.setVisibility(View.INVISIBLE);

        binding.retrievePasswordDoneIv.setVisibility(View.VISIBLE);
        binding.retrievePasswordSentTitle.setVisibility(View.VISIBLE);
        binding.retrievePasswordSentDescription.setVisibility(View.VISIBLE);
    }
}
