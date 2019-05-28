package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentReceiptsBinding;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.ReceiptsPagerAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.ReceiptViewModel;
import br.com.monitoratec.loysci_android.util.CustomDateFilterDialog;

public class ReceiptsFragment extends Fragment implements View.OnClickListener {

    private FragmentReceiptsBinding binding;
    private CustomDateFilterDialog customDateFilterDialog;
    private ReceiptsPagerAdapter adapter;
    private ReceiptViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipts, container, false);
        viewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            adapter = new ReceiptsPagerAdapter(getChildFragmentManager(), getContext());
            binding.receiptsViewPager.setAdapter(adapter);
            binding.receiptsTabLayout.setupWithViewPager(binding.receiptsViewPager);
            binding.receiptsCalendarImage.setOnClickListener(this);
            viewModel.loadExtracts();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.receipts_calendar_image:
                showFilterDialog();
                break;
        }
    }

    private void showFilterDialog() {
            int filterDays = viewModel.getFilterDays().getValue() == null ? -1 : viewModel.getFilterDays().getValue();
            if (adapter.getCurrentFragment() instanceof ExtractFragment) {
                customDateFilterDialog = new CustomDateFilterDialog(getContext(), days -> {
                    viewModel.setFilterDays(days);
                    customDateFilterDialog.dismiss();
                }, filterDays);
                customDateFilterDialog.show();
            }
        }
}
