package br.com.loysciapp.loysci_android.presentation.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.FragmentExpiredBinding;
import br.com.loysciapp.loysci_android.model.History;
import br.com.loysciapp.loysci_android.presentation.ui.adapters.ExpiredVouchersAdapter;
import br.com.loysciapp.loysci_android.presentation.ui.viewModels.ReceiptViewModel;
import br.com.loysciapp.loysci_android.util.Constants;
import br.com.loysciapp.loysci_android.util.LoadStateViewsHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpiredFragment extends Fragment {

    private FragmentExpiredBinding binding;
    private ExpiredVouchersAdapter adapter;
    private ReceiptViewModel viewModel;
    private LoadStateViewsHandler loadStateViewsHandler;

    public ExpiredFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expired, container, false);
        loadStateViewsHandler = new LoadStateViewsHandler(binding.extractLoadingLayout, binding.extractFailLayout, binding.extractRefreshLayout);
        loadStateViewsHandler.setToastErrorStringResId(R.string.failed_load_extract);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getParentFragment()).get(ReceiptViewModel.class);
        binding.extractRefreshLayout.setOnRefreshListener(() -> viewModel.loadExtracts());

        ExpiredVouchersAdapter adapter = new ExpiredVouchersAdapter(getContext());
        binding.extractRecycler.setAdapter(adapter);
        binding.extractRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = adapter;

        viewModel.getIsLoading().observe(this, loadState -> loadStateViewsHandler.handleLoadState(loadState, adapter.getItemCount() > 0));
        viewModel.getAllExtracts().observe(this, this::onExtractLoaded);
    }

    private void onExtractLoaded(List<History> extracts) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long currentDate = date.getTimeInMillis();
        List<History> expiredExtracts = new ArrayList<>();
        for (History extract : extracts) {
            long startDate = extract.getDate();
            if (startDate < 1000000000000L) {
                startDate = startDate * 1000L;
                extract.setDate(startDate);
            }
            if (extract.getMetricEntry() != null) {
                if ((extract.getIdTransaction().contains("R1") || extract.getIdTransaction().contains("R2")) && startDate + Constants.SIX_MONTHS <= currentDate) {
                    expiredExtracts.add(extract);
                }
            }
        }
        adapter.populateExtracts(expiredExtracts);
        binding.extractLoadingLayout.setVisibility(View.GONE);
    }
}
