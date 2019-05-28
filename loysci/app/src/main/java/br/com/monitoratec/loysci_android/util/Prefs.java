package br.com.monitoratec.loysci_android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.monitoratec.loysci_android.model.AccessToken;
import br.com.monitoratec.loysci_android.model.Badge;
import br.com.monitoratec.loysci_android.model.Notification;
import br.com.monitoratec.loysci_android.model.Points;
import br.com.monitoratec.loysci_android.model.Profile;
import br.com.monitoratec.loysci_android.model.Progress;
import br.com.monitoratec.loysci_android.model.Reward;


public final class Prefs {
    private static final String PREF_ACCESS_TOKEN = "pref_access_token";
    private static final String PREF_ACCESS_TOKEN_VALID_UNTIL = "pref_access_token_valid_until";
    private static final String PREF_PROFILE = "pref_profile";
    private static final String PREF_NOTIFICATIONS = "pref_notifications";
    private static final String PREF_JEWELS = "pref_jewels";
    private static final String PREF_PROGRESS = "pref_progress";
    private static final String PREF_POINTS = "pref_points";
    private static final String PREF_CART = "pref_cart";
    private static final String PREF_EXTERNAL_ACCESS_TOKEN = "pref_external_access_token";
    private static final String PREF_EXTERNAL_ACCESS_TOKEN_VALID_UNTIL = "pref_external_access_token_valid_until";
    private static final String PREF_USERNAME = "dummy_var";
    private static final String PREF_MOSTRAR_SLIDER = "mostrarsliderhome";
    private static final String PREF_PW = "dummy_const";
    private static final String PREF_AVATAR = "pref_avatar";
    private static final String PREF_CHALLENGE_STATUS= "pref_challenge_status";
    private static Context context;

    public static void init(Context context) {
        Prefs.context = context;
    }

    public static boolean isLoggedIn() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_ACCESS_TOKEN, null) != null;
    }
    public static boolean isSliderHome() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_MOSTRAR_SLIDER, null) != null;
    }

    public static void clearAccessToken() {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(PREF_ACCESS_TOKEN)
                .apply();
    }

    public static void clearCart() {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(PREF_CART)
                .apply();
    }

    public static void clearAll() {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(PREF_ACCESS_TOKEN)
                .remove(PREF_PROFILE)
                .remove(PREF_NOTIFICATIONS)
                .remove(PREF_JEWELS)
                .remove(PREF_PROGRESS)
                .remove(PREF_POINTS)
                .remove(PREF_CART)
                .remove(PREF_AVATAR)
                .apply();
    }

    public static void saveAccessToken(AccessToken accessToken) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_ACCESS_TOKEN, accessToken.getAccessToken())
                .putLong(PREF_ACCESS_TOKEN_VALID_UNTIL, System.currentTimeMillis() + accessToken.getExpiresIn())
                .apply();
    }

    public static void saveLoginInfo(Pair<String, String> userAndPassword) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USERNAME, userAndPassword.first)
                .putString(PREF_PW, userAndPassword.second)
                .apply();
    }

    public static void saveSlider() {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_MOSTRAR_SLIDER, "0")
                .apply();
    }

    public static void saveExternalAccessToken(AccessToken accessToken) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_EXTERNAL_ACCESS_TOKEN, accessToken.getAccessToken())
                .putLong(PREF_EXTERNAL_ACCESS_TOKEN_VALID_UNTIL, System.currentTimeMillis() + accessToken.getExpiresIn())
                .apply();
    }

    public static Pair<String, String> getLoginInfo() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        return new Pair<>(
                defaultSharedPreferences.getString(PREF_USERNAME, null),
                defaultSharedPreferences.getString(PREF_PW, null)
        );

    }

    public static String getAccessToken() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long validUntil = defaultSharedPreferences.getLong(PREF_ACCESS_TOKEN_VALID_UNTIL, 0);
        if (System.currentTimeMillis() > validUntil) {
            return null;
        } else {
            return defaultSharedPreferences
                    .getString(PREF_ACCESS_TOKEN, null);
        }
    }

    public static String getExternalAccessToken() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long validUntil = defaultSharedPreferences.getLong(PREF_EXTERNAL_ACCESS_TOKEN_VALID_UNTIL, 0);
        if (System.currentTimeMillis() > validUntil) {
            return null;
        } else {
            return defaultSharedPreferences
                    .getString(PREF_EXTERNAL_ACCESS_TOKEN, null);
        }
    }

    public static void saveProfile(Profile profile) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_PROFILE, new Gson().toJson(profile))
                .apply();
    }

    public static String getProfile() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_PROFILE, null);
    }

    public static void saveNotifications(List<Notification> notifications) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_NOTIFICATIONS, new Gson().toJson(notifications))
                .apply();
    }

    public static String getNotifications() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_NOTIFICATIONS, null);
    }

    public static void saveCart(List<Reward> rewardList) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_CART, new Gson().toJson(rewardList))
                .apply();
    }

    public static List<Reward> getCart() {
        List<Reward> voucherList = new ArrayList<>();
        String jsonCart = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_CART, null);

        if (jsonCart != null) {
            voucherList = new Gson().fromJson(jsonCart, new TypeToken<List<Reward>>() {
            }.getType());
        }

        return voucherList;
    }

    public static void saveJewels(List<Badge> badges) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_JEWELS, new Gson().toJson(badges))
                .apply();
    }

    public static String getJewels() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_JEWELS, null);
    }

    public static void saveProgress(Progress progress) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_PROGRESS, new Gson().toJson(progress))
                .apply();
    }

    public static String getProgress() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_PROGRESS, null);
    }

    public static void savePoints(Points points) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_POINTS, new Gson().toJson(points))
                .apply();
    }

    public static String getPoints() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_POINTS, null);
    }

    public static void savePathAvatar(String path) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_AVATAR, new Gson().toJson(path))
                .apply();
    }

    public static String getPathAvatar() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_AVATAR, null);
    }

    public static void setChallengeStatus(String challengeId, int status) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_CHALLENGE_STATUS+"_"+challengeId, status)
                .apply();
    }

    public static int getChallengeStatus(String challengeId) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_CHALLENGE_STATUS+"_"+challengeId, -1);
    }

    private Prefs() {
    }
}
