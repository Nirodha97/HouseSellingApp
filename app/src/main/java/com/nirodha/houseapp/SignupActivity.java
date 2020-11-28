package com.nirodha.houseapp;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener{

    EditText etfirstname, etlastname, etemail, etnic, ettel, etpassword, etcpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // getSupportActionBar().setTitle("User Registration");
        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#FFFFFF"));
        ActionBar actionBar;
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FF9100'>User Registration</font>"));
        

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etfirstname = findViewById(R.id.firstname);
        etlastname = findViewById(R.id.lastname);
        etemail = findViewById(R.id.email);
        etnic = findViewById(R.id.nic);
        ettel= findViewById(R.id.tel);
        etpassword= findViewById(R.id.password);
        etcpassword = findViewById(R.id.cpassword);


    }

    public void back1(View view)
    {

        Intent i = new Intent(this,loginActivity.class);
        startActivity(i);
    }

    public void signup(View view)
    {

        final String firstname = etfirstname.getText().toString().trim();
        final String lastname = etlastname.getText().toString().trim();
        final String email = etemail.getText().toString().trim();
        final String nic = etnic.getText().toString().trim();
        final String tel = ettel.getText().toString().trim();
        final String password = etpassword.getText().toString().trim();
        final String cpassword = etcpassword.getText().toString().trim();

        String emailpattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        if(TextUtils.isEmpty(firstname))
        {
            etfirstname.setError("First Name is required");
            return;
        }

        else if(TextUtils.isEmpty(lastname))
        {
            etlastname.setError("Last Name is required");
            return;
        }
        else if(TextUtils.isEmpty(nic))
        {
            etnic.setError("NIC is required");
            return;
        }

        else if(TextUtils.isEmpty(tel))
        {
            ettel.setError("Contact Number is required");
            return;
        }

        else if(tel.length()!=10 || !tel.matches("\\d+(?:\\.\\d+)?"))
        {
            ettel.setError(" Invalid Contact Number");
            return;
        }

       else if(TextUtils.isEmpty(email))
        {
            etemail.setError("Email is required");
            return;
        }

        else if(!email.matches(emailpattern))
        {
            etemail.setError(" Invalid email address");
            return;
        }


        else if(TextUtils.isEmpty(password))
        {
            etpassword.setError("Password is required");
            return;
        }
        else if(password.length()<8)
        {
            etpassword.setError("Password must be more than 8 characters");
        }


        else if(!cpassword.equals(password))
        {
            etcpassword.setError("Password mismatch");
            return;
        }



        else
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://beezzserver.com/nirodha/sweethomes/register/insert.php";
            StringRequest request = new StringRequest(Request.Method.POST,url,this,this){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> params = new HashMap<>();

                    params.put("firstname",firstname);
                    params.put("lastname",lastname);
                    params.put("email",email);
                    params.put("nic",nic);
                    params.put("tel",tel);
                    params.put("password",password);
                    //params.put("cpassword",cpassword);

                    return params;
                }
            };
            queue.add(request);
            Intent i = new Intent(this,loginActivity.class);
            startActivity(i);
        }


    }

    @Override
    public void onResponse(String response) {
        Toast.makeText(this, "Success"+response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        error.printStackTrace();
        Toast.makeText(this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}