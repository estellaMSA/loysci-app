package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogExitQuizBinding;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

import static br.com.loysciapp.loysci_android.util.Constants.EXIT_BUTTON_TRIGGERED;
import static br.com.loysciapp.loysci_android.util.Constants.EXIT_TOOLBAR_TRIGGERED;

public class ActivityExitDialog extends Dialog {
    private DialogExitQuizBinding binding;
    private String activityDescription;
    Context context;
    boolean isRunning = false;
    int score;
    int total;
    int remaining;
    SimpleItemClickListener simpleItemClickListener;
    int triggerOption;

    public ActivityExitDialog(@NonNull Context context, String activityDescription, SimpleItemClickListener simpleItemClickListener, int triggerOption) {
        super(context);
        this.context = context;
        this.activityDescription = activityDescription;
        this.simpleItemClickListener = simpleItemClickListener;
        this.triggerOption = triggerOption;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_exit_quiz, null, false);
        setContentView(binding.getRoot());
        isRunning = true;
        this.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        binding.btnExit.setOnClickListener(v -> {
            simpleItemClickListener.onSimpleItemClick(0);
            ActivityExitDialog.this.dismiss();
        });
        binding.btnBack.setOnClickListener(v -> dismiss());
        binding.txtActivityTitle.setText(activityDescription);
        switch (triggerOption){
            case EXIT_TOOLBAR_TRIGGERED:
                binding.btnExit.setText(context.getString(R.string.exit_activity));
                break;
            case EXIT_BUTTON_TRIGGERED:
                binding.btnExit.setText(context.getString(R.string.give_up));
                break;
        }
    }
}
