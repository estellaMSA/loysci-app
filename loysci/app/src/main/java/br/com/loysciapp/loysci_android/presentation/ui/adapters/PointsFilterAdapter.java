package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemFilterBinding;

/**
 * Created by Felipe Galeote on 06,Novembro,2018
 */
public class PointsFilterAdapter extends RecyclerView.Adapter<PointsFilterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Long> points;
    private int selectedPosition = -1;
    private OnPointsSelectedListener onPointsSelectedListener;

    public PointsFilterAdapter(Context context, OnPointsSelectedListener onPointsSelectedListener) {
        this.context = context;
        this.onPointsSelectedListener = onPointsSelectedListener;
        this.points = new ArrayList<>();
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
                onPointsSelectedListener.onPointsUnselected();
            }
            else {
                selectedPosition = position;
                notifyDataSetChanged();
                onPointsSelectedListener.onPointsSelected(String.valueOf(points.get(position)));
            }
        });

        holder.binding.tvItemName.setText(String.valueOf(points.get(position)));
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
        return points.size();
    }

    public void setPoints(ArrayList<Long> points) {
        this.points = points;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemFilterBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public interface OnPointsSelectedListener {
        void onPointsSelected(String category);
        void onPointsUnselected();
    }
}
