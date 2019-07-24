package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
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

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemTopicCompletedBinding;
import br.com.loysciapp.loysci_android.databinding.ItemTopicsBinding;
import br.com.loysciapp.loysci_android.model.Mission;
import br.com.loysciapp.loysci_android.model.Topic;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.TopicMissionCickListener;
import br.com.loysciapp.loysci_android.presentation.ui.viewModels.MainViewModel;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicViewHolder>{

    List<Topic> topics;
    Context context;
    final int NORMAL_TOPIC =0;
    final int COMPLETED_TOPIC = 1;
    TopicMissionCickListener topicMissionCickListener;
    MainViewModel model;

    public TopicsAdapter(List<Topic> topics, Context context, MainViewModel model, TopicMissionCickListener topicMissionCickListener) {
        this.topics = topics;
        this.context = context;
        this.model = model;
        this.topicMissionCickListener = topicMissionCickListener;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources().getDisplayMetrics());
        params.setMargins(margin, 0, 0, 0);
        switch (viewType){
            case NORMAL_TOPIC:
                ItemTopicsBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_topics, parent, false);
                binding.getRoot().setLayoutParams(params);
                return new TopicViewHolder(binding);
            case COMPLETED_TOPIC:
                ItemTopicCompletedBinding completedBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_topic_completed, parent, false);
                completedBinding.getRoot().setLayoutParams(params);
                return new TopicViewHolder(completedBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        final Topic topic = topics.get(position);
        if(topic.isCompleted()){
            holder.completedBinding.txtPoints.setText("+" + topic.getPoints() + " " + context.getString(R.string.topics_points));
            if (topic.getImagem() != null)
                Glide.with(context)
                        .load(topic.getImagem())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.completedBinding.imgTopic);
        }else {
            holder.binding.txtTopicTitle.setText(topic.getNome().toUpperCase());
            holder.binding.txtTopicPoints.setText("+" + topic.getPoints() + " " + context.getString(R.string.topics_points));

            if (topic.getImagem() != null)
                Glide.with(context)
                        .load(topic.getImagem())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(holder.binding.imgTopic);

            if(topic.getMissions()!=null) {

                int missionCounter = 1;
                int completedCount = 0;
                for (Mission mission : topic.getMissions()) {
                    switch (missionCounter) {
                        case 1:
                            if (mission.getTitulo() != null && !mission.getTitulo().isEmpty())
                                holder.binding.txtMission1.setText(mission.getTitulo());
                            if (mission.getImagem() != null)
                                Glide.with(context).load(mission.getImagem()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.binding.imgMission1);
                            if(mission.isCompleted()){
                                completedCount++;
                                holder.binding.txtMission1Points.setVisibility(View.GONE);
                                holder.binding.mission1CompletedLayout.setVisibility(View.VISIBLE);
                                holder.binding.mission1CompletedCheck.setVisibility(View.VISIBLE);
                            }else{
                                holder.binding.cardMission1.setOnClickListener(view -> topicMissionCickListener.onTopicMissionClick(topic,topic.getMissions().get(0), 1));
                                holder.binding.txtMission1Points.setText(mission.getValor() + " " + context.getString(R.string.mission_points));
                            }
                            break;
                        case 2:
                            if (mission.getTitulo() != null && !mission.getTitulo().isEmpty())
                                holder.binding.txtMission2.setText(mission.getTitulo());
                            if (mission.getImagem() != null)
                                Glide.with(context).load(mission.getImagem()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.binding.imgMission2);
                            if(mission.isCompleted()){
                                completedCount++;
                                holder.binding.txtMission2Points.setVisibility(View.GONE);
                                holder.binding.mission2CompletedLayout.setVisibility(View.VISIBLE);
                                holder.binding.mission2CompletedCheck.setVisibility(View.VISIBLE);
                            }else{
                                holder.binding.cardMission2.setOnClickListener(view -> topicMissionCickListener.onTopicMissionClick(topic,topic.getMissions().get(1), 2));
                                holder.binding.txtMission2Points.setText(mission.getValor() + " " + context.getString(R.string.mission_points));
                            }

                            holder.binding.cardMission2.setVisibility(View.VISIBLE);
                            break;
                        case 3:
                            if (mission.getTitulo() != null && !mission.getTitulo().isEmpty())
                                holder.binding.txtMission3.setText(mission.getTitulo());
                            if (mission.getImagem() != null)
                                Glide.with(context).load(mission.getImagem()).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.binding.imgMission3);
                            if(mission.isCompleted()){
                                completedCount++;
                                holder.binding.txtMission3Points.setVisibility(View.GONE);
                                holder.binding.mission3CompletedLayout.setVisibility(View.VISIBLE);
                                holder.binding.mission3CompletedCheck.setVisibility(View.VISIBLE);
                            }else{
                                holder.binding.cardMission3.setOnClickListener(view -> topicMissionCickListener.onTopicMissionClick(topic,topic.getMissions().get(2), 3));
                                holder.binding.txtMission3Points.setText(mission.getValor() + " " + context.getString(R.string.mission_points));
                            }
                            holder.binding.cardMission3.setVisibility(View.VISIBLE);
                            break;
                    }
                    missionCounter++;
                }

                holder.binding.txtMissionsCount.setText(String.valueOf(completedCount)+"/"+topic.getMissions().size());
                if(completedCount == topic.getMissions().size()){
                    holder.binding.btnRequestTopicPoints.setVisibility(View.VISIBLE);
                    holder.binding.btnRequestTopicPoints.setOnClickListener(view -> topicMissionCickListener.onRequestPrize(topic));
                    holder.binding.btnRequestTopicPoints.setText(context.getString(R.string.request_points)+ String.valueOf(topic.getPoints()));
                }
            }
        }
    }

    public void updateList(List<Topic> topics){
        this.topics = topics;
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull TopicViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder.binding!=null) {
            holder.binding.cardMission2.setVisibility(View.GONE);
            holder.binding.cardMission3.setVisibility(View.GONE);
        }
//        Glide.clear(holder.binding.imgTopic);
//        Glide.clear(holder.binding.imgMission1);
//        Glide.clear(holder.binding.imgMission2);
//        Glide.clear(holder.binding.imgMission3);
    }

    @Override
    public int getItemViewType(int position) {
        if(topics != null){
            if(topics.get(position).isCompleted()){
                return COMPLETED_TOPIC;
            }else{
                return NORMAL_TOPIC;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(topics!=null)
            return topics.size();
        return 0;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        ItemTopicsBinding binding;
        ItemTopicCompletedBinding completedBinding;
        public TopicViewHolder(View itemView) {
            super(itemView);
        }

        public TopicViewHolder(ItemTopicsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public TopicViewHolder(ItemTopicCompletedBinding completedBinding) {
            super(completedBinding.getRoot());
            this.completedBinding = completedBinding;
        }
    }
}
