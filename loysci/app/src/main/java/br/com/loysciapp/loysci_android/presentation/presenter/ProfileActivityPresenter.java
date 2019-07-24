package br.com.loysciapp.loysci_android.presentation.presenter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityProfileBinding;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.networkUtils.LoyaltyApi;
import br.com.loysciapp.loysci_android.presentation.ui.activities.ProfileActivity;
import br.com.loysciapp.loysci_android.util.Constants;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.loysciapp.loysci_android.util.Constants.PROFILE_PARCELABLE;

public class ProfileActivityPresenter {

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 2888;
    private static final String BASE_STORAGE_URL = "https://movida.s3.amazonaws.com/images/client";
    private ProfileActivity view;
    private ActivityProfileBinding binding;
    private Profile profile;
    private Bitmap profilePhoto;
    private String newImgName;
    private String newAvatar;
    private File newImgFile;
    private String profileBase64 = "";
    Bitmap profileBitmap;

    public void setNewImgFile(File newImgFile) {
        this.newImgFile = newImgFile;
    }

    public void setAvatar(String profileBase64) {
        this.profileBase64 = profileBase64;
    }

    public ProfileActivityPresenter(ProfileActivity view, ActivityProfileBinding binding) {
        this.view = view;
        this.binding = binding;
    }

    public void saveProfile() {

        binding.profileLayout.setAlpha(0.2f);
        binding.loadingLayout.loadingText.setText(view.getString(R.string.updating_data));
        binding.loadingLayout.loadingLayout.setVisibility(View.VISIBLE);

        if(newImgFile!=null){
            updateProfile(); // UpdateProfile will be called when this is finished
        }else{
            updateProfile();
        }
/*
        if (profileBitmap == null) {
            profileBitmap = view.getImageFromImageProfile();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);


            byte[] imageBytes = baos.toByteArray();
            profile.setAvatar(Base64.encodeToString(imageBytes, Base64.NO_WRAP));
            profile.getAvatar().replaceAll("\n", "");
            Log.e("base64", profileBase64);
        }
*/
//        if(profilePhoto == null)
//            profilePhoto = view.getImageFromImageProfile();
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        profilePhoto.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        profile.setAvatar(Base64.encodeToString(imageBytes, Base64.DEFAULT));
//        profile.getAvatar().replaceAll("\n", "");
    }


    private void updateProfileImage() {
        newImgName = profile.getIdMiembro() + System.currentTimeMillis()+".jpg";
        LoyaltyApi.updateProfilePictureToken(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.headers().get("X-Auth-Token") != null) {
                    InputStream in = null;
                    try {
                        byte[] buf;
                        in = new FileInputStream(newImgFile);
                        buf = new byte[in.available()];
                        while (in.read(buf) != -1) ;

                        RequestBody requestBody = RequestBody
                                .create(MediaType.parse("application/octet-stream"), buf);
                        LoyaltyApi.uploadProfilePicture(requestBody, newImgName, response.headers().get("X-Auth-Token"), new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                response.code();
                                newAvatar = BASE_STORAGE_URL+newImgName;
                                updateProfile();
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                displayError();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                displayError();
            }
        });
    }

    private void displayError() {
        Toast.makeText(view, "Algum erro ocorreu", Toast.LENGTH_SHORT).show();
        binding.profileLayout.setAlpha(1f);
        binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
    }


    private void updateProfile() {
        LoyaltyApi.getProfile(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Profile profile = response.body();

                view.getProfileFromView(profile, profileBase64);

                profile.setAvatar(profileBase64);

                LoyaltyApi.updateProfile(profile, new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == Constants.SUCCESSFUL) {
                            Intent intent = new Intent();
                            intent.putExtra(PROFILE_PARCELABLE, profile);
                            view.setResult(Activity.RESULT_OK, intent);
                            view.finish();
                            Toast.makeText(view, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(view, "Algum erro ocorreu", Toast.LENGTH_SHORT).show();
                            binding.profileLayout.setAlpha(1f);
                            binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(view, "Algum erro ocorreu", Toast.LENGTH_SHORT).show();
                        binding.profileLayout.setAlpha(1f);
                        binding.loadingLayout.loadingLayout.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }

    public void fromGalery() {
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(view, intent, GALLERY_REQUEST, new Bundle());
        EasyImage.openGallery(view.getActivityFromView(), GALLERY_REQUEST);
    }

    public void fromCamera() {
        EasyImage.openCamera(view.getActivityFromView(), CAMERA_REQUEST);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(view, intent, CAMERA_REQUEST, new Bundle());
    }

    public Bitmap setCameraRequest(Intent data) {
        if (data.getExtras() != null && data.getExtras().get("data") != null)
            return (Bitmap) data.getExtras().get("data");
        else
            return null;
    }

    public Bitmap setAvatarRequest(Intent data) {
        if (data.getExtras() != null && data.getExtras().get("data") != null)
            return (Bitmap) data.getExtras().get("data");
        else
            return null;
    }

    public Bitmap setGalleryRequest(Intent data) {
        if (data.getData() != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = view.getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
            if (yourSelectedImage != null) {
                return yourSelectedImage;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setBitmapPhoto(Bitmap photo) {
        profilePhoto = photo;
    }


    public int getGenderFromProfile() {
        if (profile.getIndGenero() != null) {
            switch (profile.getIndGenero()) {
                case "M":
                    return 1;
                case "F":
                    return 2;
                case "0":
                    return 3;
                default:
                    return 0;
            }
        } else
            return 0;

    }

    public ArrayAdapter<String> getSpinnerInstance() {
        return new ArrayAdapter<String>(
                view, R.layout.support_simple_spinner_dropdown_item, view.getResources().getStringArray(R.array.gender_arrays)) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(0, 0, 0, 0);
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }


            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(view.getResources().getColor(R.color.inactive_text));
                } else {
                    tv.setTextColor(view.getResources().getColor(R.color.primary_text));
                }
                return view;
            }
        };
    }
}
