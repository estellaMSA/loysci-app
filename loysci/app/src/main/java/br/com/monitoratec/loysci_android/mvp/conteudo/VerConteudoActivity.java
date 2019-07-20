package br.com.monitoratec.loysci_android.mvp.conteudo;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

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
    private String urlToSee;
    private boolean openUrl;
    private String msgSucess;


    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    private myWebChromeClient mWebChromeClient;
    private myWebViewClient mWebViewClient;


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


        binding.btResgatar.setOnClickListener(onclick());
        binding.btResgatarFake.setOnClickListener(onclick());


        mWebViewClient = new myWebViewClient();
        binding.mpwVideoPlayer.setWebViewClient(mWebViewClient);

        mWebChromeClient = new myWebChromeClient();
        binding.mpwVideoPlayer.setWebChromeClient(mWebChromeClient);
        binding.mpwVideoPlayer.getSettings().setJavaScriptEnabled(true);
        binding.mpwVideoPlayer.getSettings().setAppCacheEnabled(true);
        binding.mpwVideoPlayer.getSettings().setSaveFormData(true);

        customViewContainer = binding.customViewContainer;

        binding.textDesc.setVisibility(View.INVISIBLE);

    }


    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }


    @Override
    protected void onStop() {
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
        if (inCustomView()) {
            hideCustomView();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (inCustomView()) {
                hideCustomView();
                return true;
            }

            if ((mCustomView == null) && binding.mpwVideoPlayer.canGoBack()) {
                binding.mpwVideoPlayer.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private View.OnClickListener onclick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showConfirmSeeContent();

            }
        };
    }

    private void showConfirmSeeContent() {


//        final CharSequence[] options = {"Sim", "Não"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(VerConteudoActivity.this);
//        builder.setTitle("Resgate de Pontos");
//        builder.setMessage("Você confirma a visualização do conteudo?");
//
//        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.dismiss();
                presenter.onCallAPIAnswer();
//            }
//        });
//
//        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                dialogInterface.dismiss();
//            }
//        });
//
//
//
//        builder.show();

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
        binding.btResgatar.setEnabled(false);
        binding.btResgatar.setAlpha(0.5f);

        binding.txtMissionTitle.setText("Assista o vídeo até o final para ganhar pontos!");

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
        binding.mpwVideoPlayer.setVisibility(View.GONE);
        binding.btResgatarFake.setVisibility(View.VISIBLE);
        binding.btResgatar.setVisibility(View.GONE);
        urlToSee = url;
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

        msgSucess = string;

        if(urlToSee != null) {


            openUrl = true;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToSee));
            startActivity(browserIntent);


        }
        else{


            UIUtil.AlertSucesso(this, string, () -> {



                    setResult(MISSION_FINISHED);
                    finish();



            });

        }

    }

    @Override
    public void errorSendAnswer(String message) {

        UIUtil.AlertErro(this, message, () -> {

        });
    }

    @Override
    public void setTime(int duration) {

        Handler handler = new Handler();

        Runnable r = () -> {

            VerConteudoActivity.this.runOnUiThread(() -> {

                binding.btResgatar.setEnabled(true);
                binding.btResgatar.setAlpha(1.0f);
            });
        };

        handler.postDelayed(r,(duration * 1000));
    }

    private void loadWebviewVIdeo(String url) {

        binding.mpwVideoPlayer.loadUrl(url);
        binding.textDesc.setVisibility(View.VISIBLE);
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


    @Override
    protected void onResume() {
        super.onResume();

        if(urlToSee != null &&  openUrl){

            UIUtil.AlertSucesso(this, msgSucess, () -> {

                setResult(MISSION_FINISHED);
                finish();
            });
        }

    }


    class myWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;
        private View mVideoProgressView;

        @Override
        public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
            onShowCustomView(view, callback);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onShowCustomView(View view,CustomViewCallback callback) {

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            binding.mpwVideoPlayer.setVisibility(View.GONE);
            customViewContainer.setVisibility(View.VISIBLE);
            customViewContainer.addView(view);
            customViewCallback = callback;
        }

        @Override
        public View getVideoLoadingProgressView() {

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(VerConteudoActivity.this);
                mVideoProgressView = inflater.inflate(R.layout.video_progress, null);
            }
            return mVideoProgressView;
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();    //To change body of overridden methods use File | Settings | File Templates.
            if (mCustomView == null)
                return;

            hideCustomview();
        }
    }

    private void hideCustomview() {
        binding.mpwVideoPlayer.setVisibility(View.VISIBLE);
        customViewContainer.setVisibility(View.GONE);

        // Hide the custom view.
        mCustomView.setVisibility(View.GONE);

        // Remove the custom view from its container.
        customViewContainer.removeView(mCustomView);
        customViewCallback.onCustomViewHidden();

        mCustomView = null;
    }

    class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
