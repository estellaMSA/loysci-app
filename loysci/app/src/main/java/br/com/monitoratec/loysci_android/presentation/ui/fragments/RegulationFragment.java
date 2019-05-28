package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentRegulationBinding;

public class RegulationFragment extends Fragment {

    FragmentRegulationBinding binding;

    public RegulationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_regulation, container, false);

        //binding.txt_regulation_description.setText(getTermsString());
        return binding.getRoot();
    }

    /*private String getTermsString() {
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
    }*/



}
