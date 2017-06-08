package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Usuario on 07/06/2017.
 */

public class TimePieceSharedPreferences {
    static final String EXECUTED = "executed";
    static final String AUTH_TOKEN= "auth_token";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static Boolean getExecuted(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(EXECUTED, false);
    }

    public static void setExecuted(Context ctx, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(EXECUTED, value);
        editor.apply();
    }

    public static void clearSharedPreferences(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}
