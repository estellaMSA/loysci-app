package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityMainBinding;
import br.com.monitoratec.loysci_android.model.Level;
import br.com.monitoratec.loysci_android.model.Notification;
import br.com.monitoratec.loysci_android.model.Points;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.Progress;
import br.com.monitoratec.loysci_android.presentation.ui.fragments.HomeFragment;
import br.com.monitoratec.loysci_android.presentation.ui.listeners.ViewModelSimpleCallback;
import br.com.monitoratec.loysci_android.presentation.ui.presenters.MainActivityPresenter;
import br.com.monitoratec.loysci_android.presentation.ui.viewModels.MainViewModel;
import br.com.monitoratec.loysci_android.util.Prefs;
import br.com.monitoratec.loysci_android.util.WelcomeMessageDialog;

import static br.com.monitoratec.loysci_android.util.Constants.*;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeOptionsListener, View.OnClickListener {
    boolean slider = false;
    private ActivityMainBinding binding;
    private final String TAG = this.getClass().getName();
    boolean isClicked = false;
    public static ArrayList<Notification> notifications;
    MainViewModel model;
    MainActivityPresenter presenter;
    boolean doubleBackToExitPressedOnce = false;
    boolean isNewMember = false;
    boolean communicationAuthorized = false;
    String cardType;
    String telefonoMovil;

    public static Profile profile;

    //Slider
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //binding.layoutProfile.setAlpha(0.2f);
        binding.loadingProfile.setVisibility(View.VISIBLE);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.seekBarLevel.setEnabled(false);
        binding.imgProfile.setOnClickListener(view -> {
            if (model.finishedLoading) {
                if (!isClicked) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(PROFILE_PARCELABLE, model.profile);
                    startActivityForResult(intent, 2);
                }
            }
        });
        if(getIntent().getExtras() != null) {
            isNewMember =   getIntent().getExtras().getBoolean(IS_NEW_MEMBER);
            cardType =  getIntent().getExtras().getString(CARD_TYPE);
            communicationAuthorized =  getIntent().getExtras().getBoolean(AUTHORIZE_COMMUNICATION);
            telefonoMovil = getIntent().getExtras().getString(TELEFONO_MOVIL);
        }
        binding.notificationLayout.setOnClickListener(this);
        binding.cartLayout.setOnClickListener(this);

        presenter = new MainActivityPresenter(this, binding);
        Prefs.init(this);
        binding.logoutLayout.setOnClickListener((v) -> logout());

    }

    private void logout() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(R.string.leave)
                .setMessage("Deseja realmente sair?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    Prefs.clearAll();
                    Intent login = new Intent(this, LoginActivity.class);
                    startActivity(login);
                    finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                })
                .setCancelable(false);
        android.app.AlertDialog dialog = builder.create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        });
        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isClicked = false;
        populateProfile();
        populateNotifications();
        populateUserLevel();
        populateUserPoints();
    }

    public void checkLoading() {
        if (model.finishedLoading) {
            binding.layoutProfile.setAlpha(1f);
            binding.loadingProfile.setVisibility(View.GONE);
        }
    }

    public void populateProfile() {
        String jsonProfile = Prefs.getProfile();
        if (jsonProfile != null) {
            Profile savedProfile = new Gson().fromJson(jsonProfile, Profile.class);
            model.profile = savedProfile;
            setProfileImage();
            setNameLastname();
            model.finishedLoading();
            checkLoading();
        }
        model.loadProfile(new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {
                checkLoading();
                Prefs.saveProfile(model.profile);
                setProfileImage();
                setNameLastname();
                if(isNewMember && cardType!=null){
                    presenter.setPoints(model.profile.getIdMiembro(), REGISTER_VALUE, cardType, "B1", "Bonus Cadastro");
                    model.profile.defineCardType(cardType);
                    model.profile.defineAcceptContract(true);
                    model.profile.defineEmailCommunications(communicationAuthorized);
                    model.profile.setTelefonoMovil(telefonoMovil);
                    presenter.updateProfile(model.profile);
                    isNewMember = false;
                }
            }

            @Override
            public void onFailed(Object object) {
                checkLoading();
                switch ((int) object) {
                    case RESPONSE_EXPIRED_TOKEN:
                        Toast.makeText(MainActivity.this, R.string.token_expired, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case RESPONSE_FAILED_LOAD:
                        Snackbar.make(binding.container, MainActivity.this.getString(R.string.failed_load_profile), Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
        }, true);
        if(isNewMember){
            WelcomeMessageDialog dialog = new WelcomeMessageDialog(this);
            dialog.show();
        }
    }

    private void setNameLastname(){
        String jsonProfile = Prefs.getProfile();
        String nomeSobrenome;
        if (jsonProfile != null) {
            Profile savedProfile = new Gson().fromJson(jsonProfile, Profile.class);

            nomeSobrenome = savedProfile.getNombre() + " " + savedProfile.getApellido();
            binding.nomeSobrenome.setText(nomeSobrenome);
        }
    }

    private void setProfileImage() {
        if (!MainActivity.this.isDestroyed()) {
            Glide.with(MainActivity.this)
                    .load(model.profile.getAvatar())
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(binding.imgProfile);
        }
    }

    public void populateUserPoints() {
        String jsonPoints = Prefs.getPoints();
        if (jsonPoints != null) {
            Points savedPoints = new Gson().fromJson(jsonPoints, Points.class);
            model.points = savedPoints;
            setupPoints();
            model.finishedLoading();
            checkLoading();
        }
        model.loadPoints(new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {
                checkLoading();
                setupPoints();
            }

            @Override
            public void onFailed(Object object) {
                checkLoading();
                Snackbar.make(binding.container, MainActivity.this.getString(R.string.failed_load_profile), Snackbar.LENGTH_LONG).show();

            }
        }, true);
    }

    private void setupPoints() {
        binding.txtPoints.setText(getString(R.string.points_count));
        binding.txtPointsCount.setText(String.valueOf(Math.round(model.points.getDisponible())));
        if (model.points.getVencido() > 0)
            binding.txtExpire.setText(getString(R.string.points_to_expire, String.valueOf(Math.round(model.points.getVencido()))));
    }


    public void populateUserLevel() {
        String jsonProgress = Prefs.getProgress();
        if (jsonProgress != null) {
            Progress savedProgress = new Gson().fromJson(jsonProgress, Progress.class);
            model.progress = savedProgress;
            setupProgress();
            model.finishedLoading();
            checkLoading();
        }
        model.loadProgress(new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {
                checkLoading();
                setupProgress();
            }

            @Override
            public void onFailed(Object object) {
                checkLoading();
                Snackbar.make(binding.container, MainActivity.this.getString(R.string.failed_load_profile), Snackbar.LENGTH_LONG).show();

            }
        }, true);
    }

    private void setupProgress() {
        List<Level> levels = new ArrayList<>(model.progress.getNiveles());
        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            if (model.progress.getNivelActual() != null && model.progress.getNivelActual().getIdNivel().equals(level.getIdNivel())) {
                int multiple = (int) (100 * model.progress.getProgreso());

                int result;

                if (i == levels.size() - 1) {
                    result = 100;
                }
                else {
                    result = multiple / Math.round(model.progress.getNiveles().get(i + 1).getMetricaInicial());
                }

                if (result >= 100) {
                    binding.seekBarLevel.setProgress(100);
                } else {
                    binding.seekBarLevel.setProgress(result);
                }
                binding.txtLevelName.setText(level.getNombre());
                binding.txtLevelCount.setText(getString(R.string.level_number, (i + 1)));
                if (i < (levels.size() - 1)) {
                    binding.txtLevel.setText(levels.get(i + 1).getNombre());
                } else {
                    binding.txtNextLevel.setVisibility(View.GONE);
                }
            }
        }
    }


    public void populateNotifications() {
        String jsonNotifications = Prefs.getNotifications();
        if (jsonNotifications != null) {
            List<Notification> savedNotifications = new Gson().fromJson(jsonNotifications, new TypeToken<List<Notification>>() {
            }.getType());
            model.notifications = savedNotifications;
            setupNotifications();
            model.finishedLoading();
            checkLoading();
        }
        model.loadNotifications(new ViewModelSimpleCallback() {
            @Override
            public void onSuccess() {
                checkLoading();
                setupNotifications();
            }

            @Override
            public void onFailed(Object object) {
                checkLoading();
                Snackbar.make(binding.container, MainActivity.this.getString(R.string.failed_load_profile), Snackbar.LENGTH_LONG).show();

            }
        }, true);
    }

    private void setupNotifications() {
        int unreadCounter = 0;
        for (Notification notification : model.notifications) {
            if (!notification.isVisto()) {
                unreadCounter++;
            }
        }
        if (unreadCounter > 0) {
            binding.imgNotificationBall.setVisibility(View.VISIBLE);
        } else {
            binding.imgNotificationBall.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHomeOptionSelected(int option) {
        switch (option) {

            case HOME_PARTICIPATE_WIN:
                binding.mainViewPager.setCurrentItem(TAB_GAMES);
                break;

            case HOME_GET_PRIZE:
                binding.mainViewPager.setCurrentItem(TAB_VOUCHERS);
                break;

            case HOME_SALES:
                binding.mainViewPager.setCurrentItem(TAB_SALES);
                break;

            case HOME_REGULAMENTO:
                startActivity(new Intent(this, RegulationsActivity.class));
                break;


            case HOME_CARTAO_VIRTUAL:
                startActivity(new Intent(this, CartaoActivity.class));
                break;

            case HOME_PERSONAL_DATA_OPTION:
                if (model.finishedLoading) {
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra(PROFILE_PARCELABLE, model.profile);
                    startActivity(intent);
                }
                break;

            case HOME_ADDRESS_OPTION:
                if (model.finishedLoading) {
                    Intent intent = new Intent(this, EditAddressActivity.class);
                    intent.putExtra(PROFILE_PARCELABLE, model.profile);
                    startActivityForResult(intent, UPDATE_ADDRESS);
                }
                break;

            case HOME_CHANGE_PASS_OPTION:
                if (model.finishedLoading) {
                    Intent intent = new Intent(this, ChangePasswordActivity.class);
                    intent.putExtra(PROFILE_PARCELABLE, model.profile);
                    startActivityForResult(intent, UPDATE_PASS);
                }
                break;
            case HOME_DRIVER_LICENSE_OPTION:
                if (model.finishedLoading) {
                    Intent updateLicense = new Intent(this, UpdateDriversLicenseActivity.class);
                    updateLicense.putExtra(PROFILE_PARCELABLE, model.profile);
                    startActivityForResult(updateLicense, UPDATE_LICENSE);
                }
                break;

            case HOME_BADGES:
                startActivity(new Intent(this, BadgesActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        model.activityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notificationLayout:
                if (model.finishedLoading) {
                    Intent intent = new Intent(MainActivity.this, NotificationsActivity.class);
                    intent.putParcelableArrayListExtra(NOTIFICATIONS_PARCELABLE, new ArrayList<>(model.notifications));
                    startActivity(intent);
                }
                break;

            case R.id.cartLayout:
                if (model.finishedLoading) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.press_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
