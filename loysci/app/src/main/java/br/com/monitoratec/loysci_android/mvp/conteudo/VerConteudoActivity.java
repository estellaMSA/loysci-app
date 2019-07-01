package br.com.monitoratec.loysci_android.mvp.conteudo;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityVerConteudoBinding;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.presentation.ui.activities.SubirConteudoActivity;
import br.com.monitoratec.loysci_android.util.UIUtil;

import static br.com.monitoratec.loysci_android.util.Constants.MISSION_FINISHED;

public class VerConteudoActivity extends AppCompatActivity implements  VerConteudoView {


    ActivityVerConteudoBinding binding;

    VerConteudoPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ver_conteudo);
        setSupportActionBar(binding.includeToolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        presenter = new VerConteudoPresenter(this,this,getIntent());
        presenter.init();


        binding.btResgatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showConfirmSeeContent();

            }
        });



    }

    private void showConfirmSeeContent() {


        final CharSequence[] options = {"Sim", "Não"};
        AlertDialog.Builder builder = new AlertDialog.Builder(VerConteudoActivity.this);
        builder.setTitle("Resgate de Pontos");
        builder.setMessage("Você confirma a visualização do conteudo?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                presenter.onCallAPIAnswer();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });



        builder.show();

    }

    @Override
    public void setMissionAndPoints(Mission mission, int pontos) {


        binding.txtTopicPoints.setText(getString(R.string.topic_points, pontos));
        binding.txtCompleteToWin.setText(mission.getTitulo());
        binding.txtMissionTitle.setText(mission.getTitulo());
        binding.txtTopicTitle.setText(mission.getTitulo());

        if(mission.getChallenges().size() > 0)
            Glide.with(this).load(mission.getChallenges().get(0).getImagen()).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imgMission);

        Glide.with(this).load(mission.getImagem()).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imgTopic);

    }

    @Override
    public void setVideoURL(String videoStr) {

        binding.ivPlayer.setVisibility(View.GONE);
        binding.mpwVideoPlayer.setVisibility(View.VISIBLE);
        loadWebviewVIdeo(videoStr);


    }

    @Override
    public void setImgUrl(String url) {

        binding.ivPlayer.setVisibility(View.VISIBLE);
        binding.mpwVideoPlayer.setVisibility(View.GONE);
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivPlayer);
    }

    @Override
    public void setWebUrl(String url) {
        binding.ivPlayer.setVisibility(View.GONE);
        binding.mpwVideoPlayer.setVisibility(View.VISIBLE);
        loadWebview(url);
    }

    @Override
    public void showProgress(boolean show) {


        if(show){

            binding.loadingLayout.loadingText.setText(getString(R.string.sending_answer));
            binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void successSendAnswer(String string) {

        UIUtil.AlertSucesso(this, string, () -> {

            setResult(MISSION_FINISHED);
            finish();
        });

    }

    @Override
    public void errorSendAnswer(String message) {

        UIUtil.AlertErro(this, message, () -> {

        });
    }

    private void loadWebviewVIdeo(String url) {
        binding.mpwVideoPlayer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = binding.mpwVideoPlayer.getSettings();
        ws.setJavaScriptEnabled(true);
        binding.mpwVideoPlayer.loadData(url, "text/html", "utf-8");
    }

    private void loadWebview(String url) {
        binding.mpwVideoPlayer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings ws = binding.mpwVideoPlayer.getSettings();
        ws.setJavaScriptEnabled(true);
        binding.mpwVideoPlayer.loadUrl(url);
    }


}
