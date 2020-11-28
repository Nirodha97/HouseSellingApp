package com.nirodha.houseapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.HashMap;

public class settingActivity extends AppCompatActivity {

    EditText fn,ln,em,te;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
       // getSupportActionBar().setTitle("Edit Details");
        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#FFFFFF"));
        ActionBar actionBar;
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FF9100' >Edit Details</font>"));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fn=findViewById(R.id.firstname);
        ln=findViewById(R.id.lastname);
        em=findViewById(R.id.email);
        te=findViewById(R.id.tel);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userdata= sessionManager.getUserData();

        String f= userdata.get(SessionManager.KEY_FIRSTNAME);
        String l= userdata.get(SessionManager.KEY_LASTNAME);
        String email= userdata.get(SessionManager.KEY_EMAIL);
       // String tel= userdata.get(SessionManager.KEY_TEL);

        fn.setText(f);
        ln.setText(l);
        em.setText(email);
       // te.setText(tel);


    }
}