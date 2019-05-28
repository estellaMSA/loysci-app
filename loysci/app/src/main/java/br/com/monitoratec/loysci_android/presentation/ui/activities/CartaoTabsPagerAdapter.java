package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


import br.com.monitoratec.loysci_android.presentation.ui.fragments.CartaoFragment;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.CartaoFragmentVerso;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.HomeFragment;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SalesFragment;

public class CartaoTabsPagerAdapter extends FragmentPagerAdapter {

    public CartaoTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Seu primeiro fragment
                return new CartaoFragment();
            case 1:
                // Seu segundo fragment
                return new CartaoFragmentVerso();
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        // get item count - quantidade de tabs
        return 2;
    }
}
