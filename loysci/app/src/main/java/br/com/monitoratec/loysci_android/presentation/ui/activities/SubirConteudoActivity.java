package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivitySubirconteudoBinding;
import br.com.monitoratec.loysci_android.model.Challenge;
import br.com.monitoratec.loysci_android.model.ChallengeUploadContent;
import br.com.monitoratec.loysci_android.model.Mission;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.SimpleItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.monitoratec.loysci_android.util.Constants.MISSION_PARCELABLE;


public class SubirConteudoActivity extends AppCompatActivity implements SimpleItemClickListener {

    String content = "code";
    //private Button btnSend;
    //private TextView subencabezado;
    //private TextView puntos;
    private ImageView imageView;
    public static Challenge challenge;

    Bitmap thumbnail;
    private String image = "";
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 4;

    ActivitySubirconteudoBinding binding;
    Mission mission;

    int missionIndex;
    String idMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_subirconteudo);

        mission = getIntent().getParcelableExtra(MISSION_PARCELABLE);

        List<Challenge> challenges = mission.getChallenges();
        challenge = challenges.get(0);

        setSupportActionBar(binding.includeToolbar.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);

        setTitle(challenge.getEncabezadoArte());

        imageView = (ImageView) this.findViewById(R.id.ivImage);

        //btnSend = (Button) this.findViewById(R.id.btnChallenge);
        //subencabezado = (TextView) this.findViewById(R.id.tvDescription);
        //puntos = (TextView) this.findViewById(R.id.puntosPromo);
        //subencabezado.setText(challenge.getDescripcion());

        binding.puntosPromo.setText(String.valueOf(challenge.getValor()) + " " + challenge.getMetrica().getNombre());
        binding.includeToolbar.toolbarTitle.setText(challenge.getEncabezadoArte());
        binding.tvDescription.setText(challenge.getDescripcion());

        //if(mission.getChallenges().size() > 0)
            //Glide.with(this).load(mission.getChallenges().get(0).getImagen()).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.imgMission);

        Glide.with(this)
                .load(mission.getImagem())
                .crossFade()
                .into(imageView);


        binding.ivImage.setVisibility(View.VISIBLE);

        if(mission.getImagem()!=null)
            Glide.with(this)
                    .load(mission.getImagem())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(binding.ivImage);

        if (challenge.getIndTipoMision() != null) {
            if (challenge.getIndTipoMision().equals(Challenge.TYPE_UPLOAD_CONTENT) && challenge.getMisionSubirContenido().getIndTipo().equals(ChallengeUploadContent.TYPE_IMAGE)) {
                binding.btnChallenge.setText("Carregar Imagem");

                binding.btnChallenge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });
            } else if (challenge.getIndTipoMision().equals(Challenge.TYPE_UPLOAD_CONTENT) && challenge.getMisionSubirContenido().getIndTipo().equals(ChallengeUploadContent.TYPE_VIDEO)) {
                binding.btnChallenge.setText("Carregar Vídeo");

                binding.btnChallenge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectVideo();
                    }
                });
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        super.onCreate(savedInstanceState);
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        getBase64(thumbnail);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            finish();
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
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
                    //thumbnail = yourSelectedImage;
                    getBase64(yourSelectedImage);

                    uploadContentType();
                } else {
                    Toast.makeText(SubirConteudoActivity.this, "Por favor tente outra imagem", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SubirConteudoActivity.this, "Ocorreu algum erro com a imagem", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                onCaptureImageResult(data);

                uploadContentType();
            }
        } else if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                uploadContentType();
            }
        }
    }


    private void selectImage() {
        final CharSequence[] options = {"Tirar Foto", "Cancelar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SubirConteudoActivity.this);
        builder.setTitle("CAPTURAR FOTO");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Tirar Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (options[item].equals("Galeria de Fotos")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 3);
                } else if (options[item].equals("Cancelar")) {

                    dialog.dismiss();
                }
            }

        });

        builder.show();
    }
    private void selectVideo() {
        final CharSequence[] options = { "Gravar Vídeo","Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SubirConteudoActivity.this);
        builder.setTitle("GRAVAR VÍDEO");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Gravar Vídeo"))

                {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
                    }

                } else if (options[item].equals("Galeria"))

                {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 4);


                } else if (options[item].equals("Cancelar")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void uploadContentType() {
        Intent intent = null;
        switch (challenge.getMisionSubirContenido().getIndTipo()) {
            case ChallengeUploadContent.TYPE_IMAGE:
                intent = new Intent(SubirConteudoActivity.this, UploadDataActivity.class);
                UploadDataActivity.challenge = challenge;

                intent.putExtra("Image", image);
                if (thumbnail != null) {
                    intent.putExtra("Thumbnail", thumbnail);
                }
                setRegistrarVistaMision();
                break;
            case ChallengeUploadContent.TYPE_VIDEO:
                intent = new Intent(SubirConteudoActivity.this, UploadDataVideoActivity.class);
                UploadDataVideoActivity.challenge = challenge;
                setRegistrarVistaMision();
                break;
        }
        if (intent != null) {
            intent.putExtra("challenge-id", challenge.getIdMision());
            startActivityForResult(intent, 2);
        }
    }


    //Se registra como vista la mision actual.
    private void setRegistrarVistaMision() {
        LoyaltyApi.setRegistrarVistaMision(challenge.getIdMision(), new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Toast.makeText(ChallengeDetailActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.println(Log.ERROR, "RegistrarVistaMision", t.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    // Here, thisActivity is the current activity

                    //getPermissions();
                } else {
                    Toast.makeText(SubirConteudoActivity.this, "Negado", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void getBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        imageBase64 = imageBase64.replaceAll("\n", "");
        this.image = imageBase64;
    }

    @Override
    public void onSimpleItemClick(Object object) {

    }
}
