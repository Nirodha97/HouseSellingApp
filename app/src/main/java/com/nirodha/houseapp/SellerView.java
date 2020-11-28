package com.nirodha.houseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellerView extends AppCompatActivity {


    ImageSlider imageSlider;
    Dialog dialog;
    String id;
    TextView title_11,location_11,price_11,type_11,description_11,refno2;
    String name,email,tel;

    Bitmap bitmap;
    RoundedImageView pic1,pic2,pic3,pic4;
    HorizontalScrollView horizontalScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#FFFFFF"));
        ActionBar actionBar;
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FF9100' >View House</font>"));









        setContentView(R.layout.activity_seller_view);
        title_11 = findViewById(R.id.title);
        location_11 = findViewById(R.id.location);
        price_11 = findViewById(R.id.price1);
        type_11 = findViewById(R.id.type1);
        refno2 = findViewById(R.id.refno2);
        description_11 = findViewById(R.id.description1);

        horizontalScrollView= findViewById(R.id.categoryItem);
        imageSlider= findViewById(R.id.slider);


        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomNavigation1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.profile:
                        Toast.makeText(SellerView.this, "Profile", Toast.LENGTH_SHORT).show();
                        profile();
                        break;
                    case R.id.home:
                        Toast.makeText(SellerView.this, "Home", Toast.LENGTH_SHORT).show();
                        home();
                        break;
                    case R.id.addnew:
                        Toast.makeText(SellerView.this, "New", Toast.LENGTH_SHORT).show();
                        addnew();
                        break;

                }


                return false;
            }
        });


        id = getIntent().getStringExtra("key");
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        viewhouse();

    }




    public void profile()
    {
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }

    public void home()
    {
        Intent i = new Intent(this,dashboard.class);
        startActivity(i);
    }
    public void addnew()
    {
        Intent i = new Intent(this,addnewHouse.class);
        startActivity(i);
    }


    public void viewhouse()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/house/index.php?id="+id+"";
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            JSONObject obj = response.getJSONObject(0);
                            name= obj.getString("name");
                            email= obj.getString("email");
                            tel= obj.getString("telephone");
                            String price= obj.getString("price");
                            String title= obj.getString("title");
                            String type= obj.getString("type");
                            String description= obj.getString("description");
                            String ref = obj.getString("id");
                            String location= obj.getString("town")+","+obj.getString("district");

                            String p1 = "http://beezzserver.com/nirodha/sweethomes/house/Images/" + obj.getString("photo1");
                            String p2= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo2");
                            String p3= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo3");
                            String p4= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo4");
                            //Toast.makeText(viewHouse.this, ""+p1, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(viewHouse.this, ""+p2, Toast.LENGTH_SHORT).show();
                            //  Toast.makeText(viewHouse.this, ""+p3, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(viewHouse.this, ""+p4, Toast.LENGTH_SHORT).show();
                            List<SlideModel> slideModels = new ArrayList<>();
                            slideModels.add(new SlideModel(p1,"1"));
                            slideModels.add(new SlideModel(p2,"2"));
                            slideModels.add(new SlideModel(p3,"3"));
                            slideModels.add(new SlideModel(p4,"4"));
                            imageSlider.setImageList(slideModels,true);
//
//                            if(obj.getString("photo1")!="") {
//                           // Toast.makeText(viewHouse.this, ""+obj.getString("photo1"), Toast.LENGTH_SHORT).show();
//                                String p1 = "http://beezzserver.com/nirodha/sweethomes/house/Images/" + obj.getString("photo1");
//                                Glide.with(viewHouse.this).load(p1).centerCrop().into(pic1);
//                            }

//                            if(obj.getString("photo2")!="http://beezzserver.com/nirodha/sweethomes/house/Images/")
//                            {
//                                pic2.setVisibility(View.VISIBLE);
//                                String p2= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo2");
//                                Glide.with(viewHouse.this).load(p2).centerCrop().into(pic2);
//                            }
//
//                            if(obj.getString("photo3")!="http://beezzserver.com/nirodha/sweethomes/house/Images/")
//                            {
//                                pic3.setVisibility(View.VISIBLE);
//                                String p3= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo3");
//                                Glide.with(viewHouse.this).load(p3).centerCrop().into(pic3);
//                            }
//
//                            if(obj.getString("photo4")!="http://beezzserver.com/nirodha/sweethomes/house/Images/")
//                            {
//                                pic4.setVisibility(View.VISIBLE);
//                                String p4= "http://beezzserver.com/nirodha/sweethomes/house/Images/"+obj.getString("photo4");
//                                Glide.with(viewHouse.this).load(p4).centerCrop().into(pic4);
//                            }


                            title_11.setText(title);
                            location_11.setText(location);
                            //price_11.setText(price);

                            if(type.equals("Buy House"))
                            {
                                price_11.setText("Total Price : Rs. "+price);
                            }
                            else
                            {
                                price_11.setText("Monthly rental : Rs. "+price);
                            }
                            type_11.setText(type);
                            description_11.setText(description);
                            refno2.setText(ref);
//                            List<String> list = new ArrayList<String>();
//                            list.add(name);
//                            int layout = R.layout.view_house_bottom_sheet;
//                            String[] columns = {"name"};
//                            int[] labels = {R.id.name_id, };
//
//                            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, layout, cursor, columns, labels);
//                            lv.setAdapter(adapter);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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

    public void contact_seller(View view) {


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                SellerView.this,R.style.BottomSheetDialogTheme
        );
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.view_house_bottom_sheet,
                        (LinearLayout) findViewById(R.id.bottomSheetContainer)
                );

        TextView name1= bottomSheetView.findViewById(R.id.name_id);
        TextView tel1= bottomSheetView.findViewById(R.id.telephone_id);
        TextView email1= bottomSheetView.findViewById(R.id.email_id);
        name1.setText(name);
        tel1.setText(tel);
        email1.setText(email);




        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }



}