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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogSelectQuantityVoucherBinding;
import br.com.monitoratec.loysci_android.model.Reward;

public class AddVoucherDialog extends Dialog {
    DialogSelectQuantityVoucherBinding binding;
    private Reward voucher;
    private Context context;
    private int quantity = 1;
    private OnClickListener onClickListener;

    public AddVoucherDialog(@NonNull Context context, Reward voucher, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.voucher = voucher;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_select_quantity_voucher, null, false);
        setContentView(binding.getRoot());

        binding.tvVoucherName.setText(voucher.getEncabezadoArte());
        binding.tvQuantity.setText(String.valueOf(quantity));

        Glide.with(context)
                .load(voucher.getImagenArte())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        binding.ivImageVoucherLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        binding.ivImageVoucherLoading.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(binding.ivImgVoucher);


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
        binding.tvConfirm.setOnClickListener(v -> {
            v.setClickable(false);
            onClickListener.onConfirm(quantity);
        });

        binding.buttonAddLayout.setOnClickListener(v -> {
            if(quantity < 27) {
                quantity++;
                binding.tvQuantity.setText(String.valueOf(quantity));
                onClickListener.onAdd();
            }
        });
        binding.buttonRemoveLayout.setOnClickListener(v -> {
            if(quantity > 1){
                quantity--;
                binding.tvQuantity.setText(String.valueOf(quantity));
                onClickListener.onRemove();
            }
        });
    }

    public interface OnClickListener {
        void onAdd();

        void onRemove();

        void onCancel();

        void onConfirm(int quantity);
    }
}
