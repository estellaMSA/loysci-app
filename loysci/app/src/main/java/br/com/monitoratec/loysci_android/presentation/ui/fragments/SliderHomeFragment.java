package br.com.monitoratec.loysci_android.presentation.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.presentation.ui.activities.MainActivity;
import br.com.monitoratec.loysci_android.util.Prefs;

public class SliderHomeFragment extends Fragment {
    boolean mostra;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sliderhome, container, false);

        LinearLayout lnBack = (LinearLayout) view.findViewById(R.id.linerslider);
        lnBack.setBackgroundResource(R.drawable.slider01);

        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.naovisualizarslider);
        final ImageView naovisualizarfechar = (ImageView) view.findViewById(R.id.naovisualizarfechar);

        mostra = false;
        naovisualizarfechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostra)
                    Prefs.saveSlider();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_done);
                    simpleCheckedTextView.setChecked(false);
                    mostra = false;
                } else {
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check);
                    simpleCheckedTextView.setChecked(true);
                    mostra = true;
                }
            }
        });
        return view;
    }
}