package br.com.loysciapp.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ItemExtractBinding;
import br.com.loysciapp.loysci_android.model.History;
import br.com.loysciapp.loysci_android.util.Constants;


/**
 * Created by Felipe Galeote on 29,Outubro,2018
 */
public class ExpiredVouchersAdapter extends RecyclerView.Adapter<ExpiredVouchersAdapter.ViewHolder> {
    private List<History> extracts = new ArrayList<>();
    private DateFormat dateFormat;
    private Context context;

    public ExpiredVouchersAdapter(Context context) {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.context = context;
    }

    @NonNull
    @Override
    public ExpiredVouchersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExtractBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_extract, parent, false);
        return new ExpiredVouchersAdapter.ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ExpiredVouchersAdapter.ViewHolder holder, int position) {

        History extract = extracts.get(position);
        String transactionId = extract.getIdTransaction();

        String[] title = extract.getIdTransaction().replace("–","-").split("-");
        holder.binding.extractTitle.setText(title[title.length-1].trim());

        if(extract.getIdTransaction().toLowerCase().contains("netshoes")){
            holder.binding.extractType.setText("Netshoes");
        } else {
            holder.binding.extractType.setText("Loysci");
        }


        String availableDate = String.format("%s %s", context.getString(R.string.expired_on), dateFormat.format(new Date(extract.getDate() + Constants.SIX_MONTHS)));
        holder.binding.extractAvailableDate.setText(availableDate);
        holder.binding.extractAvailableDate.setTextColor(context.getResources().getColor(R.color.md_red_600));

        if (transactionId.contains("R1") || transactionId.contains("R2") || transactionId.contains("E1")) {
            holder.binding.extractPoints.setTextColor(context.getResources().getColor(R.color.md_red_600));
            holder.binding.extractPoints.setText(String.format("-%s", String.valueOf(Math.round(extract.getMetricEntry().getAmount()))));
        } else {
            holder.binding.extractPoints.setTextColor(context.getResources().getColor(R.color.positive_points));
            holder.binding.extractPoints.setText(String.format("+%s", String.valueOf(Math.round(extract.getMetricEntry().getAmount()))));
        }
    }

    @Override
    public int getItemCount() {
        return extracts.size();
    }


    public void populateExtracts(List<History> extracts) {
        this.extracts = extracts;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemExtractBinding binding;

        ViewHolder(ItemExtractBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
