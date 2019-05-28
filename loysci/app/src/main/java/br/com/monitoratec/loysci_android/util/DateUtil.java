package br.com.monitoratec.loysci_android.util;

/**
 * Created by Felipe Galeote on 31,Outubro,2018
 */
public class DateUtil {

    public static long convertDaysToMilliseconds(int days){
        //86400000L = 24L * 60L * 60L * 1000L
        return days * 86400000L;
    }
}
