package br.com.loysciapp.loysci_android.presentation.ui.activities;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.Collections;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.model.ChallengeSeeContentAnswer;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitAnswers;
import br.com.loysciapp.loysci_android.model.ChallengeSubmitResponse;
import br.com.loysciapp.loysci_android.model.Mission;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.loysciapp.loysci_android.util.Constants.MISSION_PARCELABLE;


public class PuzzleActivity extends AppCompatActivity {

    private RelativeLayout absoluteLayout;
    private TextView moveCounter;
    private TextView feedbackText;
    private Button[] buttons;
    private Boolean bad_move=false;
    public static Challenge challenge;
    private static final Integer[] goal = new Integer[] {0,1,2,3,4,5,6,7,8};
    private ArrayList<Integer> cells = new ArrayList<Integer>();
    private RelativeLayout relativeLayout;
    private ImageView imageView;
    private TextView textCount;
    private TextView textViewMessage;
    private TextView toolbarText;
    private TextView txtTopicTitle;

    Mission mission;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        mission = getIntent().getParcelableExtra(MISSION_PARCELABLE);

        textCount = (TextView) this.findViewById(R.id.textView5);
        absoluteLayout = (RelativeLayout) this.findViewById(R.id.GameField);
        relativeLayout = (RelativeLayout) this.findViewById(R.id.relativeLayout);
        imageView = (ImageView) this.findViewById(R.id.imageIcon);
        textViewMessage = (TextView) this.findViewById(R.id.textView7);
        toolbarText = (TextView) this.findViewById(R.id.toolbarTitle);
        toolbarText.setText(R.string.mission_activities);

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        buttons = findButtons();
        for(int i=0;i<9;i++)
        {
            this.cells.add(i);
        }
        Collections.shuffle(this.cells); //random cells array

        fill_grid();
        setImages();
        moveCounter = (TextView) findViewById(R.id.MoveCounter);
        feedbackText = (TextView) findViewById(R.id.FeedbackText);

        for (int i = 1; i < 9; i++) {
            buttons[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    makeMove((Button) v);
                }
            });
        }

        moveCounter.setText("0");
        feedbackText.setText("Não há comentários.");
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

    public Button[] findButtons() {
        Button[] b = new Button[9];

        b[0] = (Button) findViewById(R.id.Button00);
        b[1] = (Button) findViewById(R.id.Button01);
        b[2] = (Button) findViewById(R.id.Button02);
        b[3] = (Button) findViewById(R.id.Button03);
        b[4] = (Button) findViewById(R.id.Button04);
        b[5] = (Button) findViewById(R.id.Button05);
        b[6] = (Button) findViewById(R.id.Button06);
        b[7] = (Button) findViewById(R.id.Button07);
        b[8] = (Button) findViewById(R.id.Button08);
        return b;
    }

    public void makeMove(final Button b) {
        bad_move=true;
        int b_text,b_pos,zuk_pos;
        b_text=Integer.parseInt((String) b.getText());
        b_pos=find_pos(b_text);
        zuk_pos=find_pos(0);
        switch(zuk_pos)
        {
            case(0):
                if(b_pos==1||b_pos==3)
                    bad_move=false;
                break;
            case(1):
                if(b_pos==0||b_pos==2||b_pos==4)
                    bad_move=false;
                break;
            case(2):
                if(b_pos==1||b_pos==5)
                    bad_move=false;
                break;
            case(3):
                if(b_pos==0||b_pos==4||b_pos==6)
                    bad_move=false;
                break;
            case(4):
                if(b_pos==1||b_pos==3||b_pos==5||b_pos==7)
                    bad_move=false;
                break;
            case(5):
                if(b_pos==2||b_pos==4||b_pos==8)
                    bad_move=false;
                break;
            case(6):
                if(b_pos==3||b_pos==7)
                    bad_move=false;
                break;
            case(7):
                if(b_pos==4||b_pos==6||b_pos==8)
                    bad_move=false;
                break;
            case(8):
                if(b_pos==5||b_pos==7)
                    bad_move=false;
                break;
        }

        if(bad_move==true)
        {
            feedbackText.setText("Movimento Não Permitido");
            return;
        }
        feedbackText.setText("Ok");
        cells.remove(b_pos);
        cells.add(b_pos, 0);
        cells.remove(zuk_pos);
        cells.add(zuk_pos,b_text);


        fill_grid();
        moveCounter.setText(Integer.toString(Integer.parseInt((String) moveCounter.getText())+1));

        for(int i=0;i<9;i++)
        {
            if(cells.get(i)!=goal[i])
            {
                return;
            }
        }
        textViewMessage.setText("Vencedor!");
        Toast.makeText(this, "Vencedor", Toast.LENGTH_SHORT).show();
        absoluteLayout.setVisibility(View.GONE);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.winnergif).into(imageViewTarget);
        imageView.setImageDrawable(getDrawable(R.drawable.celebracion));
        relativeLayout.setVisibility(View.VISIBLE);
        sendWinner();
    }

    public void fill_grid()
    {
        for(int i=0;i<9;i++)
        {
            int text=cells.get(i);
            RelativeLayout.LayoutParams absParams =
                    (RelativeLayout.LayoutParams)buttons[text].getLayoutParams();
            switch(i)
            {case(0):

                absParams.leftMargin = 5;
                absParams.topMargin = 5;
                buttons[text].setLayoutParams(absParams);
                break;
                case(1):

                    absParams.leftMargin = 110;
                    absParams.topMargin = 5;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(2):

                    absParams.leftMargin = 215;
                    absParams.topMargin = 5;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(3):

                    absParams.leftMargin = 5;
                    absParams.topMargin = 110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(4):

                    absParams.leftMargin =110;
                    absParams.topMargin =110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(5):

                    absParams.leftMargin = 215;
                    absParams.topMargin =110;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(6):

                    absParams.leftMargin = 5;
                    absParams.topMargin = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(7):

                    absParams.leftMargin = 110;
                    absParams.topMargin = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
                case(8):

                    absParams.leftMargin = 215;
                    absParams.topMargin = 215;
                    buttons[text].setLayoutParams(absParams);
                    break;
            }
        }

    }

    public int find_pos(int element)
    {
        int i=0;
        for(i=0;i<9;i++)
        {
            if(cells.get(i)==element)
            {
                break;
            }
        }
        return i;
    }

    private void setImages(){
        for (int i = 1; i < 9; i++) {
            new DownloadImageTask(buttons[i], getResources())
                    .execute(challenge.getJuego().getImagenes().get(i).getImagen());
        }
        timerPuzzle(challenge.getJuego().getTiempo()*1000);

    }

    private void timerPuzzle(int time){
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                textCount.setText("Segundos Restantes: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                textCount.setText("Fim");
                absoluteLayout.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    private void sendWinner(){
        ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
        submitAnswers.setRespuestaVerContenido(new ChallengeSeeContentAnswer());
        LoyaltyApi.submitChallenge(challenge, submitAnswers, new Callback<ChallengeSubmitResponse>() {
            @Override
            public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PuzzleActivity.this, R.string.challenge_submitted_successfully, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(PuzzleActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {
                Toast.makeText(PuzzleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

