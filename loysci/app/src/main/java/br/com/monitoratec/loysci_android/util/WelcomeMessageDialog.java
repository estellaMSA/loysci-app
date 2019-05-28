package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogWelcomeMessageBinding;

public class WelcomeMessageDialog extends Dialog {

    private DialogWelcomeMessageBinding binding;

    public WelcomeMessageDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_welcome_message, null, false);
        setContentView(binding.getRoot());
        this.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        binding.btnContinue.setOnClickListener((v)->this.dismiss());
    }


}
