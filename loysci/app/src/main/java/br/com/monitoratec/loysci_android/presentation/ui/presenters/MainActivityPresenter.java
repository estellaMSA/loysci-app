package br.com.monitoratec.loysci_android.presentation.ui.presenters;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityMainBinding;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionRequest;
import br.com.monitoratec.loysci_android.model.VoucherTransaction.VoucherTransactionResponse;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.activities.MainActivity;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.SectionsPagerAdapter;
import br.com.monitoratec.loysci_android.util.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.BONUS_CARD_TYPE_DIGITAL_VALUE;
import static br.com.monitoratec.loysci_android.util.Constants.CARD_TYPE_DIGITAL;
import static br.com.monitoratec.loysci_android.util.Constants.TAB_VOUCHERS;

public class MainActivityPresenter {

    MainActivity mainActivity;
    private ActivityMainBinding binding;
    SectionsPagerAdapter sectionsPagerAdapter;
    String cardType;


    public MainActivityPresenter(MainActivity mainActivity, ActivityMainBinding binding) {
        this.mainActivity = mainActivity;
        this.binding = binding;
        setupNavigation();
    }

    private void setupNavigation() {

        sectionsPagerAdapter = new SectionsPagerAdapter(mainActivity.getSupportFragmentManager(), mainActivity);

        binding.mainViewPager.setAdapter(sectionsPagerAdapter);
        binding.mainViewPager.setOffscreenPageLimit(2);

        binding.mainTab.setupWithViewPager(binding.mainViewPager);

        binding.mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < binding.mainTab.getTabCount(); i++) {
                    binding.mainTab.getTabAt(i).getIcon().setAlpha(110);
                }
                binding.mainTab.getTabAt(position).getIcon().setAlpha(255);

                if (position == TAB_VOUCHERS) {
                    binding.notificationLayout.setVisibility(View.GONE);
                    binding.cartLayout.setVisibility(View.VISIBLE);
                } else {
                    binding.notificationLayout.setVisibility(View.VISIBLE);
                    binding.cartLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < binding.mainTab.getTabCount(); i++) {
            switch (i) {
                case 0:
                    binding.mainTab.getTabAt(i).setIcon(R.drawable.ic_home);
                    break;
                case 1:
                    binding.mainTab.getTabAt(i).setIcon(R.drawable.ic_receipt);
                    break;
                case 2:
                    binding.mainTab.getTabAt(i).setIcon(R.drawable.ic_store_bottom);
                    break;
                case 3:
                    binding.mainTab.getTabAt(i).setIcon(R.drawable.ic_game);
                    break;
                case 4:
                    binding.mainTab.getTabAt(i).setIcon(R.drawable.trofeu);
                    break;
            }
            if(i <5) {
                binding.mainTab.getTabAt(i).getIcon().setAlpha(128);
                binding.mainTab.getTabAt(i).getIcon().setTint(ContextCompat.getColor(mainActivity, R.color.colorPrimary));
            }
        }
        binding.mainTab.getTabAt(0).getIcon().setAlpha(255);
        binding.mainTab.setSelectedTabIndicatorColor(ContextCompat.getColor(mainActivity, R.color.colorPrimary));
    }

    public void setPoints(String idMember, int points, String cardType, String transactionType, String description) {

        this.cardType = cardType;

        VoucherTransactionRequest voucherTransactionRequest;
        voucherTransactionRequest = new VoucherTransactionRequest();

        voucherTransactionRequest.setDate(System.currentTimeMillis());
        voucherTransactionRequest.setTransactionDesc(description);
        voucherTransactionRequest.setIdMember(idMember);
        voucherTransactionRequest.setSubtotal(points);
        voucherTransactionRequest.setTotal(points);
        voucherTransactionRequest.setTaxes(0);
        voucherTransactionRequest.setIdTransaction(transactionType + " - " + description);


        LoyaltyApi.transactVouchers(voucherTransactionRequest, new Callback<VoucherTransactionResponse>() {
            @Override
            public void onResponse(Call<VoucherTransactionResponse> call, Response<VoucherTransactionResponse> response) {
                Log.d("SUCCESS UPDATE POINTS", response.toString());
                if (cardType != null && cardType.equals(CARD_TYPE_DIGITAL)) {
                    setPoints(idMember, BONUS_CARD_TYPE_DIGITAL_VALUE, null,"B2", "Bonus Cartao Digital");
                }
                mainActivity.populateUserPoints();
                mainActivity.populateUserLevel();
            }

            @Override
            public void onFailure(Call<VoucherTransactionResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("FAILED UPDATE POINTS", t.toString());
            }
        });
    }

    public void updateProfile(Profile profile) {
        LoyaltyApi.updateProfile(profile, new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == Constants.SUCCESSFUL) {
                    Log.e("SUCCESS UPDATE PROFILE", response.toString());
                } else {
                    Log.e("FAILED UPDATE PROFILE", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("FAILED UPDATE PROFILE", t.toString());
            }
        });

    }
}
