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
import br.com.monitoratec.loysci_android.databinding.DialogBadgeBinding;
import br.com.monitoratec.loysci_android.databinding.DialogConfirmationBinding;
import br.com.monitoratec.loysci_android.model.Badge;
import br.com.monitoratec.loysci_android.model.Reward;

public class BadgeDialog extends Dialog {
    private Context context;
    private Badge badge;
    private DialogBadgeBinding binding;
    private OnClickListener onClickListener;
    private int quantity;

    public BadgeDialog(@NonNull Context context, Badge badge, int quantity, OnClickListener onClickListener) {
        super(context);
        this.context = context;
        this.badge = badge;
        this.quantity = quantity;
        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_badge, null, false);
        setContentView(binding.getRoot());

        Glide.with(context)
                .load(badge.getImagen())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(binding.imgPromotion);


        binding.txtPromotionTitle.setText(badge.getNombreInsignia());
        binding.txtPromotionSubdescription.setText(badge.getDescripcion());


        binding.btnClose.setOnClickListener(v -> onClickListener.onCancel());

    }

    public interface OnClickListener {
        void onCancel();

        void onGoToCart();
    }
}
