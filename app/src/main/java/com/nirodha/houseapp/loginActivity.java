package com.nirodha.houseapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  loginActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener{

    EditText lemail,lpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
       // getSupportActionBar().setTitle("User Login");

        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#FFFFFF"));
        ActionBar actionBar;
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FF9100' >User Login</font>"));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lemail = findViewById(R.id.lemail);
        lpassword = findViewById(R.id.lpassword);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //checklogin();

    }

    public void checklogin(View view)
    {
        final String lemail1 = lemail.getText().toString().trim();
        final String lpassword1 = lpassword.getText().toString().trim();

        if(TextUtils.isEmpty(lemail1))
        {
            lemail.setError("Email is empty");
            return;
        }

        else if(TextUtils.isEmpty(lpassword1))
        {
            lpassword.setError("Password is empty");
            return;
        }

        else
        {
           // Toast.makeText(this, ""+lemail1+" "+lpassword1, Toast.LENGTH_SHORT).show();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://beezzserver.com/nirodha/sweethomes/register/login.php";
            StringRequest request = new StringRequest(Request.Method.POST,url,this,this){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();

                    params.put("lemail",lemail1);
                    params.put("lpassword",lpassword1);
                    return params;
                }
            };
            queue.add(request);
        }

    }





    public void signup(View view)
    {

        Intent i2 = new Intent(this,SignupActivity.class);
        startActivity(i2);
    }

    @Override
    public void onResponse(String response) {

        try {
            JSONObject obj = new JSONObject(response);
            String message= obj.getString("status");


            if(message.equals("0"))
            {

                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();

//


            }

            else
            {
                String values= obj.getString("values");
                JSONArray jsonArray = new JSONArray(values);
                JSONObject obj2 = jsonArray.getJSONObject(0);
                String fn= obj2.getString("firstname");
                String ln= obj2.getString("lastname");
               String email= obj2.getString("email");
               String tel= obj2.getString("tel");
               String nic= obj2.getString("nic");
               String id= obj2.getString("id");
               String password= obj2.getString("password");

               SessionManager sessionManager = new SessionManager(loginActivity.this);
               sessionManager.createLoggingSession(fn,ln,email,tel,nic,id,password);
               //sessionManager;




                Toast.makeText(this, ""+id, Toast.LENGTH_LONG).show();
                Intent i = new Intent(this,ProfileActivity.class);
                i.putExtra("fn",fn);
                i.putExtra("ln",ln);
                i.putExtra("email",email);
                startActivity(i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onErrorResponse(VolleyError error) {

        error.printStackTrace();
        Toast.makeText(this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
    }




}