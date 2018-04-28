package com.yoryo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yoryo.base.MyApplicationClass;


public class AppPreferenceStorage {

    private static final String mAppPref = "mAppPref";
    private static final String appUrl = "app_url";

    private static String APP_URL = "";

    public static void saveAppUrl(String appUrl) {
        SharedPreferences hxPrefs = MyApplicationClass.getAppContext().getSharedPreferences(mAppPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = hxPrefs.edit();
        editor.putString(appUrl, appUrl);

        setAppUrl(appUrl);
        editor.apply();
    }

    public static String getAppUrl() {
        SharedPreferences hxPrefs = MyApplicationClass.getAppContext().getSharedPreferences(mAppPref, Context.MODE_PRIVATE);
        APP_URL = hxPrefs.getString(appUrl, "");
        return APP_URL;
    }

    private static void setAppUrl(String appUrl) {
        APP_URL = appUrl;
    }

}