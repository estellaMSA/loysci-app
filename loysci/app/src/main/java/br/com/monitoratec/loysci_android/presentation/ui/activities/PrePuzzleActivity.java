package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivitySubirconteudoBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import br.com.monitoratec.loysci_android.util.CustomPhotoPickerDialog;

import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;

public class PrePuzzleActivity extends AppCompatActivity implements SimpleItemClickListener {
    String content = "code";
    private CardView btnSend;
    private TextView subencabezado;
    private TextView puntos;
    private ImageView imageView;
    private TextView toolbarText;
    private TextView txtCompleteToWin;
    private TextView txtTopicTitle;
    private TextView txtMissionTitle;
    private TextView txtActivitiesCount;
    private ImageView imgPhoto;
    public static Challenge challenge;

    private CustomPhotoPickerDialog photoDialog;

    Bitmap thumbnail;
    private String image = "";
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 2888;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 4;

    ActivitySubirconteudoBinding binding;
    Mission mission;

    int missionIndex;
    String idMember;
    int completedCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subirconteudo);

        mission = getIntent().getParcelableExtra(MISSION_PARCELABLE);

        List<Challenge> challenges = mission.getChallenges();
        challenge = challenges.get(0);

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        toolbarText = (TextView) this.findViewById(R.id.toolbarTitle);
        imageView = (ImageView) this.findViewById(R.id.ivImage);
        imgPhoto = (ImageView) this.findViewById(R.id.img_mission2);
        btnSend = (CardView) this.findViewById(R.id.btnChallenge);
        subencabezado = (TextView) this.findViewById(R.id.tvDescription);
        puntos = (TextView) this.findViewById(R.id.puntosPromo);
        txtCompleteToWin = (TextView) findViewById(R.id.txt_complete_to_win);
        txtTopicTitle = (TextView) findViewById(R.id.txt_topic_title);
        txtMissionTitle = (TextView) findViewById(R.id.txt_mission_title);
        txtActivitiesCount = (TextView) findViewById(R.id.txt_activities_count);

        subencabezado.setText(challenge.getDescripcion());
        puntos.setText(String.valueOf(challenge.getValor())+" "+challenge.getMetrica().getNombre());

        toolbarText.setText(R.string.mission_activities);
        txtCompleteToWin.setText(R.string.complete_to_win_this);
        txtMissionTitle.setText(mission.getTitulo());
        txtTopicTitle.setText(mission.getTitulo());

        Glide.with(this)
                .load(challenge.getImagen())
                .crossFade()
                .into(imageView);

        if (challenge.getIndTipoMision() != null) {
            if (challenge.getIndTipoMision().equals(Challenge.TYPE_GAMES)) {
                //btnSend.setText("Carregar Imagem");
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EntrarPuzzle();
                    }
                });
            }
        }

        //setTitle(challenge.getEncabezadoArte());
    }

    private void EntrarPuzzle() {
        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSimpleItemClick(Object object) {

    }
}
