package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSeeContent;
import br.com.monitoratec.loysci_android.model.ChallengeSocialNetwork;
import br.com.monitoratec.loysci_android.model.ChallengeSocialNetworkAnswer;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChallengeNetworkActivity extends AppCompatActivity {
    private CallbackManager fb_callbackManager;
    private ShareDialog fb_shareDialog;
    private TwitterLoginButton loginButton;
    private TextView toolbarText;
    private Intent intent;
    private String url;
    private String header;
    private String description;
    private String general;
    Button shareButton;
    private static String token = "";
    private static String secret ="";
    //INSTAGRAM
    private LinearLayout buttonInsta;

    private ImageView imgShare;

    LikeView likeView;
    public static Challenge challenge;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Facebook API
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        fb_callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_challenge_network);
        Bundle extras = getIntent().getExtras();
        url = extras.getString("image");
        header = extras.getString("title");
        description = extras.getString("message");
        general = extras.getString("urlGeneral");

        Bitmap thumbnail = getIntent().getParcelableExtra("Thumbnail");

        buttonInsta = (LinearLayout) this.findViewById(R.id.buttonInsta);

        loginButton = (TwitterLoginButton) this.findViewById(R.id.login_button);
        likeView = (LikeView) this.findViewById(R.id.like_view);
        likeView.setLikeViewStyle(LikeView.Style.STANDARD);
        likeView.setObjectIdAndType(
                general,
                LikeView.ObjectType.PAGE);

        shareButton = (Button) this.findViewById(R.id.button2);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectOptionsShare();
                share();
            }
        });

        imgShare = findViewById(R.id.imgShare);

        //ActionBar actionBar = getSupportActionBar();
        //assert actionBar != null;
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        toolbarText = (TextView) this.findViewById(R.id.toolbarTitle);
        toolbarText.setText(R.string.mission_activities);

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        validateType();

        //setUpTwitterButton();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        new DownloadImageTask().execute(url);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void setUpTwitterButton() {

        if(token.isEmpty() && secret.isEmpty()){
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                @Override
                public void success(Result result) {
                    TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                    TwitterAuthToken authToken = session.getAuthToken();
                    token = authToken.token;
                    secret = authToken.secret;

                    shareButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpViewsForTweetComposer();
                        }
                    });

                    setUpTwitterButton();
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(getApplicationContext(),
                            exception.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            shareButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        }

    }


    private void setUpViewsForTweetComposer() {
        TweetComposer.Builder builder = null;
        try {
            builder = new TweetComposer.Builder(this)
                    .text(description)
                    .url( new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        builder.show();
    }


    private void validateType(){
        final ChallengeSocialNetwork content = challenge.getMisionRedSocial();
        switch (challenge.getMisionRedSocial().getIndTipo()) {
            case ChallengeSocialNetwork.TYPE_LIKE:
                likeView.setVisibility(View.VISIBLE);
                likeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(content.getUrlObjectivo()));
                        startActivity(intent);
                        sendWinner();
                    }
                });
                break;
            case ChallengeSocialNetwork.TYPE_MESSAGE:
                shareButton.setVisibility(View.VISIBLE);
                break;
            case ChallengeSocialNetwork.TYPE_SHARE:
                shareButton.setVisibility(View.VISIBLE);
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectOptionsShare();
                    }
                });
                break;
            case ChallengeSocialNetwork.TYPE_IMAGE:
                shareButton.setVisibility(View.VISIBLE);
                shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectOptionsShare();
                    }
                });
            case ChallengeSocialNetwork.TYPE_TWITER_SHARE:
                setUpTwitterButton();
                break;
            case ChallengeSocialNetwork.TYPE_INSTAGRAM_LIKE:
                buttonInsta.setVisibility(View.VISIBLE);
                buttonInsta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(content.getUrlObjectivo()));
                        startActivity(intent);
                        sendWinner();
                    }
                });
                break;
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        shareImage(thumbnail);
    }

    private void onSelectGallery(Intent data){
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(
                selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
        if (yourSelectedImage != null) {
            shareImage(yourSelectedImage);
            Toast.makeText(ChallengeNetworkActivity.this, "Listo", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ChallengeNetworkActivity.this, "Por favor intente luego", Toast.LENGTH_LONG).show();
        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fb_callbackManager.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == 2) {
                onSelectGallery(data);
            }
            else if (requestCode == 3) {
                onCaptureImageResult(data);
            }
        }
    }

    private void selectOptionsShare(){
        final CharSequence[] options = {"Compartilhar conteúdo", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeNetworkActivity.this);
        builder.setTitle("Share options!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Compartilhar conteúdo"))

                {
                    share();

                } else if (options[item].equals("Compartilhar conteúdo com uma imagem"))

                {
                    selectImage();

                } else if (options[item].equals("Cancelar")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();
    }
    private void selectImage() {
        final CharSequence[] options = { "Tirar uma foto", "Escolher da Galeria","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ChallengeNetworkActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Tirar uma foto"))

                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 3);

                } else if (options[item].equals("Escolher da Galeria"))

                {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancelar")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    private void share() {
        ShareDialog shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.registerCallback(fb_callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    //Toast.makeText(ChallengeNetworkActivity.this, "Share Success", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE SUCCESS");
                    Toast.makeText(ChallengeNetworkActivity.this, R.string.share_success, Toast.LENGTH_SHORT).show();
                    sendWinner();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(ChallengeNetworkActivity.this, "Share Cancelled", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE CACELLED");
                    Toast.makeText(ChallengeNetworkActivity.this, R.string.share_cancel, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(ChallengeNetworkActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("DEBUG", "Share: " + exception.getMessage());
                    exception.printStackTrace();
                }
            });
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentTitle(header)
                    .setContentDescription(description)
                    .setContentUrl(Uri.parse(general))
                    //.setImageUrl(Uri.parse(url))
                    .build();

            shareDialog.show(content);


        }
    }



    private void shareImage(Bitmap bitmap) {
        ShareDialog shareDialog = new ShareDialog(this);
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.registerCallback(fb_callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    //Toast.makeText(ChallengeNetworkActivity.this, "Share Success", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE SUCCESS");
                    Toast.makeText(ChallengeNetworkActivity.this, R.string.share_success, Toast.LENGTH_SHORT).show();
                    sendWinner();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(ChallengeNetworkActivity.this, "Share Cancelled", Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "SHARE CACELLED");
                    Toast.makeText(ChallengeNetworkActivity.this, R.string.share_cancel, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException exception) {
                    Toast.makeText(ChallengeNetworkActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("DEBUG", "Share: " + exception.getMessage());
                    exception.printStackTrace();
                }
            });
            SharePhoto sharePhoto1 = new SharePhoto.Builder()
                    .setBitmap(bitmap)
                    .build();
            ShareContent shareContent = new ShareMediaContent.Builder()
                    .addMedium(sharePhoto1)
                    .build();
            shareDialog.show(shareContent);
        }
        else
        {
            Log.e("Problem", "here");
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendWinner(){
        ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
        submitAnswers.setRespuestaRedSocial(new ChallengeSocialNetworkAnswer());

        LoyaltyApi.submitChallenge(challenge, submitAnswers, new Callback<ChallengeSubmitResponse>() {
            @Override
            public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChallengeNetworkActivity.this, R.string.challenge_submitted_successfully, Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Toast.makeText(ChallengeNetworkActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {
                Toast.makeText(ChallengeNetworkActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //bmImage.setImageBitmap(result);
            Drawable d = new BitmapDrawable(getResources(), result);

            imgShare.setImageDrawable(d);
        }
    }
}
