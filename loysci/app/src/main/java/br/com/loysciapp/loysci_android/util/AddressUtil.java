package br.com.loysciapp.loysci_android.util;

import android.content.Context;

import br.com.loysciapp.loysci_android.R;

public class AddressUtil {

    public static String convertFullStateNameToInitials(String stateName, Context context){
        String[] allInitials;
        String[] allStateNames;
        allInitials = context.getResources().getStringArray(R.array.ufs);
        allStateNames = context.getResources().getStringArray(R.array.states);

        for(int i = 0; i<allStateNames.length; i++){
            if(stateName.equals(allStateNames[i])){
                return allInitials[i];
            }
        }
        return "";
    }

    public static String convertInitialsToFullStateName(String initials, Context context){
        String[] allInitials;
        String[] allStateNames;
        allInitials = context.getResources().getStringArray(R.array.ufs);
        allStateNames = context.getResources().getStringArray(R.array.states);

        for(int i = 0; i<allInitials.length; i++){
            if(initials.equals(allInitials[i])){
                return allStateNames[i];
            }
        }
        return "";
    }

    public static int getArrayPositionByInitials(String initials, Context context){
        String[] allInitials;
        allInitials = context.getResources().getStringArray(R.array.ufs);

        for(int i = 0; i<allInitials.length; i++){
            if(initials.equals(allInitials[i])){
                return i;
            }
        }
        return 0;
    }
}
