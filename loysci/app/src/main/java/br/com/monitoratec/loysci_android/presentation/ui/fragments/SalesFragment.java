package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentSalesBinding;
import br.com.monitoratec.loysci_android.model.LoadState;
import br.com.monitoratec.loysci_android.model.Promotion;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.PromotionsAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.presenters.SalesFragmentPresenter;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.MainViewModel;
import br.com.monitoratec.loysci_android.util.LoadStateViewsHandler;
import br.com.monitoratec.loysci_android.util.PromotionDetailsDialog;

public class SalesFragment extends Fragment implements SimpleItemClickListener {

    //private OnFragmentInteractionListener mListener;
    FragmentSalesBinding binding;
    PromotionsAdapter adapter;
    SalesFragmentPresenter presenter;
    MainViewModel model;
    LoadStateViewsHandler loadStateViewsHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sales, container, false);

        loadStateViewsHandler = new LoadStateViewsHandler(binding.loadingPromotions, binding.txtEmpty, binding.refreshLayout);
        loadStateViewsHandler.setToastErrorStringResId(R.string.failed_load_promotions);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new SalesFragmentPresenter(this,binding);
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        populatePromotions();
        binding.refreshLayout.setOnRefreshListener(this::populatePromotions);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    public void populatePromotions() {
        loadStateViewsHandler.handleLoadState(LoadState.LOADING, false);
        model.loadPromotions(new ViewModelSimpleCallback(){
            @Override
            public void onSuccess() {
                // No need to recreate adapter each time.
                loadStateViewsHandler.handleLoadState(LoadState.LOADED, false);
                //model.promotions = Collections.emptyList();
                if(model.promotions == null || model.promotions.size()==0) binding.txtEmpty.setVisibility(View.VISIBLE);
                else {

                    adapter = new PromotionsAdapter(model.promotions, SalesFragment.this, SalesFragment.this);
                    binding.recyclerSales.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.recyclerSales.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
            @Override
            public void onFailed(Object object) {
                loadStateViewsHandler.handleLoadState(LoadState.FAILED, adapter != null && adapter.getItemCount() > 0);
            }
        }, true);

    }

    @Override
    public void onSimpleItemClick(Object object) {
        if(object instanceof Promotion){
//            Intent intent = new Intent(getContext(), PromotionDetailActivity.class);
//            intent.putExtra(PROMOTION_PARCELABLE, (Promotion) object);
//            startActivity(intent);

            PromotionDetailsDialog promotionDialog = new PromotionDetailsDialog(getContext(), ((Promotion) object), new PromotionDetailsDialog.OnButtonClicked() {
                @Override
                public void seeMore(String urlOrCodigo) {
                    if (urlOrCodigo.startsWith("http://") ||
                            urlOrCodigo.startsWith("https://")) {
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(urlOrCodigo));
                            startActivity(i);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.Failed_load_promotion, Toast.LENGTH_LONG).show();
                    }
                }
            });
            promotionDialog.show();
        }
    }


}
