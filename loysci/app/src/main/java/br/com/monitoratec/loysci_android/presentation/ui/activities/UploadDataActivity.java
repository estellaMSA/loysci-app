package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.UploadDataActivityBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.ChallengeUploadAnswer;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDataActivity extends AppCompatActivity {
    public static Challenge challenge;
    private static final int CAMERA_REQUEST = 1888;
    private UploadDataActivityBinding binding;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private String image = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.upload_data_activity);

        this.image = getIntent().getStringExtra("Image");

        Bitmap thumbnail = getIntent().getParcelableExtra("Thumbnail");

        if (thumbnail != null) {
            binding.imageView4.setImageBitmap(thumbnail);
        }
        setSupportActionBar(binding.includeToolbar.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.includeToolbar.toolbarTitle.setText(challenge.getEncabezadoArte());

        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        binding.button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWinner();
            }
        });

        setTitle("Carregar Imagem");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    private void sendWinner(){
        final ProgressDialog dialog = ProgressDialog.show(UploadDataActivity.this, "",
                "Por favor, aguarde", true);

        ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
        ChallengeUploadAnswer challengeAnswer = new ChallengeUploadAnswer();
        challengeAnswer.setDatosContenido(image);
        submitAnswers.setRespuestaSubirContenido(challengeAnswer);
        LoyaltyApi.submitChallenge(challenge, submitAnswers, new Callback<ChallengeSubmitResponse>() {
            @Override
            public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {
                dialog.dismiss();

                if (response.isSuccessful()) {
                    finish();

                    Toast.makeText(UploadDataActivity.this, R.string.success_subir_conteudo, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadDataActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {
                dialog.dismiss();

                Toast.makeText(UploadDataActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
