package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.FragmentProgramBinding;

public class ProgramFragment extends Fragment {

    FragmentProgramBinding binding;

    public ProgramFragment() {}

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
                inflater, R.layout.fragment_program, container, false);
        return binding.getRoot();
    }

}
