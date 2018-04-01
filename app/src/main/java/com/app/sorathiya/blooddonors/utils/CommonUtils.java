package com.app.sorathiya.blooddonors.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

//    zaptest13@gmail.com
    public static final String USERNAME = "sorathiyablooddonors@gmail.com";
    public static String PASSWORD = "krutika123#";

    public static boolean isOnline(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.e("connectivity", e.toString());
        }
        return connected;
    }

    public static boolean isNullString(String stringToCheck) {
        if (stringToCheck == null) {
            return true;
        } else if (stringToCheck.equals("")) {
            return true;
        }
        return false;
    }

    public static boolean checkEmailAddress(String email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
        String dateTime = simpleDateFormat.format(new Date());
        return dateTime;
    }

    public static void closeKeyboard(Activity activity) {
        try {
            InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
