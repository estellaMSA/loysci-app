package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityQuizBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.QuizOptionsAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.QuizViewModel;
import br.com.monitoratec.loysci_android.util.ActivityExitDialog;
import br.com.monitoratec.loysci_android.util.MissionEndDialog;
import br.com.monitoratec.loysci_android.util.QuizEndDialog;

import static br.com.monitoratec.loysci_android.util.Constants.CHALLENGE_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Constants.EXIT_BUTTON_TRIGGERED;
import static br.com.monitoratec.loysci_android.util.Constants.EXIT_TOOLBAR_TRIGGERED;
import static br.com.monitoratec.loysci_android.util.Constants.ID_MEMBER;
import static br.com.monitoratec.loysci_android.util.Constants.ID_MISSION;
import static br.com.monitoratec.loysci_android.util.Constants.ID_TOPIC;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_FINISHED;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_RIGHT;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_WRONG;
import static br.com.monitoratec.loysci_android.util.Constants.REMAINING_ACTIVITIES;

public class QuizActivity extends AppCompatActivity implements SimpleItemClickListener {

    ActivityQuizBinding binding;
    QuizOptionsAdapter adapter;
    QuizViewModel model;
    String idMember;
    String idTopic;
    String idMission;
    int currentRightAnswer;
    int remainingQuestions;
    int rewardTotal;
    boolean isClicked = false;
    private Challenge challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quiz);
        challenge = (Challenge) getIntent().getParcelableExtra(CHALLENGE_PARCELABLE);
        idMember = getIntent().getStringExtra(ID_MEMBER);
        idTopic = getIntent().getStringExtra(ID_TOPIC);
        idMission = getIntent().getStringExtra(ID_MISSION);
        rewardTotal = challenge.getValor();
        remainingQuestions = getIntent().getIntExtra(REMAINING_ACTIVITIES, 0);
        setSupportActionBar(binding.includeToolbar.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.answer_quiz);

        binding.btnCancel.setOnClickListener(v -> {
            if (adapter != null)
                dialogExit();
        });
        binding.btnSave.setOnClickListener(v -> {
            if (adapter != null) {
                checkQuestion();
            }
        });

        model = ViewModelProviders.of(this).get(QuizViewModel.class);
        model.challenge = challenge;
        loadQuestion();
    }

    private void dialogExit() {
        ActivityExitDialog dialog = new ActivityExitDialog(this, model.challenge.getNombre(), new SimpleItemClickListener() {
            @Override
            public void onSimpleItemClick(Object object) {
                finish();
            }
        }, EXIT_BUTTON_TRIGGERED);
        dialog.show();
    }

    private void checkQuestion() {
        if (adapter.getSelected() == -1) {
            Toast.makeText(this, getString(R.string.choose_option), Toast.LENGTH_LONG).show();
        } else if (!isClicked) {
            binding.btnSave.setAlpha(0.5f);
            isClicked = true;
            binding.quizLayout.setAlpha(0.2f);
            binding.loadingLayout.loadingText.setText(getString(R.string.sending_answer));
            binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);


            setCorrectChallenge(adapter.getSelected());

        }
    }

    private void loadQuestion() {

//        model.loadActivityInfo(idMember, model.challenge.getIdMision(), new ViewModelSimpleCallback() {
//            @Override
//            public void onSuccess() {
//                populateQuestion();
//            }
//
//            @Override
//            public void onFailed(Object object) {
//                Toast.makeText(QuizActivity.this, getString(R.string.unable_to_load_quiz), Toast.LENGTH_LONG).show();
//                finish();
//            }
//        });

        populateQuestion();

    }

    private void populateQuestion() {

        if (model.challenge.getMisionEncuestaPreguntas() != null) {
            //binding.txtQuestion.setText(model.challenge.getDescripcion());
            binding.txtTitle.setText(model.challenge.getMisionEncuestaPreguntas().get(0).getPregunta());
            String[] options = model.challenge.getMisionEncuestaPreguntas().get(0).getRespuestas().split("\n");
            int counter = 0;
            for (String option : options) {
                if (option.contains("<C>")) {
                    currentRightAnswer = counter;
                    break;
                }
                counter++;
            }

            if (model.challenge.getImagen() != null) {
                Glide.with(this).load(model.challenge.getImagen()).into(binding.imgQuestion);
                binding.imgQuestion.setVisibility(View.VISIBLE);
            } else
                binding.imgQuestion.setVisibility(View.GONE);

            if (currentRightAnswer == -1) {
                Toast.makeText(this, getString(R.string.unable_to_start_quiz), Toast.LENGTH_LONG).show();
                finish();
            } else {
                options[currentRightAnswer] = options[currentRightAnswer].replace("<C>", "");
                adapter = new QuizOptionsAdapter(this, Arrays.asList(options), this);
                binding.listAlternatives.setLayoutManager(new LinearLayoutManager(this));
                binding.listAlternatives.setAdapter(adapter);
                binding.seekBarLevel.requestFocus();
            }
        } else {
            Toast.makeText(this, getString(R.string.unable_to_load_quiz), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActivityExitDialog dialog = new ActivityExitDialog(this, model.challenge.getNombre(), new SimpleItemClickListener() {
            @Override
            public void onSimpleItemClick(Object object) {
                finish();
            }
        }, EXIT_TOOLBAR_TRIGGERED);
        dialog.show();
        return super.onOptionsItemSelected(item);
    }


    private void setCorrectChallenge(int selected) {


        model.setChallengeCorrect(model.challenge, model.challenge.getMisionEncuestaPreguntas().get(0), selected + 1, new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {

                if (remainingQuestions <= 1) {
                    MissionEndDialog dialog = new MissionEndDialog(QuizActivity.this, getString(R.string.points, rewardTotal), model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, true, adapter.getAnswer(currentRightAnswer));
                    dialog.show();
                    setResult(MISSION_FINISHED);
                } else {
                    QuizEndDialog dialog = new QuizEndDialog(QuizActivity.this, model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, true, adapter.getAnswer(currentRightAnswer));
                    dialog.show();
                    setResult(QUIZ_COMPLETED_RIGHT);
                }
                isClicked = false;
                binding.quizLayout.setAlpha(1);
                binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(Object object) {


                if (object.equals(QUIZ_COMPLETED_WRONG)) {

                    if (remainingQuestions <= 1) {
                        MissionEndDialog dialog = new MissionEndDialog(QuizActivity.this, getString(R.string.points, rewardTotal), model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, false, adapter.getAnswer(currentRightAnswer));
                        dialog.show();
                        setResult(MISSION_FINISHED);
                    } else {
                        QuizEndDialog dialog = new QuizEndDialog(QuizActivity.this, model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, false, adapter.getAnswer(currentRightAnswer));
                        dialog.show();
                        setResult(QUIZ_COMPLETED_RIGHT);
                    }
                    isClicked = false;
                    binding.quizLayout.setAlpha(1);
                    binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
                } else {

                    Toast.makeText(QuizActivity.this, getString(R.string.unable_to_send_answer), Toast.LENGTH_LONG).show();
                    isClicked = false;
                    binding.btnSave.setAlpha(1);
                    binding.quizLayout.setAlpha(1);
                    binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setWrongChallenge() {

        model.setChallengeWrong(idMember, idTopic, idMission, new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {
                if (remainingQuestions <= 1) {
                    MissionEndDialog dialog = new MissionEndDialog(QuizActivity.this, getString(R.string.points, rewardTotal), model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, false, adapter.getAnswer(currentRightAnswer));
                    dialog.show();
                    setResult(MISSION_FINISHED);

                    //QuizEndDialog dialog1 = new QuizEndDialog(QuizActivity.this, model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, false, adapter.getAnswer(currentRightAnswer));
                    //dialog1.show();
                    // setResult(QUIZ_COMPLETED_WRONG);
                } else {
                    QuizEndDialog dialog = new QuizEndDialog(QuizActivity.this, model.challenge, model.challenge.getValor(), 0, remainingQuestions - 1, false, adapter.getAnswer(currentRightAnswer));
                    dialog.show();
                    setResult(QUIZ_COMPLETED_WRONG);
                }
            }

            @Override
            public void onFailed(Object object) {
                Toast.makeText(QuizActivity.this, getString(R.string.unable_to_send_answer), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onSimpleItemClick(Object object) {
        adapter.updateSelected((int) object);
        binding.btnSave.setAlpha(1);
    }
}
