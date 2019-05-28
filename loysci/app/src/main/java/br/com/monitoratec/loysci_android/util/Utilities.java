package br.com.monitoratec.loysci_android.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.Comparator;

import br.com.monitoratec.loysci_android.R;
import br.com.monitoratec.loysci_android.model.Challenge;

/**
 * Created by Pedro Mazarini on 26/Oct/2018.
 **/
public class Utilities {
    public static float dpToPx(int dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static class challengesSorter implements Comparator<Challenge> {
        @Override
        public int compare(Challenge c1, Challenge c2) {
            if(c1.isCompleted() && c2.isCompleted()){
                return c1.getNombre().toLowerCase().compareTo(c2.getNombre().toLowerCase());
            }else if(c1.isCompleted()){
                return -1;
            }else{
                return 1;
            }
        }
    }
    public static String getOnlyNumbersCpf(String cpf){
        return cpf.replace(".","").replace("-","");
    }

    public static InputFilter[] getInputFilter() {
        String blockedCpfCharacters = "*#/().+-;,N ";
        return new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source != null && blockedCpfCharacters.contains(("" + source))) {
                return "";
            }
            return null;
        }
        };
    }

    public static InputFilter[] getCpfInputFilter() {
        return new InputFilter[]{
                getInputFilter()[0],
                new InputFilter.LengthFilter(14)
        };
    }

    public static InputFilter[] getCepInputFilter() {
        return new InputFilter[]{
                getInputFilter()[0],
                new InputFilter.LengthFilter(9)
        };
    }

    public static InputFilter[] getCellphoneInputFilter() {
        return new InputFilter[]{
                getInputFilter()[0],
                new InputFilter.LengthFilter(13)
        };
    }
}
