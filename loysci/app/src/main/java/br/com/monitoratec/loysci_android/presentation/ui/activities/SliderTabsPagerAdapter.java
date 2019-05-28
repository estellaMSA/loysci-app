package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.CartaoFragmentVerso;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SliderHomeFragment;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SliderHomeFragment02;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SliderHomeFragment03;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SliderHomeFragment04;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.SliderHomeFragment05;
import android.support.v13.app.FragmentPagerAdapter;

public class SliderTabsPagerAdapter extends FragmentPagerAdapter {

    public SliderTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new SliderHomeFragment();
            case 1:
                return new SliderHomeFragment02();
            case 2:
                return new SliderHomeFragment03();
            case 3:
                return new SliderHomeFragment04();
            case 4:
                return new SliderHomeFragment05();
            default:
                return new SliderHomeFragment();
        }

    }
    @Override
    public int getCount() {
        // get item count - quantidade de tabs
        return 5;
    }
}