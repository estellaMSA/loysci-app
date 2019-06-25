package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityProfileBinding;
import br.com.monitoratec.loysci_android.model.CepModel;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.networkUtils.LoyaltyApi;
import br.com.monitoratec.loysci_android.presentation.presenter.EditAddressPresenter;
import br.com.monitoratec.loysci_android.presentation.presenter.ProfileActivityPresenter;
import br.com.monitoratec.loysci_android.util.AddressUtil;
import br.com.monitoratec.loysci_android.util.Constants;
import br.com.monitoratec.loysci_android.util.CustomPhotoPickerDialog;
import br.com.monitoratec.loysci_android.util.MaskWatcher;
import br.com.monitoratec.loysci_android.util.PhoneNumberFormatter;
import br.com.monitoratec.loysci_android.util.Utilities;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.aprilapps.easyphotopicker.Constants.RequestCodes.PICK_PICTURE_FROM_GALLERY;
import static pl.aprilapps.easyphotopicker.Constants.RequestCodes.TAKE_PICTURE;

@RuntimePermissions
public class ProfileActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 9068;
    private static final int GALLERY_REQUEST = 2888;
    private static final int AVATAR_REQUEST = 3888;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1;


    private ActivityProfileBinding binding;
    private CustomPhotoPickerDialog photoDialog;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    private ProfileActivityPresenter presenter;
    private EditAddressPresenter presenterAddress;
    private ArrayAdapter<String> spinnerArrayAdapter;

    private Profile profile;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener dateStart;

    private String profileBase64 = "";
    Bitmap profileBitmap;

    //private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        presenter = new ProfileActivityPresenter(this, binding);
        presenterAddress = new EditAddressPresenter(this, binding);

        ///Ainda falta colocar o metodo para carrega o Spiner Endereço
        ///Ainda falta colocar o metodo para salvar os dados do Endereço
        //Importar esses itens de EditAddressActivity

        //presenterAddress = new EditAddressPresenter(this, binding);

        spinnerArrayAdapter = presenter.getSpinnerInstance();
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(spinnerArrayAdapter);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            profile = bundle.getParcelable(Constants.PROFILE_PARCELABLE);
            if (profile != null) {
                presenter.setProfile(profile);
                binding.setUser(profile);
                populateView(profile);

                populateViewsWithUserProfile();
            }

        }

        getProfile();
        //binding.tietPhone.setFilters(Utilities.getCellphoneInputFilter());

        //Enderço
        configStateSpinner();
        binding.zipcode.getEditText().setFilters(Utilities.getCepInputFilter());
        binding.zipcode.getEditText().addTextChangedListener(MaskWatcher.buildZipcode());

        TextView txtEdit = binding.txtCep;
        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    getCarregaCep(txtEdit.getText().toString().replace("-", ""));
                }
            }
        });

        onClick();
        //binding.state.setSelection(AddressUtil.getArrayPositionByInitials(profile.getEstadoResidencia(), this));
        if (profile.getEstadoResidencia() != null) {
            binding.state.setSelection(AddressUtil.getArrayPositionByInitials(profile.getEstadoResidencia(), this));
            profile.defineState(profile.getEstadoResidencia());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void getCarregaCep(String cep) {
        LoyaltyApi.getCEP(cep, new Callback<CepModel>() {
            @Override
            public void onResponse(Call<CepModel> call, Response<CepModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    CepModel cep = response.body();

                    if (cep.getLogradouro().contains(",")) {
                        String[] rua = cep.getLogradouro().split(",");
                        binding.txtendereco.setText(rua[0]);
                    } else
                        binding.txtendereco.setText(cep.getLogradouro());
                    binding.txtcidade.setText(cep.getCidade());
                    binding.txtbairro.setText(cep.getBairro());

                    if (cep.getUf() != null) {
                        binding.state.setSelection(AddressUtil.getArrayPositionByInitials(cep.getUf(), ProfileActivity.this));
                        profile.defineState(cep.getUf());
                    }
                    profile.defineState(cep.getUf());
                   /* SimpleCursorAdapter adapter = (SimpleCursorAdapter) binding.state.getAdapter();
                    for (int position = 0; position < adapter.getCount(); position++) {
                        if(adapter.getItem(position) == cep.getUf()) {
                            binding.state.setSelection(position);
                            return;
                        }
                    }*/
                }
            }

            @Override
            public void onFailure(Call<CepModel> call, Throwable t) {
                String teste = "sdjkljf";
                //loadListener.onLoadExtractFailed();
            }
        });
    }

    //Endereço
    public Profile populateUserProfileWithInputs(Profile profile) {
        profile.defineZipcode(binding.zipcode.getEditText().getText().toString().replace("-", ""));
        profile.defineThoroughfare(binding.thoroughfare.getEditText().getText().toString());
        profile.defineNumber(binding.number.getEditText().getText().toString());
        profile.defineComplement(binding.complement.getEditText().getText().toString());
        profile.defineNeighborhood(binding.neighborhood.getEditText().getText().toString());
        profile.defineCity(binding.city.getEditText().getText().toString());
        String state = AddressUtil.convertFullStateNameToInitials(binding.state.getSelectedItem().toString(), this);
        profile.defineState(state);
        profile.setEstadoResidencia(state);
        return profile;
    }

    private void configStateSpinner() {

        //binding.state.setOnItemSelectedListener(ProfileActivity.this);

        ArrayAdapter<String> spinnerArrayAdapter;
        spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.states)) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(0, 0, 0, 0);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(R.color.inactive_text));
                } else {
                    tv.setTextColor(getResources().getColor(R.color.primary_text));
                }
                return view;
            }

        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.state.setAdapter(spinnerArrayAdapter);
    }

    private void populateViewsWithUserProfile() {

        binding.zipcode.getEditText().setText(MaskWatcher.addMask(profile.recoverZipcode(), "#####-###"));
        binding.thoroughfare.getEditText().setText(profile.recoverThoroughfare());
        binding.number.getEditText().setText(profile.recoverNumber());
        binding.complement.getEditText().setText(profile.recoverComplement());
        binding.neighborhood.getEditText().setText(profile.recoverNeighborhood());
        binding.city.getEditText().setText(profile.recoverCity());

        try {
            if (profile.getEstadoResidencia() != null) {
                binding.state.setSelection(AddressUtil.getArrayPositionByInitials(profile.getEstadoResidencia(), this));
                profile.defineState(profile.getEstadoResidencia());
            }
        } catch (Exception ex) {
            Log.d("Carrega Estado", ex.getMessage());
        }
    }

    //Fim Endereço
    private void onClick() {
        binding.fabEditPhoto.setOnClickListener(view -> showOptionsPhoto());
        binding.includeEndButtons.btnSave.setOnClickListener(view -> {
            if (validateFields()) {
                presenter.saveProfile();
                // presenterAddress.saveAddress();
            }
        });
        binding.includeEndButtons.btnCancel.setOnClickListener(view -> finish());

        binding.etBirthdayDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, dateStart, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        });
        binding.imgTerms.setOnClickListener(v -> startActivity(new Intent(this, RegulationsActivity.class)));

    }

    private void populateView(Profile profile) {
        binding.spinnerGender.setSelection(presenter.getGenderFromProfile());
        binding.tvCpf.setText("CPF: " + MaskWatcher.addMask(profile.getDocIdentificacion(), "###.###.###-##"));

        PhoneNumberFormatter phoneNumberFormatter = new PhoneNumberFormatter(new WeakReference<EditText>(binding.tietPhone));
        binding.tietPhone.addTextChangedListener(phoneNumberFormatter);

        if (!profile.getTelefonoMovil().isEmpty()) {
            /*if(profile.getTelefonoMovil().length()>11)
                binding.tietPhone.setText(profile.getTelefonoMovil().substring(0, 11));
            else*/
            binding.tietPhone.setText(profile.getTelefonoMovil());
        }

        Glide.with(ProfileActivity.this)
                .load(profile.getAvatar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgProfile);
        profileBase64 = profile.getAvatar();

        myCalendar = Calendar.getInstance();
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        if (profile.getFechaNacimiento() != null) {
            binding.etBirthdayDate.setText(sdf.format(profile.getFechaNacimiento()));
        } else {
            binding.etBirthdayDate.setText(sdf.format(myCalendar.getTime()));
        }

        dateStart = (view, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            binding.etBirthdayDate.setText(sdf.format(calendar.getTime()));
        };
    }

    public void getProfileFromView(Profile profile, String profileBase64) {
        profile.setNombre(binding.tietName.getText().toString().trim());
        profile.setApellido(binding.tietLastname.getText().toString().trim());
        profile.setCorreo(binding.tietEmail.getText().toString().trim());
        profile.setTelefonoMovil(binding.tietPhone.getText().toString().replace("(", "").replace(")", "").replace("-", "").trim());

        //profile = MainActivity.profile;
        Glide.with(this)
                .load(profile.getAvatar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgProfile);
        profileBase64 = profile.getAvatar();

        //Endereço
        this.populateUserProfileWithInputs(profile);
       /* profile.defineZipcode(binding.zipcode.getEditText().getText().toString().replace("-",""));
        profile.setCodigoPostal(binding.zipcode.getEditText().getText().toString().replace("-",""));
        profile.setDireccion(binding.thoroughfare.getEditText().getText().toString());//Logradouro
        profile.defineNumber(binding.number.getEditText().getText().toString());
        profile.setDireccion(binding.complement.getEditText().getText().toString());
        profile.defineNeighborhood(binding.neighborhood.getEditText().getText().toString());//Bairro
        profile.setCiudadResidencia(binding.city.getEditText().getText().toString());
        String state = AddressUtil.convertFullStateNameToInitials(binding.state.getSelectedItem().toString(), this);
        profile.setEstadoResidencia(state);*/

        if (profileBase64 != null) profile.setAvatar(profileBase64);

        if (binding.spinnerGender.getSelectedItemPosition() == Constants.SPINNER_GENDER_MALE) {
            profile.setIndGenero("M");
        } else if (binding.spinnerGender.getSelectedItemPosition() == Constants.SPINNER_GENDER_FEMALE) {
            profile.setIndGenero("F");
        }

        Calendar birthdayCalendar = Calendar.getInstance();

        birthdayCalendar.setTimeZone(TimeZone.getDefault());

        try {
            birthdayCalendar.setTime(new SimpleDateFormat("dd/MM/yyyy")
                    .parse(binding.etBirthdayDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        birthdayCalendar.set(Calendar.HOUR_OF_DAY, birthdayCalendar.getMinimum(Calendar.HOUR_OF_DAY));
        birthdayCalendar.set(Calendar.MINUTE, birthdayCalendar.getMinimum(Calendar.MINUTE));
        birthdayCalendar.set(Calendar.SECOND, birthdayCalendar.getMinimum(Calendar.SECOND));
        birthdayCalendar.set(Calendar.MILLISECOND, birthdayCalendar.getMinimum(Calendar.MILLISECOND));

        profile.setFechaNacimiento(birthdayCalendar.getTimeInMillis());
    }

    public Bitmap getImageFromImageProfile() {
        try {
            byte[] encodeByte = Base64.decode(profile.getAvatar(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    private void showOptionsPhoto() {
        photoDialog = new CustomPhotoPickerDialog(this, new CustomPhotoPickerDialog
                .OnOptionPhotoSelected() {
            @Override
            public void onGallery() {
                ProfileActivityPermissionsDispatcher.openGalleryForImageWithPermissionCheck(ProfileActivity.this);
                photoDialog.dismiss();
            }

            @Override
            public void onCamera() {
                //ProfileActivityPermissionsDispatcher.openCameraForImageWithPermissionCheck(ProfileActivity.this);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
                photoDialog.dismiss();
            }

            @Override
            public void onAvatar() {
                photoDialog.dismiss();
                Intent intent = new Intent(ProfileActivity.this, AvatarActivity.class);
                startActivityForResult(intent, AVATAR_REQUEST);
            }
        });
        photoDialog.show();
        Window window = photoDialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void openCameraForImage() {
        presenter.fromCamera();
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void openGalleryForImage() {
        presenter.fromGalery();
    }


    public Activity getActivityFromView() {
        return this;
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        //binding.imgProfile.setVisibility(View.VISIBLE);
        profileBitmap = thumbnail;
        binding.imgProfile.setImageBitmap(thumbnail);
        uploadFileToServer();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    //Some error handling
                }

                @Override
                public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {

                    if (requestCode == PICK_PICTURE_FROM_GALLERY) {

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(
                                selectedImage, filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        if (bitmap != null) {
                            bitmap = presenter.setGalleryRequest(data);

                            binding.imgProfile.setImageBitmap(bitmap);
                            presenter.setBitmapPhoto(presenter.setGalleryRequest(data));
                            profileBitmap = bitmap;

                            //base64
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                            byte[] imageBytes = baos.toByteArray();
                            presenter.setAvatar(Base64.encodeToString(imageBytes, Base64.NO_WRAP));
                            Log.e("base64", profileBase64);
                            Toast.makeText(ProfileActivity.this, "Salvo!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Por favor, tente outra imagem.", Toast.LENGTH_LONG).show();
                        }
                    }
                    presenter.setNewImgFile(imagesFiles.get(0));

                    Glide.with(getApplicationContext())
                            .load(imagesFiles.get(0))
                            .asBitmap()
                            .fitCenter()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .skipMemoryCache(true)
                            .into(binding.imgProfile);
                    presenter.setBitmapPhoto(presenter.setGalleryRequest(data));
                    profileBase64 = profile.getAvatar();
                }
            });

            if (requestCode == CAMERA_REQUEST) {
                onCaptureImageResult(data);
            }

            if (requestCode == AVATAR_REQUEST) {
                Bitmap bitmap = presenter.setAvatarRequest(data);
                if (bitmap != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    byte[] imageBytes = baos.toByteArray();
                    presenter.setAvatar(Base64.encodeToString(imageBytes, Base64.NO_WRAP));
                    Log.e("base64", profileBase64);
                    Toast.makeText(ProfileActivity.this, "Salvo!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Por favor, tente outra imagem.", Toast.LENGTH_LONG).show();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                try {
                    File path = new File(getFilesDir().toString() + "/avatar/");

                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    File image = new File(path, "avatar.jpg");
                    if (image.exists()) {
                        image.delete();
                    }

                    if (!image.exists()) {
                        image.createNewFile();
                    }
                    FileOutputStream fOut = new FileOutputStream(image);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
                    fOut.close();

                    presenter.setNewImgFile(image);
                } catch (Exception e) {

                }

                Glide.with(this)
                        .load(stream.toByteArray())
                        .asBitmap()
                        .fitCenter()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(binding.imgProfile);
                presenter.setBitmapPhoto(presenter.setAvatarRequest(data));
                profileBase64 = profile.getAvatar();
            }
        }
    }

    private void uploadFileToServer() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        byte[] imageBytes = baos.toByteArray();
        presenter.setAvatar(Base64.encodeToString(imageBytes, Base64.NO_WRAP));
        Log.e("base64", profileBase64);
        Toast.makeText(ProfileActivity.this, "Salvo!", Toast.LENGTH_LONG).show();

        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        profileBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        profileBase64 = profileBase64.replaceAll("\n", "");
        Log.e("base64", profileBase64);*/
    }

    private boolean validateFields() {
        String name = binding.tilNameLayout.getEditText().getText().toString().trim();
        String lastName = binding.tilLastnameLayout.getEditText().getText().toString().trim();
        String email = binding.tilEmailLayout.getEditText().getText().toString().trim();
        String cellphone = binding.tilPhoneLayout.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            binding.tilNameLayout.getEditText().setError(getString(R.string.empty_field_error));
            binding.tilNameLayout.getEditText().requestFocus();
            return false;
        } else if (lastName.isEmpty()) {
            binding.tilLastnameLayout.getEditText().setError(getString(R.string.empty_field_error));
            binding.tilLastnameLayout.getEditText().requestFocus();
            return false;
        } else if (email.isEmpty()) {
            binding.tilEmailLayout.getEditText().setError(getString(R.string.empty_field_error));
            binding.tilEmailLayout.getEditText().requestFocus();
            return false;
        } else if (cellphone.isEmpty()) {
            binding.tilPhoneLayout.getEditText().setError(getString(R.string.empty_field_error));
            binding.tilPhoneLayout.getEditText().requestFocus();
            return false;
        }
        return true;
    }

    private void getProfile() {
        //profile = MainActivity.profile;
        Glide.with(ProfileActivity.this)
                .load(profile.getAvatar())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.imgProfile);
        profileBase64 = profile.getAvatar();
    }

}
