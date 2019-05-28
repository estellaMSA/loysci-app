package br.com.monitoratec.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.Window;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.DialogRequestPrizeBinding;
import br.com.monitoratec.loysci_android.model.Topic;

public class RequestPrizeDialog extends Dialog {
    private DialogRequestPrizeBinding binding;
    private Topic topic;
    Context context;

    public RequestPrizeDialog(@NonNull Context context, Topic topic) {
        super(context);
        this.context = context;
        this.topic = topic;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_request_prize, null, false);
        setContentView(binding.getRoot());
        this.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
        binding.btnClose.setOnClickListener((v)->this.dismiss());

        binding.txtPrizeContent.setText(context.getString(R.string.rewarded_points, topic.getPoints()));
    }


    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }
}
