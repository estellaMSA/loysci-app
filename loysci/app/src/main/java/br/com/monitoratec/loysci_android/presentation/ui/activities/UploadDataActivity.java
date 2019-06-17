package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.UploadDataActivityBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.ChallengeUploadAnswer;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.QuizViewModel;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import br.com.monitoratec.loysci_android.util.MissionEndDialog;
import br.com.monitoratec.loysci_android.util.QuizEndDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.MISSION_FINISHED;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_RIGHT;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_WRONG;

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

        if (thumbnail != null) {
            imageView4.setImageBitmap(thumbnail);
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
