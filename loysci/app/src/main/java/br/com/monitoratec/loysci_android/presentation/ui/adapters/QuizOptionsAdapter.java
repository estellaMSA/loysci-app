package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemQuizOptionBinding;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class QuizOptionsAdapter extends RecyclerView.Adapter<QuizOptionsAdapter.QuizOptionsViewHolder>{

        List<String> options;
        SimpleItemClickListener simpleItemClickListener;
        Context context;
        int selected = -1;

    public QuizOptionsAdapter(Context context, List<String> options, SimpleItemClickListener simpleItemClickListener) {
        this.options = options;
        this.context = context;
        this.simpleItemClickListener = simpleItemClickListener;
    }

    @NonNull
    @Override
    public QuizOptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemQuizOptionBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_quiz_option, parent, false);
        return new QuizOptionsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizOptionsViewHolder holder, int position) {
        if(selected == position) {
            holder.binding.imgOption.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.quiz_option_selected));
        }else{
            holder.binding.imgOption.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.background_circle));
        }
        holder.binding.txtOption.setText(options.get(position));
        holder.binding.getRoot().setOnClickListener(v -> simpleItemClickListener.onSimpleItemClick(position));
    }

    @Override
    public int getItemCount() {
        if(options!=null)
            return options.size();
        return 0;
    }

    public int getSelected() {
        return selected;
    }
    public String getAnswer(int pos) {
        return options.get(pos);
    }

    public void updateSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public class QuizOptionsViewHolder extends RecyclerView.ViewHolder {
        ItemQuizOptionBinding binding;
        public QuizOptionsViewHolder(View itemView) {
            super(itemView);
        }

        public QuizOptionsViewHolder(ItemQuizOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
