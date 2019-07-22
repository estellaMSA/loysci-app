package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentGamesBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.model.Topic;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.activities.ChallengeNetworkActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.ChallengeSeeContentActivity;
import br.com.monitoratec.loysci_android.mvp.conteudo.VerConteudoActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.MissionActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.PuzzleActivity;
import br.com.monitoratec.loysci_android.presentation.ui.activities.SubirConteudoActivity;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.MissionAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.TopicsAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.TopicMissionCickListener;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.MainViewModel;
import br.com.monitoratec.loysci_android.util.RequestPrizeDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.ID_MEMBER;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_INDEX;
import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;
import static br.com.monitoratec.loysci_android.util.Constants.RANDOM_IMG_BASE_URL;
import static br.com.monitoratec.loysci_android.util.Constants.RANDOM_SMALL_IMG_BASE_URL;
import static br.com.monitoratec.loysci_android.util.Constants.TOPIC_PARCELABLE;

public class GamesFragment extends Fragment implements TopicMissionCickListener, SimpleItemClickListener {

    FragmentGamesBinding binding;
    TopicsAdapter adapter;
    MainViewModel model;
    int missionsCounter = 0;

    Mission mission;
    int totalNumberOfMissions = 0;

    List<Challenge> challenges = new ArrayList<>();
    public static Challenge challenge;

    private TextView subencabezado;

    public static final String TAG = GamesFragment.class.getSimpleName();
    private MissionAdapter misionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_games, container, false);
        binding.loadingGames.setVisibility(View.VISIBLE);
