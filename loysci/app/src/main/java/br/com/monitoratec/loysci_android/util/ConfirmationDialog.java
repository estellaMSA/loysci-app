package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogConfirmationBinding;
import br.com.monitoratec.loysci_android.model.Reward;

public class ConfirmationDialog extends Dialog {
    private Context context;
    private Reward voucher;
    private DialogConfirmationBinding binding;
    private OnClickListener onClickListener;
    private int quantity;

    public ConfirmationDialog(@NonNull Context context, Reward reward, int quantity, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.voucher = reward;
        this.quantity = quantity;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_confirmation, null, false);
        setContentView(binding.getRoot());

        Glide.with(context)
                .load(voucher.getImagenArte())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(binding.ivImgVoucher);


        binding.tvVoucherName.setText(voucher.getEncabezadoArte());
        if (quantity == 1) {
            binding.tvSelectQuantityLabel.setText(context.getString(R.string.item_added));
            binding.tvVoucherQuantity.setText(context.getString(R.string.quantity_add_voucher, quantity));
        } else {
            binding.tvSelectQuantityLabel.setText(context.getString(R.string.item_added_plural));
            binding.tvVoucherQuantity.setText(context.getString(R.string.quantity_add_voucher_plural, quantity));
        }

        binding.tvCancel.setOnClickListener(v -> onClickListener.onCancel());
        binding.tvConfirm.setOnClickListener(v -> onClickListener.onGoToCart());
    }

    public interface OnClickListener {
        void onCancel();

        void onGoToCart();
    }
}
