package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.loysciapp.loysci_android.presentation.ui.fragments.GamesFragment;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.HomeFragment;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.ReceiptsFragment;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.SalesFragment;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.VouchersFragment;

import static br.com.loysciapp.loysci_android.util.Constants.NUMBER_OF_MAIN_TABS;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_GAMES;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_HOME;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_RECEIPTS;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_SALES;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_VOUCHERS;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_HOME:
                return new HomeFragment();
            case TAB_RECEIPTS:
                return new ReceiptsFragment();
            case TAB_SALES:
                return new SalesFragment();
            case TAB_GAMES:
                return new GamesFragment();
            case TAB_VOUCHERS:
                return new VouchersFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return NUMBER_OF_MAIN_TABS;
    }
}