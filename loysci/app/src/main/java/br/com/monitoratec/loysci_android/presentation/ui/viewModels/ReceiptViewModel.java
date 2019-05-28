package br.com.monitoratec.loysci_android.presentation.ui.viewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;

import java.util.List;

import br.com.monitoratec.loysci_android.model.History;
import br.com.monitoratec.loysci_android.model.LoadState;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ExtractLoadListener;
import br.com.monitoratec.loysci_android.presentation.ui.presenters.ExtractPresenter;
import br.com.monitoratec.loysci_android.util.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptViewModel extends ViewModel implements ExtractLoadListener {
    private MutableLiveData<List<History>> allExtracts;
    private MutableLiveData<LoadState> isLoading;
    private MutableLiveData<Integer> filterDays;
    private ExtractPresenter extractPresenter;

    public ReceiptViewModel() {
        isLoading = new MutableLiveData<>();
        allExtracts = new MutableLiveData<>();
        filterDays = new MutableLiveData<>();

        extractPresenter = new ExtractPresenter(this);
    }

    public void loadExtracts() {
        if (isLoading.getValue() != LoadState.LOADING) {
            this.isLoading.postValue(LoadState.LOADING);
            String jsonProfile = Prefs.getProfile();
            if (jsonProfile != null) {
                Profile savedProfile = new Gson().fromJson(jsonProfile, Profile.class);
                this.extractPresenter.loadAllHistory(savedProfile.getNombreUsuario());
            } else {
                LoyaltyApi.getProfile(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Profile profile = response.body();
                            if (profile != null && profile.getNombreUsuario() != null && !profile.getNombreUsuario().isEmpty()) {
                                ReceiptViewModel.this.extractPresenter.loadAllHistory(profile.getNombreUsuario());
                            } else {
                                onLoadExtractFailed();
                            }
                        } else {
                            onLoadExtractFailed();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {
                        onLoadExtractFailed();
                    }
                });
            }
        }
    }

    public void setFilterDays(int days) {
        filterDays.postValue(days);
    }

    public MutableLiveData<List<History>> getAllExtracts() {
        return allExtracts;
    }

    public MutableLiveData<LoadState> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Integer> getFilterDays() {
        return filterDays;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void onExtractLoaded(List<History> extracts) {
        isLoading.postValue(LoadState.LOADED);
        allExtracts.postValue(extracts);
    }

    @Override
    public void onLoadExtractFailed() {
        isLoading.postValue(LoadState.FAILED);
    }
}
