package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityNotificationsBinding;
import br.com.monitoratec.loysci_android.model.Notification;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.NotificationsAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

import static br.com.monitoratec.loysci_android.util.Constants.NOTIFICATIONS_PARCELABLE;

public class NotificationsActivity extends AppCompatActivity implements SimpleItemClickListener {

    private ActivityNotificationsBinding binding;
    public List<Notification> notifications;
    public NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications);
        notifications = getIntent().getParcelableArrayListExtra(NOTIFICATIONS_PARCELABLE);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.latest_notifications));

        adapter = new NotificationsAdapter(notifications, this, this);



        binding.recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerNotifications.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSimpleItemClick(Object object) {
        //Era pra fazer alguma coisa mas por enquanto não
    }
}
