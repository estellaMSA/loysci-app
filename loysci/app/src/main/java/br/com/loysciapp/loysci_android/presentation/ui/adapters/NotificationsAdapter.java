package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemNotificationBinding;
import br.com.loysciapp.loysci_android.model.Notification;
import br.com.loysciapp.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>{

    List<Notification> notifications;
    ItemNotificationBinding binding;
    Context context;
    SimpleItemClickListener simpleItemClickListener;

    public NotificationsAdapter(List<Notification> notifications, Context context, SimpleItemClickListener simpleItemClickListener) {
        this.notifications = notifications;
        this.context = context;
        this.simpleItemClickListener = simpleItemClickListener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_notification, parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final Notification notification = notifications.get(position);
        if(notification.getIndObjetivo() == null) notification.setIndObjetivo("");
        if(notification.getImagen()!=null)
            Glide.with(context).load(notification.getImagen()).into(binding.imgNotificationIcon);
        binding.txtNotificationHeader.setText(notification.getEncabezado());
        binding.txtNotificationContent.setText(notification.getTexto());
        if(notification.isVisto()){
            binding.imgNotificationSeen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_notifications_read));
            binding.imgNotificationSeen.getDrawable().setTint(ContextCompat.getColor(context, R.color.light_grey));
        }else{
            binding.imgNotificationSeen.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_notifications_black_24dp));
            binding.imgNotificationSeen.getDrawable().setTint(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        if(notifications!=null)
            return notifications.size();
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public NotificationViewHolder(View itemView) {
            super(itemView);
        }

        public NotificationViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
        }
    }
}
