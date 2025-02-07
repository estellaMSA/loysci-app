package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.UploadDataActivityBinding;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitAnswers;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitResponse;
import br.com.loysciapp.loysci_android.model.ChallengeUploadAnswer;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDataActivity extends AppCompatActivity {
    public static Challenge challenge;
    private static final int CAMERA_REQUEST = 1888;
    private UploadDataActivityBinding binding;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private String image = "";
    int rewardTotal;
    private ImageView imageView4;
    private TextView toolbarText;
    private CardView btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_data_activity);

        btnUpload = (CardView) this.findViewById(R.id.button7);
        imageView4 = (ImageView) this.findViewById(R.id.imageView4);

        toolbarText = (TextView) this.findViewById(R.id.toolbarTitle);

        this.image = getIntent().getStringExtra("Image");

        Bitmap thumbnail = getIntent().getParcelableExtra("Thumbnail");
        Bitmap yourSelectedImage = getIntent().getParcelableExtra("yourSelectedImage");

        if (thumbnail != null) {
            //imageView4.setImageBitmap(thumbnail);
            imageView4.setImageBitmap(resizeImage(this, thumbnail, 500, 500));
        } else {
            imageView4.setImageBitmap(yourSelectedImage);
        }

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbarText.setText(R.string.mission_activities);

        rewardTotal = challenge.getValor();

        includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWinner();
            }
        });

        setTitle("Carregar Imagem");
    }

    private static Bitmap resizeImage(Context context, Bitmap bmpOriginal,
                                      float newWidth, float newHeight) {
        Bitmap novoBmp = null;

        int w = bmpOriginal.getWidth();
        int h = bmpOriginal.getHeight();

        float densityFactor = context.getResources().getDisplayMetrics().density;
        float novoW = newWidth * densityFactor;
        float novoH = newHeight * densityFactor;

        //Calcula escala em percentagem do tamanho original para o novo tamanho
        float scalaW = novoW / w;
        float scalaH = novoH / h;

        // Criando uma matrix para manipulação da imagem BitMap
        Matrix matrix = new Matrix();

        // Definindo a proporção da escala para o matrix
        matrix.postScale(scalaW, scalaH);

        //criando o novo BitMap com o novo tamanho
        novoBmp = Bitmap.createBitmap(bmpOriginal, 0, 0, w, h, matrix, true);

        return novoBmp;
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

    private void sendWinner() {
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

                if (response.isSuccessful() && response.body().getIndEstado().equals("G")) {
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
