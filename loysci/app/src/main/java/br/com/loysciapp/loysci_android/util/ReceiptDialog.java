package br.com.loysciapp.loysci_android.util;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.DialogReceiptBinding;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.presentation.ui.adapters.VoucherReceiptAdapter;

public class ReceiptDialog extends Dialog {
    private Context context;
    private int total;
    private List<Reward> voucherList;
    private String code;
    private Long expirationDate;
    private OnDismiss onDismiss;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    public ReceiptDialog(@NonNull Context context, List<Reward> voucherList, int total, String code, Long expirationDate, OnDismiss onDismiss) {
        super(context);
        this.context = context;
        this.voucherList = voucherList;
        this.total = total;
        this.code = code;
        this.expirationDate = expirationDate;
        this.onDismiss = onDismiss;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogReceiptBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_receipt, null, false);
        setContentView(binding.getRoot());

        final VoucherReceiptAdapter adapter = new VoucherReceiptAdapter(voucherList);
        binding.rvVouchersName.setLayoutManager(new LinearLayoutManager(context));
        binding.rvVouchersName.setAdapter(adapter);

        binding.tvCode.setText(code);
        binding.tvTotal.setText(String.valueOf(total));
        binding.voucherExpirationInfo.setText(String.format(context.getString(R.string.voucher_expiration_info), dateFormat.format(new Date(expirationDate))));

        binding.btnCopiartexto.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

            clipboard.setText(code);
            Toast.makeText(context.getApplicationContext(), "Voucher copiada!", Toast.LENGTH_SHORT).show();

        });

        //binding.tvDone.setOnClickListener(v -> {
        //    dismiss();
        //    onDismiss.onDismiss();
        //});
    }

    public interface OnDismiss{
        void onDismiss();
    }
}
