package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityBadgesBinding;
import br.com.monitoratec.loysci_android.model.Badge;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.BadgesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BadgesActivity extends AppCompatActivity {


    private static final String TAG = "BADGESACTIVITY";
    ActivityBadgesBinding binding;
    private BadgesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_badges);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.badges);

        fetchUserBadges();

    }

    private void fetchUserBadges() {


        setLoadingVisible(true);


        LoyaltyApi.getBadges(new Callback<List<Badge>>() {
            @Override
            public void onResponse(Call<List<Badge>> call, Response<List<Badge>> response) {


                setLoadingVisible(false);

                adapter = new BadgesAdapter(BadgesActivity.this,response.body());
                binding.rvBadges.setLayoutManager(new GridLayoutManager(BadgesActivity.this,3));
                binding.rvBadges.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<Badge>> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(getApplicationContext(), getString(R.string.login_errorlogin), Toast.LENGTH_SHORT).show();

                setLoadingVisible(false);

            }
        });

    }

    private void setLoadingVisible(boolean isVisible) {

        binding.loading.loadingText.setText("Buscando suas badges!");

        if (isVisible) {
            binding.loading.loadingLayout.setVisibility(View.VISIBLE);
        } else {

            binding.loading.loadingLayout.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
