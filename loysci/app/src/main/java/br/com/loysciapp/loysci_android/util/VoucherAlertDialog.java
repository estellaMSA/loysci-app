package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogVoucherAlertBinding;
import br.com.loysciapp.loysci_android.model.Reward;

public class VoucherAlertDialog extends Dialog {
    public enum Type {DIARY, CONTRACT}

    private Reward voucher;
    private Context context;
    private OnClickListener onClickListener;
    private DialogVoucherAlertBinding binding;
    private Type type;

    public VoucherAlertDialog(@NonNull Context context, Reward voucher, Type type, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.voucher = voucher;
        this.type = type;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_voucher_alert, null, false);
        setContentView(binding.getRoot());

        if(type == Type.CONTRACT) {
            binding.tvAlertText.setText(context.getString(R.string.alert_only_one_contract));
        } else if(type == Type.DIARY) {
            binding.tvAlertText.setText(context.getString(R.string.alert_only_one_daily));
        }

        binding.tvCancelOption.setOnClickListener(v -> onClickListener.onCancel());

        binding.tvConfirmOption.setOnClickListener(v -> onClickListener.onConfirm());
    }

    public interface OnClickListener {
        void onCancel();

        void onConfirm();
    }
}
