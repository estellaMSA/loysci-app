package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityMisionBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.model.Topic;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.MissionActivitiesAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import br.com.monitoratec.loysci_android.util.Utilities;

import static br.com.monitoratec.loysci_android.util.Constants.CHALLENGE_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Constants.ID_MEMBER;
import static br.com.monitoratec.loysci_android.util.Constants.ID_MISSION;
import static br.com.monitoratec.loysci_android.util.Constants.ID_TOPIC;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_FINISHED;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_INDEX;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_RIGHT;
import static br.com.monitoratec.loysci_android.util.Constants.QUIZ_COMPLETED_WRONG;
import static br.com.monitoratec.loysci_android.util.Constants.REMAINING_ACTIVITIES;
import static br.com.monitoratec.loysci_android.util.Constants.REWARD_TOTAL;
import static br.com.monitoratec.loysci_android.util.Constants.TOPIC_PARCELABLE;

public class MissionActivity extends AppCompatActivity implements SimpleItemClickListener {

    ActivityMisionBinding binding;
    Mission mission;
    Topic topic;
    MissionActivitiesAdapter adapter;
    int missionIndex;
    String idMember;
    Challenge challenge;
    List<Challenge> challenges = new ArrayList<>();
    int completedCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mision);
        setSupportActionBar(binding.includeToolbar.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        mission = getIntent().getParcelableExtra(MISSION_PARCELABLE);
        missionIndex = getIntent().getIntExtra(MISSION_INDEX, 0);

        idMember = getIntent().getStringExtra(ID_MEMBER);

        binding.includeToolbar.toolbarTitle.setText(R.string.mission_activities);

        binding.recyclerMisions.setLayoutManager(new LinearLayoutManager(this));
        Collections.sort(mission.getChallenges(), new Utilities.challengesSorter());




        adapter = new MissionActivitiesAdapter(mission.getChallenges(), this, this);
        binding.recyclerMisions.setAdapter(adapter);


        int pontos = 0;

        for (Challenge perguntas : mission.getChallenges()) {

            pontos += perguntas.getValor();
        }

        binding.txtTopicPoints.setText(getString(R.string.topic_points, pontos));
        binding.txtCompleteToWin.setText(mission.getTitulo());
        binding.txtMissionTitle.setText(mission.getTitulo());
        binding.txtTopicTitle.setText(mission.getTitulo());

        if(mission.getChallenges().size() > 0)
            Glide.with(this).load(mission.getChallenges().get(0).getImagen()).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imgMission);


        Glide.with(this).load(mission.getImagem()).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imgTopic);

        updateMissionPoints();
        updateCompletedCounter();
    }

    private void updateMissionPoints() {

        int missionPoints = 0;

        for (Challenge challenge : mission.getChallenges()) {
            if (challenge.getValor() != 0 && challenge.isCompleted())
                missionPoints += challenge.getValor();
        }

        if (missionPoints != 0) {
            binding.txtMissionPoints.setText(getString(R.string.topic_points, missionPoints));
            binding.txtMissionPoints.setVisibility(View.VISIBLE);
        } else
            binding.txtMissionPoints.setVisibility(View.GONE);
    }

    private void updateCompletedCounter() {

        completedCounter = 0;
        for (Challenge challenge : mission.getChallenges()) {
            if (challenge.getValor() != 0 && challenge.isCompleted()) completedCounter++;
        }
        binding.txtActivitiesCount.setText(completedCounter + "/" + mission.getChallenges().size());
    }

    private void startQuizz(Challenge challenge) {
        Intent intent = new Intent(this, QuizActivity.class);

        int rewardTotal = 0;
        for (Challenge c : challenges)
            rewardTotal += c.getValor();

        this.challenge = challenge;
        intent.putExtra(CHALLENGE_PARCELABLE, challenge);
        intent.putExtra(ID_MISSION, mission.getIdMissao());
        intent.putExtra(ID_TOPIC, 0);
        intent.putExtra(ID_MEMBER, idMember);
        intent.putExtra(REMAINING_ACTIVITIES, challenges.size() - completedCounter);
        intent.putExtra(REWARD_TOTAL, rewardTotal);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MISSION_FINISHED) {
            setResult(MISSION_FINISHED);
            finish();
        } else if (resultCode == QUIZ_COMPLETED_RIGHT || resultCode == QUIZ_COMPLETED_WRONG) {
            if (challenge != null) {
                for (Challenge c : mission.getChallenges()) {
                    if (challenge != null && c.getNombre().equals(challenge.getNombre())) {

                        if (resultCode == QUIZ_COMPLETED_RIGHT) {
                            challenge.setStatus("C");
                            adapter.notifyDataSetChanged();
                            break;
                        } else {
                            challenge.setStatus("E");
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }

            updateMissionPoints();
            updateCompletedCounter();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSimpleItemClick(Object object) {
        if (!((Challenge) object).isCompleted())
            startQuizz(((Challenge) object));
    }
}
