package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ChallengeSeeContentActivityBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSeeContent;
import br.com.monitoratec.loysci_android.model.ChallengeSeeContentAnswer;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.CHALLENGE_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Constants.ID_MISSION;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;

public class ChallengeSeeContentActivity extends AppCompatActivity {
    private ChallengeSeeContentActivityBinding binding;
    //private Realm realm;
    private Challenge challenge;
    String idMission;
    Mission mission;

    private TextView toolbarText;
    private TextView tvTitle;
    private TextView tvURL;
    private CardView bSubmit;
    private ImageView ivImage;
    private ImageView ivImageMission;
    private TextView txtCompleteToWin;
    private TextView txtTopicTitle;
    private TextView puntosPromo;
    private TextView txtMissionTitle;
    private ImageView ivImgMission2;
    private TextView txtActivitiesCount;

    int completedCounter = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_see_content_activity);

        //Código Antigo
        //realm = Realm.getDefaultInstance();
        //challenge = (Challenge.class).equalTo("idMision", getIntent().getStringExtra("challenge-id")).findFirst();

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        mission = getIntent().getParcelableExtra(MISSION_PARCELABLE);
        List<Challenge> challenges = mission.getChallenges();
        challenge = challenges.get(0);

        toolbarText = (TextView) this.findViewById(R.id.toolbarTitle);
        tvTitle = (TextView) this.findViewById(R.id.tvTitle);
        tvURL = (TextView) this.findViewById(R.id.tvURL);
        bSubmit = (CardView) this.findViewById(R.id.bSubmit);
        ivImage = (ImageView) this.findViewById(R.id.ivImage);
        ivImageMission = (ImageView) this.findViewById(R.id.ivImageMission);
        txtCompleteToWin = (TextView) this.findViewById(R.id.txt_complete_to_win);
        txtTopicTitle = (TextView) this.findViewById(R.id.txt_topic_title);
        puntosPromo = (TextView) this.findViewById(R.id.puntosPromo);
        txtMissionTitle = (TextView) this.findViewById(R.id.txt_mission_title);
        ivImgMission2 = (ImageView) this.findViewById(R.id.img_mission2);
        txtActivitiesCount = (TextView) this.findViewById(R.id.txt_activities_count);

        puntosPromo.setText(String.valueOf(challenge.getValor())+" "+challenge.getMetrica().getNombre());
        txtCompleteToWin.setText(R.string.complete_to_win_this);
        txtTopicTitle.setText(mission.getTitulo());
        txtMissionTitle.setText(mission.getTitulo());
        ivImgMission2.setImageDrawable(this.getDrawable(R.drawable.ic_camera));
        toolbarText.setText(R.string.mission_activities);

        Glide.with(this)
                .load(challenge.getImagen())
                .crossFade()
                .into(ivImageMission);

        updateCompletedCounter();

        final ChallengeSeeContent content = challenge.getMisionVerContenido();
        tvTitle.setText(content.getTexto());
        switch (content.getIndTipo()) {
            case ChallengeSeeContent.CONTENT_TYPE_VIDEO:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(content.getUrl())));
                bSubmit.setEnabled(true);

//        binding.vvVideo.setVisibility(View.VISIBLE);
//        binding.vvVideo.setVideoURI(Uri.parse(content.getUrl()));
//        binding.vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//          @Override
//          public void onCompletion(MediaPlayer mp) {
//            binding.bSubmit.setEnabled(true);
//          }
//        });
//        binding.vvVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//          @Override
//          public boolean onError(MediaPlayer mp, int what, int extra) {
//            binding.bSubmit.setEnabled(true);
//            return true;
//          }
//        });
                break;
            case ChallengeSeeContent.CONTENT_TYPE_IMAGE:
                ivImage.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(content.getUrl())
                        .crossFade()
                        .into(ivImage);
                bSubmit.setEnabled(true);
                break;
            case ChallengeSeeContent.CONTENT_TYPE_URL:
                tvURL.setVisibility(View.VISIBLE);
                tvURL.setText(content.getUrl());
                tvURL.setMovementMethod(LinkMovementMethod.getInstance());
                tvURL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(content.getUrl()));
                        startActivity(intent);
                        bSubmit.setEnabled(true);
                    }
                });
                break;
        }
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
                submitAnswers.setRespuestaVerContenido(new ChallengeSeeContentAnswer());
                LoyaltyApi.submitChallenge(challenge, submitAnswers, new Callback<ChallengeSubmitResponse>() {
                    @Override
                    public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChallengeSeeContentActivity.this, R.string.challenge_submitted_successfully, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChallengeSeeContentActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {
                        Toast.makeText(ChallengeSeeContentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void updateCompletedCounter() {

        completedCounter = 0;
        for (Challenge challenge : mission.getChallenges()) {
            if (challenge.getValor() != 0 && challenge.isCompleted()) completedCounter++;
        }
        txtActivitiesCount.setText(completedCounter + "/" + mission.getChallenges().size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (realm != null) {
        //    realm.close();
        //}
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
}
