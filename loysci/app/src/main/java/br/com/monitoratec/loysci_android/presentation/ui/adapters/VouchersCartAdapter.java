package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemVoucherCartBinding;
import br.com.monitoratec.loysci_android.model.Reward;
import br.com.monitoratec.loysci_android.presentation.ui.activities.CartActivity;
import br.com.monitoratec.loysci_android.util.Prefs;
import br.com.monitoratec.loysci_android.util.RemoveVoucherDialog;

public class VouchersCartAdapter extends RecyclerView.Adapter<VouchersCartAdapter.MyViewHolder> {
    private RemoveVoucherDialog removeVoucherDialog;
    private List<Reward> rewardList;
    private Context context;
    private int quantity;

    public VouchersCartAdapter(Context context, List<Reward> rewardList) {
        this.context = context;
        if (rewardList != null)
            this.rewardList = rewardList;
        else
            this.rewardList = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher_cart, parent, false);
        return new VouchersCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        Glide.clear(holder.binding.ivImageVoucher);

        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Reward reward = rewardList.get(position);

        Glide.with(context)
                .load(reward.getImagenArte())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.binding.ivImageVoucher);

        holder.binding.tvPointsVoucher.setText(context.getString(R.string.points, reward.getValorMoneda()));
        holder.binding.tvTotalPointsVoucher.setText(context.getString(R.string.points,reward.getValorMoneda() * reward.getQuantity()));
        holder.binding.tvTitleVoucher.setText(reward.getEncabezadoArte());

        holder.binding.tvQuantity.setText(String.valueOf(reward.getQuantity()));

        holder.binding.buttonAddLayout.setOnClickListener(v -> {
            if(reward.getQuantity() < 27) {
                reward.setQuantity(reward.getQuantity() + 1);
                holder.binding.tvQuantity.setText(String.valueOf(reward.getQuantity()));
                holder.binding.tvTotalPointsVoucher.setText(context.getString(R.string.points, reward.getValorMoneda() * reward.getQuantity()));
                ((CartActivity)context).refreshTotalPoints(rewardList);
            }
        });

        holder.binding.buttonRemoveLayout.setOnClickListener(v -> {
            if (reward.getQuantity() > 1) {
                reward.setQuantity(reward.getQuantity()-1);
                holder.binding.tvQuantity.setText(String.valueOf(reward.getQuantity()));
                holder.binding.tvTotalPointsVoucher.setText(context.getString(R.string.points,reward.getValorMoneda() * reward.getQuantity()));
                ((CartActivity)context).refreshTotalPoints(rewardList);
            }
        });

        holder.binding.tvRemoveButton.setOnClickListener(v -> showRemoveDialog(reward, position));

        if (reward.getDetalleArte().contains(context.getString(R.string.item_per_contract))){
            holder.binding.quantityLayout.setEnabled(false);
            holder.binding.buttonRemoveLayout.setEnabled(false);
            holder.binding.buttonAddLayout.setEnabled(false);
            holder.binding.quantityLayout.setAlpha(0.35f);
        } else {
            holder.binding.quantityLayout.setEnabled(true);
            holder.binding.buttonRemoveLayout.setEnabled(true);
            holder.binding.buttonAddLayout.setEnabled(true);
            holder.binding.quantityLayout.setAlpha(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public void setItems(List<Reward> rewardsList) {
        if (rewardsList != null) {
            this.rewardList.clear();
            this.rewardList.addAll(rewardsList);
            notifyDataSetChanged();
        }
    }

    public List<Reward> getItems() {
        return rewardList;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemVoucherCartBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    private void showRemoveDialog(Reward reward, int position){
        removeVoucherDialog = new RemoveVoucherDialog(context, reward, new RemoveVoucherDialog.OnClickListener() {
            @Override
            public void onCancel() {
                removeVoucherDialog.dismiss();
            }

            @Override
            public void onRemove() {
                List<Reward> rewardList = Prefs.getCart();
                rewardList.remove(position);
                Prefs.saveCart(rewardList);
                ((CartActivity)context).refreshVoucherList();
                removeVoucherDialog.dismiss();
            }

        });
        removeVoucherDialog.show();
    }
}
