package br.com.loysciapp.loysci_android.presentation.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.loysciapp.loysci_android.model.LoadState;
import br.com.loysciapp.loysci_android.model.Reward;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.VouchersFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherFragmentPresenter {
    private VouchersFragment context;
    private String points;
    private String category;

    public VoucherFragmentPresenter(VouchersFragment context) {
        this.context = context;
    }

    public void getAvailableRewardsFiltered(){
        context.setLoadState(LoadState.LOADING);
        LoyaltyApi.getRewardsWithoutReedem(new Callback<List<Reward>>() {
            @Override
            public void onResponse(Call<List<Reward>> call, Response<List<Reward>> response) {
                List<Reward> rewardList = new ArrayList<>();


                if (response.body() != null) {
                    for (Reward reward : response.body()) {
                        try {
                            boolean include = true;

                            if (points != null && !points.isEmpty() && !String.valueOf(reward.getValorMoneda()).equals(points)) {
                                include = false;
                            }

                            if (category != null && !category.isEmpty() && !reward.getSubencabezadoArte().equals(category)) {
                                include = false;
                            }

                            if (include) {
                                rewardList.add(reward);
                            }
                        }
                        catch (Exception ex){
                            String teste = "123";
                        }
                    }
                }

                if(!rewardList.isEmpty()) {
                    context.setNewVouchersListInAdapter(rewardList);
                    context.setPlaceHolderVisibility(false);

                    if ((points == null || points.isEmpty()) && (category == null || category.isEmpty())) {
                        getAllCategories(rewardList);
                        getAllPoints(rewardList);
                    }
                } else {
                    context.setNewVouchersListInAdapter(new ArrayList<>());
                    context.setPlaceHolderVisibility(true);
                }
                context.setLoadState(LoadState.LOADED);
            }

            @Override
            public void onFailure(Call<List<Reward>> call, Throwable t) {
                context.setLoadState(LoadState.FAILED);
            }
        });
    }

    private void getAllCategories(List<Reward> rewardList){
        ArrayList<String> categories = new ArrayList<>();
        for (Reward reward : rewardList){
            if(!categories.contains(reward.getSubencabezadoArte())){
                categories.add(reward.getSubencabezadoArte());
            }
        }
        context.setNewCategoriesListInAdapter(categories);
    }

    private void getAllPoints(List<Reward> rewardList){
        ArrayList<Long> points = new ArrayList<>();
        for(Reward reward : rewardList){
            if(!points.contains(reward.getValorMoneda())){
                points.add(reward.getValorMoneda());
            }
        }
        Collections.sort(points);
        context.setNewPointsListInAdapter(points);
    }

    public void setCategoryFilter(String category) {
        this.category = category;
    }

    public void setPointsFilter(String points) {
        this.points = points;
    }

    
}
