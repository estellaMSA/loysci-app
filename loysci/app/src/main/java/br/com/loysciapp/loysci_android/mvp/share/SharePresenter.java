package br.com.loysciapp.loysci_android.mvp.share;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import br.com.loysciapp.loysci_android.R;
import br.com.loysciapp.loysci_android.model.Profile;
import br.com.loysciapp.loysci_android.util.Prefs;

public class SharePresenter {


    private final ShareView view;
    private final Context context;
    private Profile profile;

    public SharePresenter(ShareView view, Context context) {

        this.view = view;
        this.context = context;
        Prefs.init(this.context);

    }



    public void init(){

        getProfile();
        this.view.displayUserId(profile.getIdMiembro());

    }

    private void getProfile() {


        String jsonProfile = Prefs.getProfile();
        if (jsonProfile != null) {
            profile = new Gson().fromJson(jsonProfile, Profile.class);
        }
        else
            this.view.showErrorMessage("Perfil não disponível, efetue um novo login!");

    }


    public void copyIdToClipboard(){

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setText(profile.getIdMiembro());
        this.view.showSuccessToast("Código copiado");

    }


    public void onShareClick(){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("Olá estou adorando o app %s! Para se divertir e ganhar prêmios basta baixar o app e inserir o meu código: %s. %s"
                ,context.getString(R.string.app_name),profile.getIdMiembro(), String.format("https://play.google.com/store/apps/details?id=%s",context.getPackageName())));
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);


    }

}
