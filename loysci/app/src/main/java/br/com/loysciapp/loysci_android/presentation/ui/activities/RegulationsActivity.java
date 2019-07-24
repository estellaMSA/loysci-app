package br.com.loysciapp.loysci_android.presentation.ui.activities;


import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityRegulationsBinding;

public class RegulationsActivity extends AppCompatActivity {

    ActivityRegulationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regulations);
        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(R.string.terms_conditions);

        //String decoded = new String(R.string.terms_and_conditions.getBytes("ISO-8859-1"));
        binding.txtRegulationDescription.setText(getTermsString());

    }

    private String getTermsString() {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("termos.txt")));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str + "\n");
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
