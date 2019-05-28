package br.com.monitoratec.loysci_android.presentation.ui.activities;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.databinding.ActivityAvatarBinding;
import br.com.monitoratec.loysci_android.presentation.ui.adapters.AvatarAdapter;

public class AvatarActivity extends AppCompatActivity implements AvatarAdapter.onSelected {

    static final int GENDER = 0;
    static final int SKIN = 1;
    static final int HAT = 2;
    static final int HAIR = 3;
    static final int HEAD = 4;
    static final int BODY = 5;

    ActivityAvatarBinding binding;
    private AvatarPresenter presenter;
    private FloatingActionButton fabSkinSelected;
    private FloatingActionButton fabGenderSelected;
    private TextView tvGenderSelected;

    String gender = "F";

    //Gender, Skin, Hat, Hair, Head, Body
    Integer[] options = {0, -1, -1, -1, -1, -1};

    List<Bitmap> femaleSkinBitmapList;
    List<Bitmap> femaleHatBitmapList;
    List<Bitmap> femaleHairBitmapList;
    List<Bitmap> femaleHeadBitmapList;
    List<Bitmap> femaleBodyBitmapList;

    List<Bitmap> maleSkinBitmapList;
    List<Bitmap> maleHatBitmapList;
    List<Bitmap> maleHairBitmapList;
    List<Bitmap> maleHeadBitmapList;
    List<Bitmap> maleBodyBitmapList;

