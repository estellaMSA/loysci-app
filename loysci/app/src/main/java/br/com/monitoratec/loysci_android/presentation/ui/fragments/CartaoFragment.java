package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.Level;
import br.com.monitoratec.loysci_android.model.Points;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.Progress;
import br.com.monitoratec.loysci_android.util.Prefs;


public class CartaoFragment extends Fragment {
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cartao, container, false);

        TextView txtNome = (TextView) view.findViewById(R.id.txtcartaonome);
        //TextView txtPontos = binding.txtcartaopontos;


        RotateAnimation rotate= (RotateAnimation)AnimationUtils.loadAnimation(view.getContext(), R.drawable.rotate);
        txtNome.setAnimation(rotate);
        //txtPontos.setAnimation(rotate);

        String jsonProfile = Prefs.getProfile();
        String nomeCartao = "";
        if (jsonProfile != null) {
            Profile savedProfile = new Gson().fromJson(jsonProfile, Profile.class);
            nomeCartao = savedProfile.getNombre() + " " + savedProfile.getApellido();
        }

        String jsonPoints = Prefs.getPoints();
        if (jsonPoints != null) {
            Points savedPoints = new Gson().fromJson(jsonPoints, Points.class);
            txtNome.setText(nomeCartao + "\n" + String.valueOf(Math.round(savedPoints.getTotalAcumulado()) + " Pontos"));
        }
        else
            txtNome.setText(nomeCartao);

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
                            linercartao.setBackgroundResource(R.drawable.cartaoonixfrente);
                        } else if (level.getNombre().equals("Diamante")) {
                            linercartao.setBackgroundResource(R.drawable.cartaodiamantefrente);
                        } else {
                            linercartao.setBackgroundResource(R.drawable.cartaocitricofrente);
                        }
                    }
                }
            }
        }
        catch (Exception ex){

        }
    }
}
