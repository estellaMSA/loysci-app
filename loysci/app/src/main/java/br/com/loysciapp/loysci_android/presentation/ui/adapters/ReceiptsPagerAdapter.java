package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.ExpiredFragment;
import br.com.loysciapp.loysci_android.presentation.ui.fragments.ExtractFragment;

import static br.com.loysciapp.loysci_android.util.Constants.NUMBER_OF_RECEIPTS_TABS;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_EXPIRED;
import static br.com.loysciapp.loysci_android.util.Constants.TAB_EXTRACT;

/**
 * Created by Felipe Galeote on 25,Outubro,2018
 */
public class ReceiptsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private Fragment mCurrentFragment;

    public ReceiptsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case TAB_EXTRACT:
                return new ExtractFragment();
            case TAB_EXPIRED:
                return new ExpiredFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_RECEIPTS_TABS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case TAB_EXTRACT:
                return mContext.getString(R.string.extract);
            case TAB_EXPIRED:
                return mContext.getString(R.string.expired);
            default:
                return "";
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }
}