    private AvatarAdapter hatAdapter;
    private AvatarAdapter hairAdapter;
    private AvatarAdapter headAdapter;
    private AvatarAdapter bodyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_avatar);
        presenter = new AvatarPresenter(this);

        setSupportActionBar(binding.includeToolbar.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.includeToolbar.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        binding.includeToolbar.toolbarTitle.setText(getString(R.string.create_my_avatar));
        fabGenderSelected = binding.fabOptionFemale;
        tvGenderSelected = binding.tvItemFemaleGender;

        populateBitmapLists();
        configOnClicks();
        configAdapters();
        initialize();
    }

    private void initialize() {
        if(gender.equals("F")) {
            showAssetSelected(femaleSkinBitmapList.get(0), binding.ivAvatarSkin);
        } else if(gender.equals("M")){
            showAssetSelected(maleSkinBitmapList.get(0), binding.ivAvatarSkin);
        }
        options[SKIN] = 0;
        binding.fabColorA.setVisibility(View.VISIBLE);
        fabSkinSelected = binding.fabColorA;
    }

    private void populateBitmapLists() {
        this.femaleBodyBitmapList = presenter.getAllAvatarAssets("avatar/female/body");
        this.femaleHairBitmapList = presenter.getAllAvatarAssets("avatar/female/hair");
        this.femaleHatBitmapList = presenter.getAllAvatarAssets("avatar/female/hat");
        this.femaleHeadBitmapList = presenter.getAllAvatarAssets("avatar/female/head");
        this.femaleSkinBitmapList = presenter.getAllAvatarAssets("avatar/female/skin");

        this.maleBodyBitmapList = presenter.getAllAvatarAssets("avatar/male/body");
        this.maleHairBitmapList = presenter.getAllAvatarAssets("avatar/male/hair");
        this.maleHatBitmapList = presenter.getAllAvatarAssets("avatar/male/hat");
        this.maleHeadBitmapList = presenter.getAllAvatarAssets("avatar/male/head");
        this.maleSkinBitmapList = presenter.getAllAvatarAssets("avatar/male/skin");
    }

    private void configAdapters() {
        configHeadAdapter();
        configHatAdapter();
        configHairAdapter();
        configBodyAdapter();
    }

    private void configHeadAdapter() {
        if (gender.equals("F")) {
            headAdapter = new AvatarAdapter(this, femaleHeadBitmapList, AvatarAdapter.Type.HEAD, this);
        } else if (gender.equals("M")) {
            headAdapter = new AvatarAdapter(this, maleHeadBitmapList, AvatarAdapter.Type.HEAD, this);
        }
        binding.rvFace.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvFace.setAdapter(headAdapter);
    }

    private void configHatAdapter() {
        if (gender.equals("F")) {
            hatAdapter = new AvatarAdapter(this, femaleHatBitmapList, AvatarAdapter.Type.HAT, this);
        } else if (gender.equals("M")) {
            hatAdapter = new AvatarAdapter(this, maleHatBitmapList, AvatarAdapter.Type.HAT, this);
        }
        binding.rvAccessories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvAccessories.setAdapter(hatAdapter);
    }

    private void configHairAdapter() {
        if (gender.equals("F")) {
            hairAdapter = new AvatarAdapter(this, femaleHairBitmapList, AvatarAdapter.Type.HAIR, this);
        } else if (gender.equals("M")) {
            hairAdapter = new AvatarAdapter(this, maleHairBitmapList, AvatarAdapter.Type.HAIR, this);
        }
        binding.rvHair.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvHair.setAdapter(hairAdapter);
    }

    private void configBodyAdapter() {
        if (gender.equals("F")) {
            bodyAdapter = new AvatarAdapter(this, femaleBodyBitmapList, AvatarAdapter.Type.BODY, this);
        } else if (gender.equals("M")) {
            bodyAdapter = new AvatarAdapter(this, maleBodyBitmapList, AvatarAdapter.Type.BODY, this);
        }
        binding.rvBody.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvBody.setAdapter(bodyAdapter);
    }

    private void configOnClicks() {
        binding.femaleOptionLayout.setOnClickListener(view -> {
            if(!gender.equals("F")) {
                setGender("F");
                fabGenderSelected = binding.fabOptionFemale;
                fabGenderSelected.setVisibility(View.VISIBLE);
                tvGenderSelected = binding.tvItemFemaleGender;
                tvGenderSelected.setBackground(getDrawable(R.drawable.background_filter_button_clicked));
                tvGenderSelected.setTextColor(getResources().getColor(R.color.white));
            }
            initialize();
        });
        binding.maleOptionLayout.setOnClickListener(view -> {
            if(!gender.equals("M")) {
                setGender("M");
                fabGenderSelected = binding.fabOptionMale;
                fabGenderSelected.setVisibility(View.VISIBLE);
                tvGenderSelected = binding.tvItemMaleGender;
                tvGenderSelected.setBackground(getDrawable(R.drawable.background_filter_button_clicked));
                tvGenderSelected.setTextColor(getResources().getColor(R.color.white));
            }
            initialize();
        });

        binding.colorALayout.setOnClickListener(view -> {
            if(options[SKIN] != 0) {
                setSkin(0);
                fabSkinSelected = binding.fabColorA;
                fabSkinSelected.setVisibility(View.VISIBLE);
            }
        });
        binding.colorBLayout.setOnClickListener(view -> {
            if(options[SKIN] != 1) {
                setSkin(1);
                fabSkinSelected = binding.fabColorB;
                fabSkinSelected.setVisibility(View.VISIBLE);
            }
        });
        binding.colorCLayout.setOnClickListener(view -> {
            if(options[SKIN] != 2) {
                setSkin(2);
                fabSkinSelected = binding.fabColorC;
                fabSkinSelected.setVisibility(View.VISIBLE);
            }
        });
        binding.colorDLayout.setOnClickListener(view -> {
            if(options[SKIN] != 3) {
                setSkin(3);
                fabSkinSelected = binding.fabColorD;
                fabSkinSelected.setVisibility(View.VISIBLE);
            }
        });

        binding.btnSave.setOnClickListener(view -> {
            if(options[0] >= 0 && options[1] >= 0) {
                if (this.gender.equals("F")) {
                    populateBitmapLists();
                    presenter.saveAvatar(presenter.createSingleImageFromMultipleImages(
                            options[SKIN] >= 0 ? femaleSkinBitmapList.get(options[SKIN]) : null,
                            options[HEAD] >= 0 ? femaleHeadBitmapList.get(options[HEAD]) : null,
                            options[HAIR] >= 0 ? femaleHairBitmapList.get(options[HAIR]) : null,
                            options[HAT] >= 0 ? femaleHatBitmapList.get(options[HAT]) : null,
                            options[BODY] >= 0 ? femaleBodyBitmapList.get(options[BODY]) : null,
                            BitmapFactory.decodeResource(this.getResources(), R.drawable.bg_avatar)
                    ), options);
                } else if (this.gender.equals("M")) {
                    populateBitmapLists();
                    presenter.saveAvatar(presenter.createSingleImageFromMultipleImages(
                            options[SKIN] >= 0 ? maleSkinBitmapList.get(options[SKIN]) : null,
                            options[HEAD] >= 0 ? maleHeadBitmapList.get(options[HEAD]) : null,
                            options[HAIR] >= 0 ? maleHairBitmapList.get(options[HAIR]) : null,
                            options[HAT] >= 0 ? maleHatBitmapList.get(options[HAT]) : null,
                            options[BODY] >= 0 ? maleBodyBitmapList.get(options[BODY]) : null,
                            BitmapFactory.decodeResource(this.getResources(), R.drawable.bg_avatar)
                    ), options);
                }
            } else {
                //TODO
            }
        });
    }

    private void setGender(String gender) {


        if(fabGenderSelected != null) {
            fabGenderSelected.setVisibility(View.GONE);
            tvGenderSelected.setBackground(getDrawable(R.drawable.background_gender_avatar_button));
            tvGenderSelected.setTextColor(getResources().getColor(R.color.inactive_text_color_primary));
        }

        this.gender = gender;
        clearAvatar();
        populateBitmapLists();
        if (this.gender.equals("F")) {
            options[GENDER] = 0;
            headAdapter.setNewList(femaleHeadBitmapList);
            hatAdapter.setNewList(femaleHatBitmapList);
            bodyAdapter.setNewList(femaleBodyBitmapList);
            hairAdapter.setNewList(femaleHairBitmapList);
        } else if (this.gender.equals("M")) {
            options[GENDER] = 1;
            headAdapter.setNewList(maleHeadBitmapList);
            hatAdapter.setNewList(maleHatBitmapList);
            bodyAdapter.setNewList(maleBodyBitmapList);
            hairAdapter.setNewList(maleHairBitmapList);
        }
    }

    private void setSkin(int position) {
        if(fabSkinSelected != null) {
            fabSkinSelected.setVisibility(View.GONE);
        }

        if (gender.equals("F")) {
            options[SKIN] = position;
            Bitmap bitmap = femaleSkinBitmapList.get(position);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(binding.ivAvatarSkin);

        } else if (gender.equals("M")) {
            options[SKIN] = position;

            Bitmap bitmap = maleSkinBitmapList.get(position);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(binding.ivAvatarSkin);
        }
    }

    @Override
    public void onClick(int position, AvatarAdapter.Type type) {
        presenter.showAssetsOnAvatar(position, type);
    }

    @Override
    public void onDeselect(AvatarAdapter.Type type) {
        clearAssetDeselect(type);
    }

    public void showAssetSelected(Bitmap bitmap, ImageView view){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        Glide.with(this)
                .load(stream.toByteArray())
                .asBitmap()
                .into(view);
    }

    public void clearAssetDeselect(AvatarAdapter.Type type) {
        if (type == AvatarAdapter.Type.BODY) {
            Glide.clear(binding.ivAvatarBody);
            options[BODY] = -1;
        } else if (type == AvatarAdapter.Type.HAIR) {
            Glide.clear(binding.ivAvatarHair);
            options[HAIR] = -1;
        } else if (type == AvatarAdapter.Type.HAT) {
            Glide.clear(binding.ivAvatarHat);
            options[HAT] = -1;
        } else if (type == AvatarAdapter.Type.HEAD) {
            Glide.clear(binding.ivAvatarHead);
            options[HEAD] = -1;
        }
    }
    private void clearAvatar() {
        if(fabSkinSelected != null) {
            fabSkinSelected.setVisibility(View.GONE);
        }

        Glide.clear(binding.ivAvatarBody);
        bodyAdapter.setSelectedPosition(-1);
        bodyAdapter.notifyDataSetChanged();

        Glide.clear(binding.ivAvatarHair);
        hairAdapter.setSelectedPosition(-1);
        hairAdapter.notifyDataSetChanged();

        Glide.clear(binding.ivAvatarHat);
        hatAdapter.setSelectedPosition(-1);
        hatAdapter.notifyDataSetChanged();

        Glide.clear(binding.ivAvatarHead);
        headAdapter.setSelectedPosition(-1);
        headAdapter.notifyDataSetChanged();

//        Glide.clear(binding.ivAvatarSkin);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
