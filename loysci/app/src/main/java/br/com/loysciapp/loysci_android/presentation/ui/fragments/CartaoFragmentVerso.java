package br.com.loysciapp.loysci_android.presentation.ui.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.model.Level;
import br.com.loysciapp.loysci_android.model.Progress;
import br.com.loysciapp.loysci_android.util.Prefs;


public class CartaoFragmentVerso extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cartao, container, false);

        TextView txtNome = (TextView) view.findViewById(R.id.txtcartaonome);
        txtNome.setVisibility(View.GONE);
        setupProgress();

        return view;
    }

    private void setupProgress() {
        try {
            String jsonProgress = Prefs.getProgress();
            if (jsonProgress != null) {
                Progress savedProgress = new Gson().fromJson(jsonProgress, Progress.class);

                LinearLayout linercartao = (LinearLayout) view.findViewById(R.id.linercartao);

                List<Level> levels = new ArrayList<>(savedProgress.getNiveles());
                for (int i = 0; i < levels.size(); i++) {
                    Level level = levels.get(i);
                    if (savedProgress.getNivelActual() != null && savedProgress.getNivelActual().getIdNivel().equals(level.getIdNivel())) {
                        if (level.getNombre().equals("Onix")) {
                            linercartao.setBackgroundResource(R.drawable.cartaoonixverso);
                        } else if (level.getNombre().equals("Diamante")) {
                            linercartao.setBackgroundResource(R.drawable.cartaodiamanteverso);
                        } else {
                            linercartao.setBackgroundResource(R.drawable.cartaocitricoverso);
                        }
                    }
                }
            }
        }
        catch (Exception ex){

        }
    }
}

