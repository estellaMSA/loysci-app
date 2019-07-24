package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemMissionActivityBinding;
import br.com.loysciapp.loysci_android.model.Challenge;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class MissionActivitiesAdapter extends RecyclerView.Adapter<MissionActivitiesAdapter.ActivityViewHolder>{

    List<Challenge> challenges;
    Context context;
    int NORMAL_ITEM =0;
    int LAST_ITEM = 1;
    int FIRST_ITEM = 2;
    SimpleItemClickListener simpleItemClickListener;

    public MissionActivitiesAdapter(List<Challenge> challenges, Context context, SimpleItemClickListener simpleItemClickListener) {
        this.challenges = challenges;
        this.context = context;
        this.simpleItemClickListener = simpleItemClickListener;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMissionActivityBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_mission_activity, parent, false);

        //if(viewType == FIRST_ITEM && context != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
            params.setMargins(margin, 0, 0, 0);
            binding.getRoot().setLayoutParams(params);

        return new ActivityViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        final Challenge challenge = challenges.get(position);
        //holder.binding.txtActivityTitle.setText(challenge.getDescripcion());
        holder.binding.txtActivityTitle.setText(challenge.getMisionEncuestaPreguntas().get(0).getPregunta());


        if(challenge.isCompleted()){
            holder.binding.imgActivityDone.setVisibility(View.VISIBLE);
            holder.binding.imgActivityPending.setVisibility(View.GONE);
            holder.binding.txtActivityTitle.setTextColor(ContextCompat.getColor(context, R.color.challenge_done));
        }else {
            holder.binding.imgActivityDone.setVisibility(View.GONE);
            holder.binding.imgActivityPending.setVisibility(View.VISIBLE);
            holder.binding.txtActivityTitle.setTextColor(ContextCompat.getColor(context, R.color.primary_text));
            holder.binding.getRoot().setOnClickListener(view -> simpleItemClickListener.onSimpleItemClick(challenge));
        }
    }

    public void updateList(List<Challenge> challenges){
        this.challenges = challenges;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(challenges != null){
            if(position == 0){
                return FIRST_ITEM;
            }else if(position == (challenges.size()-1)){
                return LAST_ITEM;
            }else{
                return NORMAL_ITEM;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(challenges!=null)
            return challenges.size();
        return 0;
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        ItemMissionActivityBinding binding;
        public ActivityViewHolder(View itemView) {
            super(itemView);
        }

        public ActivityViewHolder(ItemMissionActivityBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
