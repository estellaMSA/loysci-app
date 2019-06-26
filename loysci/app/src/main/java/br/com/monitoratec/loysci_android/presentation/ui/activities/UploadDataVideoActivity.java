package br.com.monitoratec.loysci_android.presentation.ui.activities;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.UploadDataVideoActivityBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitAnswers;
import br.com.monitoratec.loysci_android.model.ChallengeSubmitResponse;
import br.com.monitoratec.loysci_android.model.ChallengeUploadAnswer;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.util.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDataVideoActivity extends AppCompatActivity {
    public static Challenge challenge;
    static final int REQUEST_VIDEO_CAPTURE = 444;
    private UploadDataVideoActivityBinding binding;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private String image = "";
    //private File file;

    Button button3;
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_data_video_activity);

        Button button3 = (Button) findViewById(R.id.button3);
        videoview = (VideoView) findViewById(R.id.videoview);

        this.image = getIntent().getStringExtra("Image");

        Bitmap thumbnail = getIntent().getParcelableExtra("Thumbnail");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        //ActionBar actionBar = getSupportActionBar();
        //assert actionBar != null;
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowHomeEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Toolbar includeToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(includeToolbar);

        //includeToolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        // actionBar.setIcon(getResources().getDrawable(R.drawable.com_facebook_button_send_icon_blue));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
        //videoview.setActivity(this);

        VideoView videoView = new VideoView(this);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upload_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
                // Toast.makeText(UploadDataVideoActivity.this, "Por favor espere mientras validamos el video", Toast.LENGTH_SHORT).show();
                sendWinner();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    // Here, thisActivity is the current activity

                    ///getPermissions();
                } else {
                    Toast.makeText(UploadDataVideoActivity.this, "declined", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri videoUri = data.getData();
            String filePath = videoUri.getPath();
            File file = new File(filePath);
            // try {
            //SiliCompressor.with(UploadDataVideoActivity.this).compressVideo(videoUri.toString(), videoUri.toString());
            base(videoUri);
            // } catch (URISyntaxException e) {
            //   e.printStackTrace();
            // }

        }
    }

    private void base(Uri selectedVideoUri){
        InputStream inputStream = null;
// Converting the video in to the bytes
        try
        {
            inputStream = getContentResolver().openInputStream(selectedVideoUri);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int len = 0;
        try
        {
            while ((len = inputStream.read(buffer)) != -1)
            {
                byteBuffer.write(buffer, 0, len);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = byteBuffer.size() / 1024;
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;

        if (fileSizeInMB <= 25) {
            //try {
                //Converting bytes into base64
                image = Base64.encodeToString(byteBuffer.toByteArray(), Base64.NO_WRAP);
                image = image.replaceAll("\n","");
                videoview.setVideoURI(selectedVideoUri);

                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoview);
                videoview.setMediaController(mediaController);

                videoview.start();

                Toast.makeText(UploadDataVideoActivity.this,"Pronto para enviar!",Toast.LENGTH_SHORT).show();
                Log.e("video--> ", image);
                //button3.setVisibility(View.GONE);
            //} catch (IOException e) {
            //    e.printStackTrace();
            //    Toast.makeText(UploadDataVideoActivity.this,"Favor ingrese otro video",Toast.LENGTH_SHORT).show();
            //}
        }
        else
        {
            Toast.makeText(UploadDataVideoActivity.this,"O vídeo excedeu o limite de tamanho.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void showVideoFullScreen(String link){
        //try {
            Uri video = Uri.parse(link);
            videoview.setVideoURI(video);
            //binding.videoview.start();

        //} catch (IOException e) {
        //   e.printStackTrace();
        //}
    }
    private void selectImage() {
        final CharSequence[] options = { "Gravar Vídeo", "Escolher da Galeria","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadDataVideoActivity.this);
        builder.setTitle("Vídeo");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Gravar Vídeo"))

                {
                    Intent takeVideoIntent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }

                } else if (options[item].equals("Escolher da Galeria"))

                {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 444);


                } else if (options[item].equals("Cancelar")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    private void sendWinner() {
        if (!image.equals("")) {
            Toast.makeText(UploadDataVideoActivity.this, "Enviando....", Toast.LENGTH_SHORT).show();
            ChallengeSubmitAnswers submitAnswers = new ChallengeSubmitAnswers();
            ChallengeUploadAnswer challengeAnswer = new ChallengeUploadAnswer();
            challengeAnswer.setDatosContenido(image);
            submitAnswers.setRespuestaSubirContenido(challengeAnswer);
            LoyaltyApi.submitChallenge(challenge, submitAnswers, new Callback<ChallengeSubmitResponse>() {
                @Override
                public void onResponse(Call<ChallengeSubmitResponse> call, Response<ChallengeSubmitResponse> response) {
                    if (response.isSuccessful()) {
                        finish();

                        Toast.makeText(UploadDataVideoActivity.this, response.body().getMensaje(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UploadDataVideoActivity.this, ApiUtils.getError(response).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ChallengeSubmitResponse> call, Throwable t) {
                    Toast.makeText(UploadDataVideoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(UploadDataVideoActivity.this, "Não foi possível enviar o vídeo.", Toast.LENGTH_SHORT).show();
        }

    }
}

