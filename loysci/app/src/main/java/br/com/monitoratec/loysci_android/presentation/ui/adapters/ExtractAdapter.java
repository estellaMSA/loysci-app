package br.com.monitoratec.loysci_android.presentation.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ItemExtractBinding;
import br.com.monitoratec.loysci_android.model.History;
import br.com.monitoratec.loysci_android.util.Constants;

import static br.com.monitoratec.loysci_android.model.History.TRANSACCION;

/**
 * Created by Felipe Galeote on 29,Outubro,2018
 */
public class ExtractAdapter extends RecyclerView.Adapter<ExtractAdapter.ViewHolder> {

    private List<History> extracts = new ArrayList<>();
    private DateFormat dateFormat;
    private Context context;
    private int days;

    public ExtractAdapter(Context context) {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.context = context;
    }

    @NonNull
    @Override
    public ExtractAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExtractBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_extract, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtractAdapter.ViewHolder holder, int position) {

        History extract = extracts.get(position);
        Date date = new Date(extract.getDate());
        String transactionId = extract.getIdTransaction();


        String[] title = extract.getIdTransaction().replace("–","-").split("-");
        holder.binding.extractTitle.setText(title[title.length-1].trim());

        if(extract.getIdTransaction().toLowerCase().contains("netshoes")){
            holder.binding.extractType.setText("Netshoes");
        } else {
            holder.binding.extractType.setText("LoySci");
        }

        if ((transactionId.contains("R1") || transactionId.contains("R2")) || transactionId.contains("R3")
                || transactionId.contains("A1") || transactionId.contains("A2") || transactionId.contains("E1")
                || transactionId.contains("E2") || transactionId.contains("E3") || transactionId.contains("B1")
                || transactionId.contains("B2") || transactionId.contains("B3") || transactionId.contains("M1") || transactionId.contains("M2")
                || extract.getTransactionDesc() != null && !extract.getTransactionDesc().isEmpty()) {
            holder.binding.extractCode.setText(extract.getTransactionDesc());
            holder.binding.divider.setVisibility(View.VISIBLE);
            holder.binding.extractCode.setVisibility(View.VISIBLE);
            holder.binding.extractItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClipboard(context, holder.binding.extractCode.getText().toString());
                }
            });
        }
        else {
            holder.binding.divider.setVisibility(View.GONE);
            holder.binding.extractCode.setVisibility(View.GONE);
        }

        if (extract.getIdTransaction().contains(TRANSACCION)) {
            String availableDate = String.format("%s %s %s %s",
                    context.getString(R.string.available_from), dateFormat.format(date),
                    context.getString(R.string.to), dateFormat.format(new Date(extract.getDate()+ Constants.SIX_MONTHS)));
            holder.binding.extractAvailableDate.setText(availableDate);
        } else {
            String availableDate = String.format("%s %s",
                    context.getString(R.string.available_since), dateFormat.format(date));
            holder.binding.extractAvailableDate.setText(availableDate);
        }

        if (transactionId.contains("R1") || transactionId.contains("R2") || transactionId.contains("E1")) {
            holder.binding.extractPoints.setTextColor(context.getResources().getColor(R.color.md_red_600));
            holder.binding.extractPoints.setText(String.format("-%s", String.valueOf(Math.round(extract.getMetricEntry().getAmount()))));
        } else {
            holder.binding.extractPoints.setTextColor(context.getResources().getColor(R.color.positive_points));
            holder.binding.extractPoints.setText(String.format("+%s", String.valueOf(Math.round(extract.getMetricEntry().getAmount()))));
        }

    }

    private void setClipboard(Context context, String text) {

        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        if(clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Código copiado com sucesso!", Toast.LENGTH_SHORT).show();
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
