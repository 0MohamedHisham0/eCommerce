package com.dal4.ecommerce.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Constants {

    private static SharedPreferences sharedPreferences;

    public static void saveUid(Activity activity, String id) {
        sharedPreferences = activity.getSharedPreferences("SOCIAL", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Uid", id);
        editor.apply();
    }

    public static String getUid(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("SOCIAL", Context.MODE_PRIVATE);

        return sharedPreferences.getString("Uid", "empty");
    }

    public static void saveUserData(Activity activity, String email, String pass, String userId, String phone) {
        sharedPreferences = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.putString("Pass", pass);
        editor.putString("UserId", userId);
        editor.putString("phone", phone);
        editor.apply();
    }
    public static void saveUserToken(Activity activity,String userToken) {
        sharedPreferences = activity.getSharedPreferences("UserToken", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("UserToken", userToken);
        editor.apply();
    }

    public static void saveCartSize(Context activity, int CartSize) {
        sharedPreferences = activity.getSharedPreferences("CartSize", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cartSize", CartSize);
        editor.apply();
    }

    public static int getCartSize(Context activity) {
        sharedPreferences = activity.getSharedPreferences("CartSize", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("cartSize", 0);
    }


    public static String getUserData(Activity activity, String key) {
        sharedPreferences = activity.getSharedPreferences("UserData", Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, "empty");
    }
    public static String getUserToken(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("UserToken", Context.MODE_PRIVATE);

        return sharedPreferences.getString("UserToken", "empty");
    }

    public static void RemoveSharedPreData(Activity activity, String sharedPrefName) {
        sharedPreferences = activity.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
