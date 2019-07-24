package br.com.loysciapp.loysci_android.mvp.share;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.databinding.ActivityShareWithFriendsBinding;

public class ShareWithFriendsActivity extends AppCompatActivity implements ShareView{




    ActivityShareWithFriendsBinding binding;
    SharePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_share_with_friends);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.getNavigationIcon().setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        setTitle("Indique e Ganhe");


        presenter = new SharePresenter(this,this);
        presenter.init();



        binding.relativeFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.copyIdToClipboard();
            }
        });



        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                presenter.onShareClick();
            }
        });


    }

    @Override
    public void displayUserId(String idUser) {


        binding.textCodigo.setText(idUser);

    }

    @Override
    public void showSuccessToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String s) {

        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
