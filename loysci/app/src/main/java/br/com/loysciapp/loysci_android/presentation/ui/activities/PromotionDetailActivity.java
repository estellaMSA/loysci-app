package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityPromotionDetailBinding;
import br.com.loysciapp.loysci_android.model.Promotion;
import br.com.loysciapp.loysci_android.util.Utilities;

import static br.com.loysciapp.loysci_android.model.Promotion.ACTION_TYPE_URL;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_CODE_128;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_CODE_29;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_EAN;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_ITF;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_QR_CODE;
import static br.com.loysciapp.loysci_android.util.Constants.ACTION_TYPE_REGALO;
import static br.com.loysciapp.loysci_android.util.Constants.PROMOTION_PARCELABLE;

public class PromotionDetailActivity extends AppCompatActivity {

    Promotion promotion;
    ActivityPromotionDetailBinding binding;
    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_promotion_detail);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        isRunning = true;
        Bundle bundle = getIntent().getExtras();

        promotion = bundle.getParcelable(PROMOTION_PARCELABLE);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.sales_title));
        populatePromotion();
    }

    private void populatePromotion() {
        binding.txtPromotionTitle.setText(promotion.getEncabezadoArte());
        binding.txtPromotionDescription.setText(promotion.getDetalleArte());
        Glide.with(this)
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
                    binding.btnSeeMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (promotion.getUrlOrCodigo().startsWith("http://") ||
                                    promotion.getUrlOrCodigo().startsWith("https://")) {
                                try {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(promotion.getUrlOrCodigo()));
                                    startActivity(i);
                                } catch (Exception e) {
                                    Toast.makeText(PromotionDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(PromotionDetailActivity.this, R.string.Failed_load_promotion, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
    private void generateQrCode(final BarcodeFormat format, final String data, final int width, final int height) {
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
                binding.getRoot().post(() -> Toast.makeText(PromotionDetailActivity.this, R.string.Failed_load_promotion, Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
