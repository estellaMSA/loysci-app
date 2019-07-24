package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemFilterBinding;

/**
 * Created by Felipe Galeote on 06,Novembro,2018
 */
public class CategoriesFilterAdapter extends Adapter<CategoriesFilterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> categories;
    private int selectedPosition = -1;
    private OnCategorySelectedListener onCategorySelectedListener;

    public CategoriesFilterAdapter(Context context, OnCategorySelectedListener onCategorySelectedListener) {
        this.context = context;
        this.onCategorySelectedListener = onCategorySelectedListener;
        this.categories = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.clItem.setOnClickListener(view -> {

            if (selectedPosition == position) {
                selectedPosition = -1;
                notifyDataSetChanged();
                onCategorySelectedListener.onCategoryUnselected();
            }
            else {
                selectedPosition = position;
                notifyDataSetChanged();
                onCategorySelectedListener.onCategorySelected(categories.get(position));
            }
        });

        if(categories.get(position) == null)
            holder.binding.tvItemName.setText("Todos");
        else
            holder.binding.tvItemName.setText(categories.get(position));
        if (position == selectedPosition) {
            holder.binding.tvItemName.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.tvItemName.setBackground(context.getDrawable(R.drawable.background_filter_button_clicked));
        } else {
            holder.binding.tvItemName.setTextColor(context.getResources().getColor(R.color.secondary_text));
            holder.binding.tvItemName.setBackground(context.getDrawable(R.drawable.background_filter_button));
        }
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemFilterBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public interface OnCategorySelectedListener {
        void onCategorySelected(String category);
        void onCategoryUnselected();
    }
}
