package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemPromotionBinding;
import br.com.monitoratec.loysci_android.model.Promotion;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SalesFragment;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.PromotionViewHolder>{

    List<Promotion> promotions;
    SalesFragment salesFragment;
    int NORMAL_ITEM =0;
    int LAST_ITEM = 1;
    SimpleItemClickListener simpleItemClickListener;

    public PromotionsAdapter(List<Promotion> promotions, SalesFragment salesFragment, SimpleItemClickListener simpleItemClickListener) {
        this.promotions = promotions;
        this.salesFragment = salesFragment;
        this.simpleItemClickListener = simpleItemClickListener;
        setHasStableIds(true);
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPromotionBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_promotion, parent, false);

        if(viewType == LAST_ITEM && salesFragment.getActivity() != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, salesFragment.getActivity().getResources().getDisplayMetrics());
            params.setMargins(0, margin, 0, margin);
            binding.getRoot().setLayoutParams(params);
        }
        return new PromotionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        final Promotion promotion = promotions.get(position);
        holder.binding.promotionTitle.setText(promotion.getEncabezadoArte());
        holder.binding.promotionDescription.setText(promotion.getDetalleArte());

        if(promotion.getImagenArte()!=null)
            Glide.with(salesFragment)
                    .load(promotion.getImagenArte())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.binding.imgPromotion);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleItemClickListener.onSimpleItemClick(promotion);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(promotions != null){
            if(position == (promotions.size()-1)){
                return LAST_ITEM;
            }else{
                return NORMAL_ITEM;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(promotions!=null)
            return promotions.size();
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return promotions.get(position).getIdPromocion().hashCode();
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder {
        ItemPromotionBinding binding;
        public PromotionViewHolder(View itemView) {
            super(itemView);
        }

        public PromotionViewHolder(ItemPromotionBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