/*
        if (challenge.getIndTipoMision().equals(Challenge.TYPE_GAMES) && challenge.getJuego().getTipo().equals(Game.TYPE_RULETA)) {
            String prizes = "";

            List<ChallengeGame> list = challenge.getJuego().getMisionJuego();

            for (ChallengeGame challengeGame : list) {
                switch (challengeGame.getIndTipoPremio()) {
                    case "M": {
                        prizes += "- " + challengeGame.getCantMetrica() + " " + challengeGame.getMetrica().getNombre();

                        break;
                    }
                    case "P": {
                        prizes += "- " + challengeGame.getPremio().getEncabezadoArte();

                        break;
                    }
                    default: {
                        continue;
                    }
                }

                if (list.indexOf(challengeGame) < list.size() - 1) {
                    prizes += "\n";
                }
            }

            subencabezado.setText(subencabezado.getText().toString() + "\n\nPremios:\n\n" + prizes);
        }
*/
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    public void loadTopics() {
//        model.loadTopics(getContext(), new ViewModelSimpleCallback() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "loadTopics: onSuccess");
//                loadTopicsMissions();
//            }
//
//            @Override
//            public void onFailed(Object object) {
//                Log.d(TAG, "loadTopics: onFailed");
//                Toast.makeText(getContext(), getString(R.string.failed_load_missions), Toast.LENGTH_SHORT).show();
////                loadTopics();
//            }
//        }, true);


        newLoadMissions();
    }


    public void newLoadMissions() {

        getNumberOfMissions();
        missionsCounter = 0;
        model.loadMission(new ViewModelSimpleCallback() {

            @Override
            public void onSuccess() {

                getNumberOfMissions();
                createMissionAdapter();

            }


            @Override
            public void onFailed(Object object) {


                Log.d(TAG, "loadTopicsMissions: onFailed");

                Toast.makeText(getContext(), getString(R.string.failed_load_missions), Toast.LENGTH_SHORT).show();

                newLoadMissions();

            }
        });


    }

    private void createMissionAdapter() {
        misionAdapter = new MissionAdapter(model.missionList, getActivity(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerChallenges.setLayoutManager(layoutManager);
        binding.recyclerChallenges.setAdapter(misionAdapter);
        binding.loadingGames.setVisibility(View.GONE);
        binding.txtEmpty.setVisibility(View.GONE);
        misionAdapter.notifyDataSetChanged();
    }

    public void loadTopicsMissions() {

        getNumberOfMissions();

        missionsCounter = 0;

        for (Topic topic : model.topicList) {
            if (topic.getMissions() != null) {
                for (Mission mission : topic.getMissions()) {
                    model.loadTopicsMission(topic.getId(), mission.getIdMissao(), new ViewModelSimpleCallback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "loadTopicsMissions: onSuccess");
                            missionsCounter++;
                            checkResults();
                        }

                        @Override
                        public void onFailed(Object object) {
                            Log.d(TAG, "loadTopicsMissions: onFailed");
                            Toast.makeText(getContext(), getString(R.string.failed_load_missions), Toast.LENGTH_LONG).show();

                            model.loadTopicsMission(topic.getId(), mission.getIdMissao(), new ViewModelSimpleCallback() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "loadTopicsMissions: onSuccess");

                                    missionsCounter++;
                                    checkResults();
                                }

                                @Override
                                public void onFailed(Object object) {
                                    Log.d(TAG, "loadTopicsMissions: onFailed");

                                    Toast.makeText(getContext(), getString(R.string.failed_load_missions), Toast.LENGTH_SHORT).show();

                                    missionsCounter++;
                                    checkResults();
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    private void getNumberOfMissions() {

        totalNumberOfMissions = 0;

        if (model.missionList != null)
            totalNumberOfMissions += model.missionList.size();

    }

    private void checkResults() {
        if (missionsCounter == totalNumberOfMissions) {
            Log.d(TAG, "loadAllMissions: onSuccess");

//            adapter = new TopicsAdapter(model.topicList, getContext(), model, GamesFragment.this);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//            binding.recyclerChallenges.setLayoutManager(layoutManager);
//            binding.recyclerChallenges.setAdapter(adapter);
//            binding.loadingGames.setVisibility(View.GONE);
//            binding.txtEmpty.setVisibility(View.GONE);
//            adapter.notifyDataSetChanged();

            List<Mission> missions = new ArrayList<>();


            for (Topic topic : model.topicList) {

                for (Mission mission : topic.getMissions()) {

                    mission.setTopic(topic);
                    missions.add(mission);

                }

            }

            misionAdapter = new MissionAdapter(missions, getActivity(), this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.recyclerChallenges.setLayoutManager(layoutManager);
            binding.recyclerChallenges.setAdapter(misionAdapter);
            binding.loadingGames.setVisibility(View.GONE);
            binding.txtEmpty.setVisibility(View.GONE);
            misionAdapter.notifyDataSetChanged();


        }
    }

    private List<Topic> getMockTopics() {
        Challenge challenge1 = new Challenge("Challenge 1", RANDOM_SMALL_IMG_BASE_URL, true);
        Challenge challenge2 = new Challenge("Challenge 2", RANDOM_SMALL_IMG_BASE_URL, true);
        Challenge challenge3 = new Challenge("Challenge 3", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge4 = new Challenge("Challenge 4", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge5 = new Challenge("Challenge 5", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge6 = new Challenge("Challenge 6", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge7 = new Challenge("Challenge 7", RANDOM_SMALL_IMG_BASE_URL, true);
        Challenge challenge8 = new Challenge("Challenge 8", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge9 = new Challenge("Challenge 9", RANDOM_SMALL_IMG_BASE_URL, true);
        Challenge challenge10 = new Challenge("Challenge 10", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge11 = new Challenge("Challenge 11", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge12 = new Challenge("Challenge 12", RANDOM_SMALL_IMG_BASE_URL, false);
        Challenge challenge13 = new Challenge("Challenge 13", RANDOM_SMALL_IMG_BASE_URL, true);
        Challenge challenge14 = new Challenge("Challenge 14", RANDOM_SMALL_IMG_BASE_URL, false);

        Mission mission1 = new Mission("Title 1", RANDOM_SMALL_IMG_BASE_URL, 72, true, Arrays.asList(challenge1, challenge2, challenge3, challenge4, challenge5));
        Mission mission2 = new Mission("Title 2", RANDOM_SMALL_IMG_BASE_URL, 90, false, Arrays.asList(challenge1, challenge2, challenge3, challenge4, challenge5,
                challenge6, challenge7, challenge8, challenge9, challenge10, challenge11, challenge12, challenge13, challenge14));
        Mission mission3 = new Mission("Title 3", RANDOM_SMALL_IMG_BASE_URL, 51, false, Arrays.asList(challenge1, challenge2, challenge3, challenge4, challenge5));
        List<Mission> missions1 = Arrays.asList(mission1);
        List<Mission> missions2 = Arrays.asList(mission1, mission2);
        List<Mission> missions3 = Arrays.asList(mission1, mission2, mission3);

        Topic topic1 = new Topic("Title Topic 1", 25, RANDOM_IMG_BASE_URL, missions3);
        Topic topic2 = new Topic("Title Topic 2", 30, RANDOM_IMG_BASE_URL, missions2);
        Topic topic3 = new Topic("Title Topic 3", 40, RANDOM_IMG_BASE_URL, missions1);
        Topic topic4 = new Topic("Title Topic 4", 50, RANDOM_IMG_BASE_URL, missions3);
        Topic topic5 = new Topic("Title Topic 5", 60, RANDOM_IMG_BASE_URL, missions2);
        Topic topic6 = new Topic("Title Topic 6", 70, RANDOM_IMG_BASE_URL, missions1);
        Topic topic7 = new Topic("Title Topic 7", 80, RANDOM_IMG_BASE_URL, missions3);
        Topic topic8 = new Topic("Title Topic 8", 90, RANDOM_IMG_BASE_URL, missions2);

        return Arrays.asList(topic1, topic2, topic3, topic4, topic5, topic6, topic7, topic8);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onTopicMissionClick(Topic topic, Mission mission, int index) {
        this.mission = mission;

        Intent intent = new Intent(getContext(), MissionActivity.class);
        intent.putExtra(MISSION_PARCELABLE, mission);
        intent.putExtra(TOPIC_PARCELABLE, topic);
        intent.putExtra(MISSION_INDEX, index);
        intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onRequestPrize(Topic topic) {
        RequestPrizeDialog dialog = new RequestPrizeDialog(getContext(), topic);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            for (int i = 0; i < model.topicList.size(); i++) {
                Topic t = model.topicList.get(i);
                for (int j = 0; j < t.getMissions().size(); j++) {
                    Mission m = t.getMissions().get(j);

                    if (mission != null && m.getTitulo().equals(mission.getTitulo())) {

                        t.getMissions().set(j, data.getParcelableExtra(MISSION_PARCELABLE));
                        adapter.notifyDataSetChanged();
                        break;

                    }
                }

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTopics();
    }


    @Override
    public void onSimpleItemClick(Object object) {

        this.mission = (Mission) object;

        List<Challenge> desafios = mission.getChallenges();
        Challenge desafio = desafios.get(0);
        String tipo = desafio.getIndTipoMision();
        if (tipo.equals("E")) {
            Intent intent = new Intent(getContext(), MissionActivity.class);
            intent.putExtra("mission_parcelable", mission);
            intent.putExtra(MISSION_INDEX, 0);
            intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
            startActivityForResult(intent, 0);
        } else if (tipo.equals("S")) {
            Intent intent = new Intent(getContext(), SubirConteudoActivity.class);
            intent.putExtra("mission_parcelable", mission);
            intent.putExtra(MISSION_INDEX, 0);
            intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
            intent.putExtra("imagem", mission.getImagem());
            startActivityForResult(intent, 0);
        } else if (tipo.equals("J")) {
            Intent intent = new Intent(getContext(), PuzzleActivity.class);
            //intent.putExtra("mission_parcelable", mission);
            //intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
            PuzzleActivity.challenge = desafio;
            //setRegistrarVistaMision();
            intent.putExtra("challenge-id", desafio.getIdMision());
            startActivityForResult(intent, 2);
        } else if (tipo.equals("R")) {
            Intent intent = new Intent(getContext(), ChallengeNetworkActivity.class);
            intent.putExtra("mission_parcelable", mission);
            intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
            intent.putExtra("image", desafio.getMisionRedSocial().getUrlImagen());
            intent.putExtra("message", desafio.getMisionRedSocial().getMensaje());
            intent.putExtra("title", desafio.getMisionRedSocial().getTituloUrl());
            intent.putExtra("urlGeneral", desafio.getMisionRedSocial().getUrlObjectivo());
            ChallengeNetworkActivity.challenge = desafio;
            //setRegistrarVistaMision();

            startActivityForResult(intent, 2);
        } else if (tipo.equals("V")) {

            Intent intent = new Intent(getContext(), VerConteudoActivity.class);
            intent.putExtra("mission_parcelable", mission);

            intent.putExtra(MISSION_INDEX, 0);
            intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
            intent.putExtra("imagem", mission.getImagem());
            startActivityForResult(intent, 0);
        }

        //} else if (tipo.equals("V")) {
        //Intent intent = new Intent(getContext(), ChallengeSeeContentActivity.class);
        //setRegistrarVistaMision();
        //intent.putExtra("mission_parcelable", mission);
        //intent.putExtra(ID_MEMBER, model.profile.getIdMiembro());
        //startActivityForResult(intent, 2);
    }

    //Se registra como vista la mision actual.
    private void setRegistrarVistaMision() {
        LoyaltyApi.setRegistrarVistaMision(challenge.getIdMision(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Toast.makeText(ChallengeDetailActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.println(Log.ERROR, "RegistrarVistaMision", t.getMessage());
            }
        });
    }
}
