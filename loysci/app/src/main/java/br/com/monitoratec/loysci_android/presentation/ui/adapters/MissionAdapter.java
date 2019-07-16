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
import br.com.monitoratec.loysci_android.databinding.ItemMissionBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.MissionViewHolder> {


    private List<Mission> challenges;
    Context context;
    int NORMAL_ITEM =0;
    int LAST_ITEM = 1;
    int FIRST_ITEM = 2;
    private SimpleItemClickListener simpleItemClickListener;

    public  MissionAdapter (List<Mission> challenges, Context context, SimpleItemClickListener simpleItemClickListener) {
        this.challenges = challenges;
        this.context = context;
        this.simpleItemClickListener = simpleItemClickListener;
    }


    @NonNull
    @Override
    public MissionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMissionBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_mission, parent, false);


        if(viewType == LAST_ITEM && context != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
            params.setMargins(0, margin, 0, margin);
            binding.getRoot().setLayoutParams(params);
        }

        return new MissionViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull MissionViewHolder holder, int position) {

        final Mission challenge = challenges.get(position);

        Glide.with(context)
                .load(challenge.getImagem())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.binding.imgChallenge);


        holder.binding.challengeTitle.setText(challenge.getTitulo());
        holder.binding.challengeDescription.setText(challenge.getDescricao());

        int pontos = 0;

        for (Challenge perguntas:challenge.getChallenges()) {

            pontos += perguntas.getValor();
        }

        holder.binding.txtMission1Points.setText(pontos + " " + context.getString(R.string.mission_points));


        holder.binding.txtMission1Points.setBackground(context.getResources().getDrawable(R.drawable.background_filter_button_clicked_2));

        if(challenge.isInitied()){
            holder.binding.txtMission1Points.setBackground(context.getResources().getDrawable(R.drawable.background_filter_button_clicked_initied));
            holder.binding.txtMission1Points.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.binding.challengeTitle.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.challengeDescription.setTextColor(context.getResources().getColor(R.color.white));
            holder.binding.mission1CompletedLayout.setVisibility(View.VISIBLE);
            holder.binding.getRoot().setOnClickListener(view -> simpleItemClickListener.onSimpleItemClick(challenge));
        }
        else if(challenge.isCompleted()){

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.binding.imgChallenge.setColorFilter(filter);

            holder.binding.txtMission1Points.setBackground(context.getResources().getDrawable(R.drawable.background_filter_button_clicked_completed));
        }
        else{
            holder.binding.mission1CompletedLayout.setVisibility(View.GONE);
            holder.binding.getRoot().setOnClickListener(view -> simpleItemClickListener.onSimpleItemClick(challenge));
        }



    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public class MissionViewHolder extends RecyclerView.ViewHolder {
        ItemMissionBinding binding;

        public MissionViewHolder(View itemView) {
            super(itemView);
        }

        public MissionViewHolder(ItemMissionBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
