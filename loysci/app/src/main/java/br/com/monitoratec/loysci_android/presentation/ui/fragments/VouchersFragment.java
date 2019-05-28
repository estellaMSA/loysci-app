package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentVouchersBinding;
import br.com.monitoratec.loysci_android.model.LoadState;
import br.com.monitoratec.loysci_android.model.Reward;
import br.com.monitoratec.loysci_android.presentation.presenter.VoucherFragmentPresenter;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.CategoriesFilterAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.PointsFilterAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.VouchersAdapter;
import br.com.monitoratec.loysci_android.util.LoadStateViewsHandler;

public class VouchersFragment extends Fragment {

    private VoucherOptionsListener mListener;
    private VoucherFragmentPresenter presenter;
    private VouchersAdapter vouchersAdapter;
    private CategoriesFilterAdapter categoriesFilterAdapter;
    private PointsFilterAdapter pointsFilterAdapter;
    private FragmentVouchersBinding binding;
    private LoadStateViewsHandler loadStateViewsHandler;
    private boolean expanded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vouchers, container, false);

        loadStateViewsHandler = new LoadStateViewsHandler(binding.loadingVouchers, null, binding.refreshLayout);
        loadStateViewsHandler.setToastErrorStringResId(R.string.failed_load_vouchers);

        configVouchersAdapter();
        configCategoriesFilterAdapter();
        configPointsFilterAdapter();

        binding.ivDropdownArrow.setOnClickListener(v -> {
            rotateDropdownImage();
        });

        return binding.getRoot();
    }

    private void rotateDropdownImage() {
        if (expanded) {
            RotateAnimation animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            binding.ivDropdownArrow.startAnimation(animation);
            binding.ivDropdownArrow.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_down, null));
            binding.filterLayoutExpanded.setVisibility(View.GONE);
            expanded = false;
        } else {
            RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            binding.ivDropdownArrow.startAnimation(animation);
            binding.ivDropdownArrow.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_up, null));
            binding.filterLayoutExpanded.setVisibility(View.VISIBLE);
            expanded = true;
        }
    }

    private void configVouchersAdapter() {
        binding.rvVouchers.setLayoutManager(new LinearLayoutManager(getActivity()));
        vouchersAdapter = new VouchersAdapter(getContext());
        binding.rvVouchers.setAdapter(vouchersAdapter);
    }

    private void configCategoriesFilterAdapter() {
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoriesFilterAdapter = new CategoriesFilterAdapter(getContext(), new CategoriesFilterAdapter.OnCategorySelectedListener() {
            @Override
            public void onCategorySelected(String category) {
                presenter.setCategoryFilter(category);
                presenter.getAvailableRewardsFiltered();
            }

            @Override
            public void onCategoryUnselected() {
                presenter.setCategoryFilter(null);
                presenter.getAvailableRewardsFiltered();
            }
        });
        binding.rvCategories.setAdapter(categoriesFilterAdapter);
    }

    private void configPointsFilterAdapter() {
        binding.rvPoints.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        pointsFilterAdapter = new PointsFilterAdapter(getContext(), new PointsFilterAdapter.OnPointsSelectedListener() {
            @Override
            public void onPointsSelected(String points) {
                presenter.setPointsFilter(points);
                presenter.getAvailableRewardsFiltered();
            }

            @Override
            public void onPointsUnselected() {
                presenter.setPointsFilter(null);
                presenter.getAvailableRewardsFiltered();
            }
        });
        binding.rvPoints.setAdapter(pointsFilterAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new VoucherFragmentPresenter(this);
        presenter.getAvailableRewardsFiltered();

        binding.refreshLayout.setOnRefreshListener(() -> presenter.getAvailableRewardsFiltered());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null)
            mListener = null;
    }

    public interface VoucherOptionsListener {
        void onVoucherOptionSelected(int option);
    }

    public void setNewVouchersListInAdapter(List<Reward> list) {
        vouchersAdapter.setItems(list);
    }

    public void setNewPointsListInAdapter(ArrayList<Long> points) {
        pointsFilterAdapter.setPoints(points);
    }

    public void setNewCategoriesListInAdapter(ArrayList<String> categories) {
        categoriesFilterAdapter.setCategories(categories);
    }

    public void setLoadState(LoadState loadState){
        loadStateViewsHandler.handleLoadState(loadState, false);
    }

    public void setPlaceHolderVisibility(boolean visible) {
        if (visible)
            binding.txtEmpty.setVisibility(View.VISIBLE);
        else
            binding.txtEmpty.setVisibility(View.GONE);
    }
}
