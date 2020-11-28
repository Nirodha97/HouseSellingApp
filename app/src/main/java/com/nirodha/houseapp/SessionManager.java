package com.nirodha.houseapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;
    private static final String IS_LOGIN="IsLoggedIn";
    public static final String KEY_FIRSTNAME="firstname";
    public static final String KEY_LASTNAME="lastname";
    public static final String KEY_EMAIL="email";
    public static final String KEY_TEL="tel";
    public static final String KEY_NIC="nic";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_ID="uid";

    public SessionManager(Context _context)
    {
        userSession=_context.getSharedPreferences("userLoginSession",Context.MODE_PRIVATE);
        editor=userSession.edit();
    }

    public void createLoggingSession(String firstname,String lastname, String email, String tel, String nic, String uid, String password)
    {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_FIRSTNAME,firstname);
        editor.putString(KEY_LASTNAME,lastname);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_TEL,tel);
        editor.putString(KEY_NIC,nic);
        editor.putString(KEY_ID,uid);
        editor.putString(KEY_PASSWORD,password);
        editor.commit();
    }

    public HashMap<String,String> getUserData()
    {
        HashMap<String,String> userdata= new HashMap<String, String>();
        userdata.put(KEY_FIRSTNAME,userSession.getString(KEY_FIRSTNAME,null));
        userdata.put(KEY_LASTNAME,userSession.getString(KEY_LASTNAME,null));
        userdata.put(KEY_EMAIL,userSession.getString(KEY_EMAIL,null));
       userdata.put(KEY_TEL,userSession.getString(KEY_TEL,null));
        userdata.put(KEY_NIC,userSession.getString(KEY_NIC,null));
        userdata.put(KEY_ID,userSession.getString(KEY_ID,null));
        userdata.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));

        return userdata;
    }

    public boolean cheklogin()
    {
        if (userSession.getBoolean(IS_LOGIN,false))
        {
            return true;
        }

        else
            return false;

    }


    public void logoutUser()
    {
        editor.clear();
        editor.commit();
    }
}
