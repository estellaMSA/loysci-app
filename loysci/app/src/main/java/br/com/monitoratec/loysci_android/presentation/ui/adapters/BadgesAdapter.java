package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import br.com.monitoratec.loysci_android.databinding.ItemBadgeBinding;
import br.com.monitoratec.loysci_android.model.Badge;
import br.com.monitoratec.loysci_android.util.BadgeDialog;

public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.BadgesViewHolder>{


    BadgeDialog badgeDialog;

    private Context context;
    private List<Badge> badges;

    int NORMAL_ITEM =0;
    int LAST_ITEM = 1;


    public BadgesAdapter(Context context, List<Badge> badges) {
        this.context = context;
        this.badges = badges;
    }



    @NonNull
    @Override
    public BadgesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemBadgeBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_badge, parent, false);

        if(viewType == LAST_ITEM && context != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
            params.setMargins(0, margin, 0, margin);
            binding.getRoot().setLayoutParams(params);
        }
        return new BadgesAdapter.BadgesViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull BadgesViewHolder holder, int position) {

        Badge currentBadge = badges.get(position);






        Glide.with(context)
                .load(currentBadge.getImagen())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgBadge);


        holder.binding.imgBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callDialog(currentBadge);
            }
        });

        if(!currentBadge.isObtenida())
        {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.binding.imgBadge.setColorFilter(filter);
        }



    }

    private void callDialog(Badge currentBadge) {

        badgeDialog = new BadgeDialog(context, currentBadge, 0, new BadgeDialog.OnClickListener() {
            @Override
            public void onCancel() {

                badgeDialog.dismiss();
            }

            @Override
            public void onGoToCart() {

            }
        });

        badgeDialog.show();
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public class BadgesViewHolder extends RecyclerView.ViewHolder {
        ItemBadgeBinding binding;

        public BadgesViewHolder(View itemView) {
            super(itemView);
        }

        public BadgesViewHolder(ItemBadgeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
