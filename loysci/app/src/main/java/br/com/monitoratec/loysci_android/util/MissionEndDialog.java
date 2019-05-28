package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogFinishedMissionBinding;
import br.com.monitoratec.loysci_android.databinding.DialogFinishedQuizBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.presentation.ui.activities.QuizActivity;

public class MissionEndDialog extends Dialog {
    private DialogFinishedQuizBinding binding;
    private String reward;
    private Challenge challenge;
    QuizActivity activity;
    boolean isRunning = false;
    int score;
    int total;
    int remaining;
    String answer;
    boolean rightAnswer;

    public MissionEndDialog(@NonNull QuizActivity activity, String reward, Challenge challenge, int score, int total, int remaining, boolean rightAnswer, String answer) {
        super(activity);
        this.activity = activity;
        this.reward = reward;
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

        binding.txtActivityTitle.setText(challenge.getNombre());
        binding.txtActivityTitle.setVisibility(View.VISIBLE);
        binding.congratulationsMission.setVisibility(View.GONE);
        binding.llParabens.setVisibility(View.VISIBLE);
        binding.txtRemaining.setVisibility(View.GONE);
        binding.quaseLa.setVisibility(View.GONE);
        binding.txtReward.setText(reward);
        binding.txtAnswer.setText(activity.getString(R.string.right_answer, answer));
        if (rightAnswer) {
            binding.txtResult.setText(activity.getString(R.string.answered_right));
        } else {
            binding.txtResult.setText(activity.getString(R.string.answered_wrong));
        }
    }
}
