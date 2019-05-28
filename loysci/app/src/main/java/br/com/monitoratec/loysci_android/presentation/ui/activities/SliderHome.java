package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.Level;
import br.com.monitoratec.loysci_android.model.Points;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.Progress;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.HomeFragment;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.MainViewModel;
import br.com.monitoratec.loysci_android.util.Prefs;

public class SliderHome extends AppCompatActivity implements ActionBar.TabListener {

    MainViewModel model;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_slider);

            SliderTabsPagerAdapter mAdapter = new SliderTabsPagerAdapter(getFragmentManager());
            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            viewPager.setAdapter(mAdapter);
            ActionBar actionBar = getActionBar();
            String[] tabs = {"1/5", "2/5", "3/5", "4/5", "5/5"};
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));

            }

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    actionBar.setSelectedNavigationItem(position);

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });

        }
        catch (Exception ex){


        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
