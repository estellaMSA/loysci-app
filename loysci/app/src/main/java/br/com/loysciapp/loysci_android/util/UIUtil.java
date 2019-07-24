package br.com.loysciapp.loysci_android.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class UIUtil {


    public static void AlertSucesso(Context context,String msg, AlertListener listener){




        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sucesso");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", (dialog, item) -> {


                listener.onOk();

        });

        builder.show();
    }

    public static void AlertErro(Context context,String msg, AlertListener listener){


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ocorreu um erro");
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", (dialog, item) -> {


            listener.onOk();

        });

        builder.show();

    }

    public static void showConfirme(String msg, String titulo){

    }

    public interface AlertListener{

        void onOk();

    }

    interface ConfirmListener{

        void onSuccess();
        void onError();
    }

}



