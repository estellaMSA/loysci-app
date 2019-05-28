package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogDateFilterBinding;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Felipe Galeote on 26,Outubro,2018
 */
public class CustomDateFilterDialog extends Dialog {
    private OnDaysSelectedListener listener;
    private DialogDateFilterBinding binding;
    private int selectedDays;
    private CircleImageView selectedDayButton;

    public CustomDateFilterDialog(@NonNull Context context, OnDaysSelectedListener listener, int selectedDays) {
        super(context);
        this.listener = listener;
        this.selectedDays = selectedDays;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_date_filter, null, false);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        switch (selectedDays) {
            case 7:
                selectOption(binding.dialogButton7Days, 7);
                break;
            case 30:
                selectOption(binding.dialogButton30Days, 30);
                break;
            case 60:
                selectOption(binding.dialogButton60Days, 60);
                break;
            case -1:
                selectOption(binding.dialogButtonAllPeriod, -1);
                break;
        }

        binding.dialogLayout7Days.setOnClickListener(v -> {
            if (selectedDays != 7) {
                selectOption(binding.dialogButton7Days, 7);
            }
        });
        binding.dialogLayout30Days.setOnClickListener(v -> {
            if (selectedDays != 30) {
                selectOption(binding.dialogButton30Days, 30);
            }
        });
        binding.dialogLayout60Days.setOnClickListener(v -> {
            if (selectedDays != 60) {
                selectOption(binding.dialogButton60Days, 60);
            }
        });
        binding.dialogLayoutAllPeriod.setOnClickListener(v -> {
            if (selectedDays != -1) {
                selectOption(binding.dialogButtonAllPeriod, -1);
            }
        });
        binding.dialogFilterBtn.setOnClickListener(v -> listener.onDaysSelected(selectedDays));
    }

    private void selectOption(CircleImageView view, int selectedDays) {
        view.setImageDrawable(getContext().getDrawable(R.drawable.background_circle_selected));

        if (selectedDayButton != null) {
            selectedDayButton.setImageDrawable(getContext().getDrawable(R.drawable.background_circle));
        }
        this.selectedDayButton = view;
        this.selectedDays = selectedDays;
    }

    public interface OnDaysSelectedListener {
        void onDaysSelected(int days);
    }
}
