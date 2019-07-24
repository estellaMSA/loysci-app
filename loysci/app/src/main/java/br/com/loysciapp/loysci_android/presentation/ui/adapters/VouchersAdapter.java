package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemVoucherCardBinding;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.presentation.ui.activities.VoucherActivity;

import static br.com.loysciapp.loysci_android.util.Constants.REWARD_PARCELABLE;

public class VouchersAdapter extends RecyclerView.Adapter<VouchersAdapter.MyViewHolder> {

    private List<Reward> rewardList;
    private Context context;

    public VouchersAdapter(Context context) {
        this.context = context;
        rewardList = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher_card, parent, false);
        return new VouchersAdapter.MyViewHolder(itemView);
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
        holder.binding.tvTitleVoucher.setText(reward.getEncabezadoArte());

        if (reward.getEncabezadoArte().toLowerCase().contains("zattini")) {
            holder.binding.ivImageLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_zattini));
            holder.binding.ivImageLogo.setVisibility(View.GONE);
        } else if (reward.getEncabezadoArte().toLowerCase().contains("netshoes")) {
            holder.binding.ivImageLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_netshoes));
            holder.binding.ivImageLogo.setVisibility(View.GONE);
        } else {
            holder.binding.ivImageLogo.setImageDrawable(context.getDrawable(R.drawable.mini_logo_movida));
            holder.binding.ivImageLogo.setVisibility(View.GONE);
        }

        holder.binding.cvLayout.setOnClickListener(view -> {
            Intent intent = new Intent(context, VoucherActivity.class);
            intent.putExtra(REWARD_PARCELABLE, reward);
            context.startActivity(intent);
        });

        if(position == rewardList.size()-1) {
            holder.binding.divider.setVisibility(View.GONE);
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
        ItemVoucherCardBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
