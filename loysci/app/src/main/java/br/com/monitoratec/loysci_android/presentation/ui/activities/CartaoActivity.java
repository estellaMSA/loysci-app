package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityCartaoBinding;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.MainViewModel;

public class CartaoActivity extends AppCompatActivity implements ActionBar.TabListener {

    ActivityCartaoBinding binding;
    MainViewModel model;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            binding = DataBindingUtil.setContentView(this, R.layout.activity_cartao);
            setSupportActionBar(binding.includeToolbar.toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
            binding.includeToolbar.toolbarTitle.setText(R.string.cartaofidelidade);

            CartaoTabsPagerAdapter mAdapter = new CartaoTabsPagerAdapter(getFragmentManager());
            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
           //viewPager.setAdapter(mAdapter);
            ActionBar actionBar = getActionBar();
            String[] tabs = {"Frente", "Verso"};
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
// Adicione aqui as tabs:
            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));

            }

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    // on changing the page
                    // make respected tab selected
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
