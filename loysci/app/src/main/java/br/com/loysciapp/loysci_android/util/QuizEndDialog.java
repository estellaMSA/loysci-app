package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogFinishedQuizBinding;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.presentation.ui.activities.QuizActivity;

public class QuizEndDialog extends Dialog {
    private DialogFinishedQuizBinding binding;
    private Challenge challenge;
    QuizActivity activity;
    boolean isRunning = false;
    int score;
    int total;
    int remaining;
    boolean rightAnswer;
    String answer;

    public QuizEndDialog(@NonNull QuizActivity activity, Challenge challenge, int score, int total, int remaining, boolean rightAnswer, String answer) {
        super(activity);
        this.activity = activity;
        this.challenge = challenge;
        this.score = score;
        this.total = total;
        this.remaining = remaining;
        this.rightAnswer = rightAnswer;
        this.answer = answer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_finished_quiz, null, false);
        setContentView(binding.getRoot());
        isRunning = true;
        this.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        binding.btnCreateAvatar.setOnClickListener((v) -> {
            this.dismiss();
            activity.finish();
        });

        binding.llParabens.setVisibility(View.GONE);
        binding.txtReward.setVisibility(View.GONE);
        binding.recompensas.setVisibility(View.GONE);
        binding.txtActivityTitle.setText(challenge.getNombre());
        binding.txtRemaining.setText(activity.getString(R.string.activities_remaining, remaining));
        binding.txtAnswer.setText(activity.getString(R.string.right_answer, answer));
        if (rightAnswer) {
            binding.txtResult.setText(activity.getString(R.string.answered_right));
        } else {
            binding.txtResult.setText(activity.getString(R.string.answered_wrong));
        }
    }
}
