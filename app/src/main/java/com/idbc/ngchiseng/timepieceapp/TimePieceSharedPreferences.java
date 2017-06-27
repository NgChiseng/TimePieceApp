package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ChiSeng Ng on 07/06/2017.
 * Class used for define all the SharedPreferences in the TimePiece App, this contain:
 * - Executed Preferences: Used for define the execute of the initial tour, that should be called
 * only in the first time that the application is executed.
 */

public class TimePieceSharedPreferences {

    /*
        Static Keys of each SharedPreferences
    */
    static final String EXECUTED = "executed";
    static final String AUTH_TOKEN= "auth_token";

    /* This define the default SharedPreference that will return in case of error. It is necessary
    for implements SharedPreference in a Class.
     */
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    /*  Set of methods that will use for get the SharedPrefences value belonging to each of them.
        @date[08/06/2017]
        @author[ChiSeng Ng]
        @param [Context] ctx Context of the Activity that call the corresponding method.
        @return [Value]
    */
    public static Boolean getExecuted(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(EXECUTED, false);
    }

    /*  Set of methods that will use for edit the SharedPrefences value belonging to each of them.
        @date[08/06/2017]
        @author[ChiSeng Ng]
        @param [Context] ctx Context of the Activity that call the corresponding method.
        @param [Type_Value] <Value> Value belonging to each SharedPreferences.
        @return [Void]
    */
    public static void setExecuted(Context ctx, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(EXECUTED, value);
        editor.apply();
    }

    /*  Set of methods that will use for clear the SharedPrefences value belonging to each of them.
        @date[08/06/2017]
        @author[ChiSeng Ng]
        @param [Context] ctx Context of the Activity that call the corresponding method.
        @return [Void]
    */
    public static void clearSharedPreferences(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}
