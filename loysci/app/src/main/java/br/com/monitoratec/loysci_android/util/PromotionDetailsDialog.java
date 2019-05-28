package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogPromotionDetailsBinding;
import br.com.monitoratec.loysci_android.model.Promotion;

import static br.com.monitoratec.loysci_android.model.Promotion.ACTION_TYPE_URL;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_CODE_128;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_CODE_29;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_EAN;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_ITF;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_QR_CODE;
import static br.com.monitoratec.loysci_android.util.Constants.ACTION_TYPE_REGALO;

public class PromotionDetailsDialog extends Dialog {
    private OnButtonClicked buttonClicked;
    private DialogPromotionDetailsBinding binding;
    private Promotion promotion;
    Context context;
    boolean isRunning = false;

    public PromotionDetailsDialog(@NonNull Context context, Promotion promotion, OnButtonClicked buttonClicked) {
        super(context);
        this.context = context;
        this.buttonClicked = buttonClicked;
        this.promotion = promotion;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_promotion_details, null, false);
        setContentView(binding.getRoot());
        isRunning = true;
        this.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        binding.btnClose.setOnClickListener((v)->this.dismiss());
        setListener();
        populatePromotion();
    }



    private void populatePromotion() {
        binding.txtPromotionTitle.setText(promotion.getEncabezadoArte());

        if (promotion.getSubencabezadoArte() != null && !promotion.getSubencabezadoArte().isEmpty()) {
            binding.txtPromotionSubdescription.setText(promotion.getSubencabezadoArte());
        } else {
            binding.txtPromotionSubdescription.setVisibility(View.GONE);
        }
        binding.txtPromotionDescription.setText(promotion.getDetalleArte());
        binding.txtPromotionDescription.setMovementMethod(new ScrollingMovementMethod());
        Glide.with(context)
                .load(promotion.getImagenArte())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(binding.imgPromotion);
        validateType();
    }

    private void validateType(){
        if(promotion.getIndTipoAccion()!=null) {
            switch (promotion.getIndTipoAccion()) {
                case ACTION_TYPE_URL:
                    binding.loadingPromotion.setVisibility(View.GONE);
                    binding.btnSeeMore.setVisibility(View.VISIBLE);
                    binding.btnSeeMore.setOnClickListener(v -> buttonClicked.seeMore(promotion.getUrlOrCodigo()));
                    break;
                case ACTION_TYPE_QR_CODE:
                    generateQrCode(
                            BarcodeFormat.QR_CODE,
                            promotion.getUrlOrCodigo(),
                            (int) Utilities.dpToPx(150),
                            (int) Utilities.dpToPx(150));
                    break;
                case ACTION_TYPE_ITF:
                    generateQrCode(
                            BarcodeFormat.ITF,
                            promotion.getUrlOrCodigo(),
                            (int) Utilities.dpToPx(250),
                            (int) Utilities.dpToPx(100));
                    break;
                case ACTION_TYPE_EAN:
                    generateQrCode(
                            BarcodeFormat.EAN_13,
                            promotion.getUrlOrCodigo(),
                            (int) Utilities.dpToPx(250),
                            (int) Utilities.dpToPx(100));
                    break;
                case ACTION_TYPE_CODE_128:
                    generateQrCode(
                            BarcodeFormat.CODE_128,
                            promotion.getUrlOrCodigo(),
                            (int) Utilities.dpToPx(250),
                            (int) Utilities.dpToPx(100));
                    break;
                case ACTION_TYPE_CODE_29:
                    generateQrCode(
                            BarcodeFormat.CODE_39,
                            promotion.getUrlOrCodigo(),
                            (int) Utilities.dpToPx(250),
                            (int) Utilities.dpToPx(100));
                    break;

                case ACTION_TYPE_REGALO:
                    binding.qrLayout.setVisibility(View.VISIBLE);
                    binding.loadingPromotion.setVisibility(View.GONE);
                    break;
                default:
                    binding.loadingPromotion.setVisibility(View.GONE);
            }
        }
        else
        {
            binding.loadingPromotion.setVisibility(View.GONE);
        }
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public void setListener(){
        setOnDismissListener(dialog -> isRunning = false);
    }

    private void generateQrCode(final BarcodeFormat format,  final String data, final int width, final int height) {
        new Thread(() -> {
            try {
                Writer writer = new MultiFormatWriter();
                BitMatrix bm = writer.encode(Uri.encode(data), format, width, height);
                final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        bitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.TRANSPARENT);
                    }
                }
                if (isRunning) {
                    binding.getRoot().post(() -> {
                        binding.imgQr.setImageBitmap(bitmap);
                        binding.txtCode.setText(promotion.getUrlOrCodigo());
                        binding.qrLayout.setVisibility(View.VISIBLE);
                        binding.loadingPromotion.setVisibility(View.GONE);
                    });
                }
            } catch (final Exception e) {
                binding.getRoot().post(() -> binding.loadingPromotion.setVisibility(View.GONE));
                binding.getRoot().post(() -> Toast.makeText(context, R.string.Failed_load_promotion, Toast.LENGTH_LONG).show());
            }
        }).start();
    }
    public interface OnButtonClicked {
        void seeMore(String urlOrCodigo);
    }
}
