package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogPurchaseVoucherNotEnoughPointsAlertBinding;

public class ErrorDialog extends Dialog {

    private Context context;
    private int stringRes;
    private OnDismiss onClickListener;
    private DialogPurchaseVoucherNotEnoughPointsAlertBinding binding;

    public ErrorDialog(@NonNull Context context, int stringRes, OnDismiss onClickListener) {
        super(context);
        this.context = context;
        this.stringRes = stringRes;
        this.onClickListener = onClickListener;
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_purchase_voucher_not_enough_points_alert, null, false);
        setContentView(binding.getRoot());

        binding.tvAddDaily.setOnClickListener(v -> {
            dismiss();
            onClickListener.onDismiss();
        });
        binding.tvAlertText.setText(stringRes);
    }

    public interface OnDismiss {
        void onDismiss();
    }
}
