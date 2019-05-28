package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemVoucherReceiptBinding;
import br.com.monitoratec.loysci_android.model.Reward;

/**
 * Created by Felipe Galeote on 29,Outubro,2018
 */
public class VoucherReceiptAdapter extends RecyclerView.Adapter<VoucherReceiptAdapter.ViewHolder> {

    private List<Reward> vouchers;

    public VoucherReceiptAdapter(List<Reward> vouchers) {
        this.vouchers = vouchers;
    }

    @NonNull
    @Override
    public VoucherReceiptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemVoucherReceiptBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_voucher_receipt, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherReceiptAdapter.ViewHolder holder, int position) {
        Reward voucher = vouchers.get(position);

        holder.binding.voucherReceiptName.setText(voucher.getEncabezadoArte());
        holder.binding.voucherReceiptValue.setText(String.valueOf(voucher.getValorMoneda() * voucher.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemVoucherReceiptBinding binding;

        ViewHolder(ItemVoucherReceiptBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
