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
import br.com.loysciapp.loysci_android.databinding.FragmentExtractBinding;
import br.com.loysciapp.loysci_android.model.History;
import br.com.loysciapp.loysci_android.presentation.ui.adapters.ExtractAdapter;
import br.com.loysciapp.loysci_android.presentation.ui.viewModels.ReceiptViewModel;
import br.com.loysciapp.loysci_android.util.Constants;
import br.com.loysciapp.loysci_android.util.DateUtil;
import br.com.loysciapp.loysci_android.util.LoadStateViewsHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExtractFragment extends Fragment {

    FragmentExtractBinding binding;
    private ExtractAdapter adapter;
    private List<History> extracts = new ArrayList<History>();
    private int filterDays;
    private ReceiptViewModel viewModel;
    private LoadStateViewsHandler loadStateViewsHandler;

    public ExtractFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_extract, container, false);
        loadStateViewsHandler = new LoadStateViewsHandler(binding.extractLoadingLayout, binding.extractFailLayout, binding.extractRefreshLayout);
        loadStateViewsHandler.setToastErrorStringResId(R.string.failed_load_extract);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(getParentFragment()).get(ReceiptViewModel.class);
        binding.extractRefreshLayout.setOnRefreshListener(() -> viewModel.loadExtracts());

        ExtractAdapter adapter = new ExtractAdapter(getContext());
        binding.extractRecycler.setAdapter(adapter);
        binding.extractRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = adapter;

        viewModel.getAllExtracts().observe(this, this::onExtractLoaded);
        viewModel.getIsLoading().observe(this, loadState -> loadStateViewsHandler.handleLoadState(loadState, !extracts.isEmpty()));
        viewModel.getFilterDays().observe(this, this::filterByDays);
    }

    private void filterByDays(int days) {
        filterDays = days;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        long currentDate = date.getTimeInMillis();
        long filterMillis = DateUtil.convertDaysToMilliseconds(filterDays);
        long startDate;
        List<History> filteredExtracts = new ArrayList<>();
        for (History extract : this.extracts) {
            startDate = extract.getDate();
            if (extract.getIdTransaction().contains("R1") || extract.getIdTransaction().contains("R2")) {
                if (startDate < 1000000000000L) {
                    startDate = startDate * 1000L;
                    extract.setDate(startDate);
                }
                if (startDate + Constants.SIX_MONTHS >= currentDate) {
                    if (filterDays == -1) {
                        filteredExtracts.add(extract);
                    } else if (currentDate - startDate <= filterMillis) {
                        filteredExtracts.add(extract);
                    }
                }
            } else {
                if (filterDays == -1) {
                    filteredExtracts.add(extract);
                } else if (currentDate - startDate <= filterMillis) {
                    filteredExtracts.add(extract);
                }
            }

        }
        adapter.populateExtracts(filteredExtracts);
    }

    private void onExtractLoaded(List<History> extracts) {
        this.extracts = extracts;
        if (viewModel.getFilterDays().getValue() != null)
            filterByDays(viewModel.getFilterDays().getValue());
        else
            filterByDays(-1);
    }
}
