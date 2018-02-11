package com.pilambda.findmerenew.MyConstants;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alexis on 27/01/18.
 */

public class MyConstants {

    public static final String SMS_MESSAGE_RECEIVED = "SmsMessage";

    public static final String APPNAKME = "findMe";
    public static final String PREFERENCES = "preferences";
    public static final String PREF_IS_USER_LOGGED = "userLogged";
    public static void setUserLogged(boolean userLogged, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_IS_USER_LOGGED,userLogged);
        editor.commit();
    }

    public static final int arriba = 0;
    public static final int abajo = 1;
}
