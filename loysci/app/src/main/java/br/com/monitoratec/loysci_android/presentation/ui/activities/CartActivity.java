package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityCartBinding;
import br.com.monitoratec.loysci_android.model.Reward;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.VouchersCartAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.presenters.CartPresenter;
import br.com.monitoratec.loysci_android.util.Prefs;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartPresenter mPresenter;
    private VouchersCartAdapter adapter;
    private List<Reward> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        mPresenter = new CartPresenter(this);
        voucherList = Prefs.getCart();

        if (voucherList != null && !voucherList.isEmpty()) {
            binding.tvNoVouchers.setVisibility(View.GONE);
            binding.cartDetailsLayout.setVisibility(View.VISIBLE);
            binding.tvQuantity.setText(String.valueOf(voucherList.size()));
            binding.tvTotalQuantity.setText(getString(R.string.points, mPresenter.calculatePoints(voucherList)));
            if (voucherList.size() > 1)
                binding.tvBenefitsLabel.setText(getString(R.string.added_benefits));
            else
                binding.tvBenefitsLabel.setText(getString(R.string.benefit_added));
        } else {
            binding.tvNoVouchers.setVisibility(View.VISIBLE);
            binding.cartDetailsLayout.setVisibility(View.GONE);
        }

        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.my_cart));

        adapter = new VouchersCartAdapter(this, voucherList);

        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerCart.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        binding.loading.loadingText.setText(getString(R.string.processing));

        binding.purchaseButtonLayout.setOnClickListener(view -> {
            view.setClickable(false);
            setModalVisible(true);
            mPresenter.proceedWithPurchaseRequest(voucherList, new SimpleCallback<Void>() {
                @Override
                public void onResponse(Void response) {
                    view.setClickable(true);
                    setModalVisible(false);
                }

                @Override
                public void onError(Throwable t) {
                    view.setClickable(true);
                    setModalVisible(false);
                }
            });
        });

    }

    private void setModalVisible(boolean visible) {
        if (!visible) {
            binding.includeToolbar.appbar.setAlpha(1f);
            binding.recyclerCart.setAlpha(1f);
            binding.cartDetailsLayout.setAlpha(1f);
            binding.loading.loadingLayout.setVisibility(View.GONE);
        } else {
            binding.includeToolbar.appbar.setAlpha(0.2f);
            binding.recyclerCart.setAlpha(0.2f);
            binding.cartDetailsLayout.setAlpha(0.2f);
            binding.loading.loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    public void refreshVoucherList() {
        List<Reward> rewardList = Prefs.getCart();

        if (rewardList != null && !rewardList.isEmpty()) {
            adapter.setItems(rewardList);
            binding.tvNoVouchers.setVisibility(View.GONE);
            binding.tvQuantity.setText(String.valueOf(voucherList.size()));
            binding.cartDetailsLayout.setVisibility(View.VISIBLE);
            binding.tvTotalQuantity.setText(getString(R.string.points, mPresenter.calculatePoints(voucherList)));
        } else {
            adapter.setItems(new ArrayList<>());
            binding.tvNoVouchers.setVisibility(View.VISIBLE);
            binding.cartDetailsLayout.setVisibility(View.GONE);
        }
    }

    public void refreshTotalPoints(List<Reward> voucherList) {
        binding.tvTotalQuantity.setText(getString(R.string.points, mPresenter.calculatePoints(voucherList)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
