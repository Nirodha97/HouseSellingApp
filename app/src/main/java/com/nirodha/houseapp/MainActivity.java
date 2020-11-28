package com.nirodha.houseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.renderscript.Sampler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    private static int SPALASH_SCREEN_TIME_OUT=300;

    Animation animation1;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //RunAnimation();



    }

    private void RunAnimation()
    {
        animation1 = AnimationUtils.loadAnimation(this,R.anim.animation1);
        animation1.reset();
        title = findViewById(R.id.title);
        title.clearAnimation();
        title.startAnimation(animation1);
    }


    public void seller(View view)
    {
        Intent i = new Intent(this,loginActivity.class);
        startActivity(i);
    }
    public AlphaAnimation bc = new AlphaAnimation(1F,0.8F);
    public void dashboard(View view)
    {
        view.startAnimation(bc);
        Intent i = new Intent(this,dashboard.class);
        startActivity(i);


    }




}