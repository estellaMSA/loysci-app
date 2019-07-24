package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogPurchaseVoucherAlertBinding;
import br.com.loysciapp.loysci_android.model.Reward;

public class PurchaseVoucherMissingCarAlertDialog extends Dialog {

    private List<Reward> voucherList;
    private Context context;
    private OnClickListener onClickListener;
    private DialogPurchaseVoucherAlertBinding binding;

    public PurchaseVoucherMissingCarAlertDialog(@NonNull Context context, List<Reward> voucherList, OnClickListener onClickListener) {
        super(context);
        this.context = context;

        if(voucherList != null)
            this.voucherList = voucherList;
        else
            this.voucherList = new ArrayList<>();

        this.onClickListener = onClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_purchase_voucher_alert, null, false);
        setContentView(binding.getRoot());

        binding.tvAddDaily.setOnClickListener(v -> onClickListener.onAddDaily());
        binding.tvAlertText.setText(R.string.purchase_voucher_alert_missing_car);
    }

    public interface OnClickListener {
        void onAddDaily();
    }

    private String getStringFromVoucherList(List<Reward> voucherList){
        String s = "";

        for (Reward r: voucherList) {
            s = s.concat(r.getEncabezadoArte() + "\n");
        }

        return s;
    }
}
