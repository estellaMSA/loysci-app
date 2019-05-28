package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityActivitiesBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeQuestion;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.ChallengesAdapter;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;

import static br.com.monitoratec.loysci_android.model.Challenge.TYPE_SURVEY;
import static br.com.monitoratec.loysci_android.model.ChallengeQuestion.TYPE_RATING;

public class ActivitiesActivity extends AppCompatActivity implements SimpleItemClickListener {

    ActivityActivitiesBinding binding;
    ChallengesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activities);

        adapter = new ChallengesAdapter(null, this, this );
    }

    @Override
    public void onSimpleItemClick(Object object) {
        if (object instanceof Challenge) {
            if(((Challenge)object).getIndTipoMision().equals(TYPE_SURVEY)) {
                boolean isRating = false;
                for (ChallengeQuestion question : ((Challenge) object).getMisionEncuestaPreguntas()) {
                    if(question.getIndTipoPregunta().equals(TYPE_RATING)){
                        isRating = true;
                    }
                }
                if(!isRating){}
//                    startQuizz((Challenge) object);
            }else{
                Toast.makeText(this,"Coming soon", Toast.LENGTH_LONG).show();
            }
        }
    }
}
