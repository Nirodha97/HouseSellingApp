package com.nirodha.houseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dashboard extends AppCompatActivity  {


    RecyclerView recyclerView;
    Dialog dialog;
    ListView lv;
    AutoCompleteTextView search;
    ImageButton rent_apartment,rent_house,buy_house;
    TextView tvtype;
    LinearLayout typelayout1,typelayout2;
    String logkey;

    MyAdapter myAdapter;
    List<ModelImage> imageList;
    //ModelImage modelImage;
    LinearLayoutManager linearLayoutManager;

    Map<String, String> houses =new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView = findViewById(R.id.recycle);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        imageList = new ArrayList<>();
        myAdapter = new MyAdapter(this,imageList);
        recyclerView.setAdapter(myAdapter);





        search= findViewById(R.id.search);

        rent_apartment = findViewById(R.id.rent_apartment);
        buy_house= findViewById(R.id.buy_house);
        rent_house= findViewById(R.id.rent_house);
        typelayout1= findViewById(R.id.typelayout1);
        typelayout2= findViewById(R.id.typelayout2);
        tvtype= findViewById(R.id.tvtype);
        loadhouses();



        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.profile:
                        Toast.makeText(dashboard.this, "Profile", Toast.LENGTH_SHORT).show();
                        profile();
                        break;
                    case R.id.home:
                        Toast.makeText(dashboard.this, "Home", Toast.LENGTH_SHORT).show();
                        home();
                        break;
                    case R.id.addnew:
                        Toast.makeText(dashboard.this, "New", Toast.LENGTH_SHORT).show();
                       addnew();
                        break;

                }


                return false;
            }
        });


        if(isConnected(this))
        {
            showCustomDialog();
        }

    }



    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        builder.setMessage("Please connect to the Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
                        finish();
                    }
                });

    }

    private boolean isConnected(dashboard dashboard) {
        ConnectivityManager connectivityManager = (ConnectivityManager) dashboard.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificonn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileconn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wificonn!=null && wificonn.isConnected() ) || (mobileconn!=null && mobileconn.isConnected()))
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //loadhouses();
        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();



    }

    ///Get data from data base
    public void loadhouses()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/house/index.php";
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        setHouses(response);
                        searchbar(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }

    public void setHouses(JSONArray response)
    {
//        List<HashMap<String, String>> house_list = new ArrayList<HashMap<String, String>>();
        try {

            for(int i=0;i<response.length();i++)
            {
                JSONObject obj = response.getJSONObject(i);
                //HashMap<String,String> map= new HashMap<>();
                String p1= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo1");
                ModelImage modelImage = new ModelImage();
                modelImage.setImageurl(p1);
                modelImage.setId(obj.getString("id"));
                modelImage.setTitle(obj.getString("title"));
                modelImage.setTown(obj.getString("town")+",");
                modelImage.setDistrict(obj.getString("district"));

                if(obj.getString("type").equals("Buy House"))
                {

                    modelImage.setPrice("Total Price : Rs. "+obj.getString("price"));
                }
                else
                {
                    modelImage.setPrice("Monthly rental : Rs. "+obj.getString("price"));

                }
                modelImage.setDate(obj.getString("date"));
                imageList.add(modelImage);
                myAdapter.notifyDataSetChanged();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }





    public void searchbar(JSONArray response)
    {
        List<String> list = new ArrayList<>();
        for(int i=0; i<response.length();i++)
        {
            try {
                JSONObject obj = response.getJSONObject(i);
                list.add(obj.getString("town")+" , "+obj.getString("district"));
                //list.add(obj.getString("district"));
                houses.put(obj.getString("town"),obj.getString("id"));
                houses.put(obj.getString("district"),obj.getString("id"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        int layout = android.R.layout.simple_list_item_1;
        ArrayAdapter adapter = new ArrayAdapter(this,layout,list);
        search.setAdapter(adapter);
    }

    public void profile()
    {
        SessionManager sessionManager = new SessionManager(this);


        if(sessionManager.cheklogin()==true)
        {
            Intent i = new Intent(this,ProfileActivity.class);
            startActivity(i);
        }

        else
        {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialogboxlogin);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView imageViewclose= dialog.findViewById(R.id.imageViewclose);
            Button btnok = dialog.findViewById(R.id.btnok);



            imageViewclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(dashboard.this,loginActivity.class);
                    startActivity(i);
                    Toast.makeText(dashboard.this, "ok", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }

    }

    public void home()
    {
        Intent i = new Intent(this,dashboard.class);
        startActivity(i);
    }
    public void addnew()
    {
        SessionManager sessionManager = new SessionManager(this);
        if(sessionManager.cheklogin()==true)
        {
            Intent i = new Intent(this,addnewHouse.class);
            startActivity(i);
        }


        else
        {
            dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialogboxlogin);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView imageViewclose= dialog.findViewById(R.id.imageViewclose);
            Button btnok = dialog.findViewById(R.id.btnok);



            imageViewclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(dashboard.this,loginActivity.class);
                    startActivity(i);
                    Toast.makeText(dashboard.this, "ok", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }


    }

    public void buy_house(View view)
    {
       typelayout1.setVisibility(View.GONE);
        tvtype.setText("Buy House");
        setHouses_bytype();
       typelayout2.setVisibility(View.VISIBLE);
    }

    public void rent_house(View view)
    {
        typelayout1.setVisibility(View.GONE);
        tvtype.setText("Rent House");
        setHouses_bytype();
        typelayout2.setVisibility(View.VISIBLE)  ;
    }

    public void rent_apartment(View view)
    {
        typelayout1.setVisibility(View.GONE);
        tvtype.setText("Rent Apartment");
        setHouses_bytype();
        typelayout2.setVisibility(View.VISIBLE);
    }

    public void backtodash(View view)
    {
        typelayout1.setVisibility(View.VISIBLE);
        loadhouses();
        typelayout2.setVisibility(View.GONE);
    }

    public void searchnew(View view)
    {
        imageList.clear();
        String location;
        String l1 = search.getText().toString();
        String type= tvtype.getText().toString();

        if(l1.contains(" ")){
            l1= l1.substring(0, l1.indexOf(" "));
        }
        location =l1;
        Toast.makeText(this, ""+location+" "+type, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/house/index.php?town="+location+"&type="+type+"";
        //String url = "http://beezzserver.com/nirodha/sweethomes/house/index.php?town="+location+"&type="+type+"";
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                       Toast.makeText(dashboard.this, ""+response, Toast.LENGTH_SHORT).show();



                        try {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                String p1= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo1");
                                ModelImage modelImage = new ModelImage();
                                modelImage.setImageurl(p1);
                                modelImage.setId(obj.getString("id"));
                                modelImage.setTitle(obj.getString("title"));
                                modelImage.setTown(obj.getString("town")+",");
                                modelImage.setDistrict(obj.getString("district"));

                                if(obj.getString("type").equals("Buy House"))
                                {

                                    modelImage.setPrice("Total Price : Rs. "+obj.getString("price"));
                                }
                                else
                                {
                                    modelImage.setPrice("Monthly rental : Rs. "+obj.getString("price"));

                                }
                                modelImage.setDate(obj.getString("date"));
                                imageList.add(modelImage);
                                myAdapter.notifyDataSetChanged();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                HashMap<String,String> map =(HashMap<String,String>)adapterView.getItemAtPosition(i);
//                                String id  = (String)map.get("id");
//                                Intent i1 = new Intent(dashboard.this,viewHouse.class);
//                                i1.putExtra("key",id);
//                                startActivity(i1);
//                            }
//                        });

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);



    }

    public void setHouses_bytype()
    {

        imageList.clear();
        String type= tvtype.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/house/index.php?type="+type+"";
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                      //  Toast.makeText(dashboard.this, ""+response, Toast.LENGTH_SHORT).show();


                        try {

                            for(int i=0;i<response.length();i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                String p1= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo1");
                                ModelImage modelImage = new ModelImage();
                                modelImage.setImageurl(p1);
                                modelImage.setId(obj.getString("id"));
                                modelImage.setTitle(obj.getString("title"));
                                modelImage.setTown(obj.getString("town")+",");
                                modelImage.setDistrict(obj.getString("district"));

                                if(obj.getString("type").equals("Buy House"))
                                {

                                    modelImage.setPrice("Total Price : Rs. "+obj.getString("price"));
                                }
                                else
                                {
                                    modelImage.setPrice("Monthly rental : Rs. "+obj.getString("price"));

                                }
                                modelImage.setDate(obj.getString("date"));

                                imageList.add(modelImage);
                                myAdapter.notifyDataSetChanged();

                               // Toast.makeText(dashboard.this, ""+obj.getString("price"), Toast.LENGTH_SHORT).show();


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);

    }
}