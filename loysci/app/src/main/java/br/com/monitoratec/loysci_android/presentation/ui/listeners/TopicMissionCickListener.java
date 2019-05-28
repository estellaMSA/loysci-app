package br.com.monitoratec.loysci_android.presentation.ui.listeners;

import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.model.Topic;

/**
 * Created by Pedro Mazarini on 23/Oct/2018
 */
public interface TopicMissionCickListener {
    void onTopicMissionClick(Topic topic, Mission mission, int index);
    void onRequestPrize(Topic topic);
}
