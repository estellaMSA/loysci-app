package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogRemoveVoucherConfirmationBinding;
import br.com.monitoratec.loysci_android.model.Reward;

public class RemoveVoucherDialog extends Dialog {
    DialogRemoveVoucherConfirmationBinding binding;
    private Reward voucher;
    private Context context;
    private OnClickListener onClickListener;

    public RemoveVoucherDialog(@NonNull Context context, Reward voucher, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.voucher = voucher;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_remove_voucher_confirmation, null, false);
        setContentView(binding.getRoot());

        binding.tvVoucherName.setText(voucher.getEncabezadoArte());

        Glide.with(context)
                .load(voucher.getImagenArte())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.ivImgVoucher);

//
//        if (voucher.getEncabezadoArte().toLowerCase().contains("zattini")) {
//            binding.ivImgLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_zattini));
//            binding.ivImgLogo.setVisibility(View.VISIBLE);
//        } else if (voucher.getEncabezadoArte().toLowerCase().contains("netshoes")) {
//            binding.ivImgLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_netshoes));
//            binding.ivImgLogo.setVisibility(View.VISIBLE);
//        } else {
//            binding.ivImgLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_movida));
//            binding.ivImgLogo.setVisibility(View.VISIBLE);
//        }

        binding.ivImgLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_movida));
        binding.ivImgLogo.setVisibility(View.GONE);


        binding.tvCancel.setOnClickListener(v -> onClickListener.onCancel());

        binding.tvRemove.setOnClickListener(v -> onClickListener.onRemove());
    }

    public interface OnClickListener {
        void onCancel();
        void onRemove();
    }
}
