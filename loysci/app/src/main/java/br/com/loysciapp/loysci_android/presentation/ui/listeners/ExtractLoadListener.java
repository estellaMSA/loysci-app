package br.com.loysciapp.loysci_android.presentation.ui.listeners;

import java.util.List;

import br.com.loysciapp.loysci_android.model.History;

/**
 * Created by Felipe Galeote on 29,Outubro,2018
 */
public interface ExtractLoadListener {
    void onExtractLoaded(List<History> extracts);
    void onLoadExtractFailed();
}
