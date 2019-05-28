package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemAvatarOptionsBinding;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyViewHolder> {

    public enum Type {
        HEAD, HAT, HAIR, BODY
    }
    private List<Bitmap> avatarOptions;
    private Context context;
    private onSelected onSelected;
    private int selectedPosition = -1;
    private MyViewHolder holder;
    private Type type;

    public AvatarAdapter(Context context, List<Bitmap> avatarOptions, Type type, onSelected onSelected) {
        this.context = context;
        this.avatarOptions = avatarOptions;
        this.type = type;
        this.onSelected = onSelected;
    }

    @NonNull
    @Override
    public AvatarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_avatar_options, parent, false);
        return new AvatarAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Bitmap bitmap = avatarOptions.get(position);

        if(position == selectedPosition){
            holder.binding.cvAvatar.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.fabSelected.setVisibility(View.VISIBLE);
        } else {
            holder.binding.cvAvatar.setCardBackgroundColor(context.getResources().getColor(R.color.item_avatar_color));
            holder.binding.fabSelected.setVisibility(View.GONE);
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        Glide.with(context)
                .load(stream.toByteArray())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.binding.ivAvatar);

        holder.binding.cvAvatar.setOnClickListener(view -> {

            if(selectedPosition == position) {
                selectedPosition = -1;
                holder.binding.cvAvatar.setCardBackgroundColor(context.getResources().getColor(R.color.item_avatar_color));
                holder.binding.fabSelected.setVisibility(View.GONE);
                onSelected.onDeselect(type);
               this.holder = null;
            } else {

                holder.binding.fabSelected.setVisibility(View.VISIBLE);
                holder.binding.cvAvatar.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

                if (this.holder != null) {
                    holder.binding.cvAvatar.setCardBackgroundColor(context.getResources().getColor(R.color.item_avatar_color));
                    holder.binding.fabSelected.setVisibility(View.GONE);
                    this.holder = null;
                }

                this.holder = holder;
                selectedPosition = position;
                onSelected.onClick(position, type);
            }
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        return avatarOptions.size();
    }

    public void setNewList(List<Bitmap> avatarOptions) {
        this.avatarOptions.clear();
        this.avatarOptions.addAll(avatarOptions);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemAvatarOptionsBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public interface onSelected {
        void onClick(int position, Type type);

        void onDeselect (Type type);
    }
}
