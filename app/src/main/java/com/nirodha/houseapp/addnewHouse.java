package com.nirodha.houseapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class addnewHouse extends AppCompatActivity implements Response.Listener<String>,Response.ErrorListener{

    ImageView img1,img2,img3,img4;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    Uri uri;
    String uid;
    Button submit;
    Spinner spinner;
    Spinner spinner2;
    Boolean i1=false,i2=false,i3=false,i4=false;
    EditText etname,ettelephone,ettitle,etprice,ettown,etdescription;
    TextView etemail;
   // int SELECT_IMAGE=1;
    String [] listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_house);
       // getSupportActionBar().hide();
        //getSupportActionBar().setTitle("Add New Advertisement");

        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#FFFFFF"));
        ActionBar actionBar;
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#FF9100'>Add New Advertisement</font>"));


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userdata= sessionManager.getUserData();

        String f= userdata.get(SessionManager.KEY_FIRSTNAME);
        String l= userdata.get(SessionManager.KEY_LASTNAME);
        String email= userdata.get(SessionManager.KEY_EMAIL);
        String tel= userdata.get(SessionManager.KEY_TEL);
         uid= userdata.get(SessionManager.KEY_ID);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        etname= findViewById(R.id.name_id);
        ettelephone= findViewById(R.id.telephone_id);
        etemail= findViewById(R.id.email_id);
        ettitle= findViewById(R.id.title_id);
        etprice= findViewById(R.id.price_id);
        ettown = findViewById(R.id.town_id);
        etdescription=findViewById(R.id.description_id);
        img1 = (ImageView) findViewById(R.id.cam1);
        img2 = (ImageView) findViewById(R.id.cam2);
        img3 = (ImageView) findViewById(R.id.cam3);
        img4 = (ImageView) findViewById(R.id.cam4);
        spinner = findViewById(R.id.type_id);
        spinner2 = findViewById(R.id.district_id);
        submit = findViewById(R.id.submit);


        etemail.setText(email);
        etname.setText(f+" "+l);
        ettelephone.setText(tel);
        selectType();
        selectDistrict();




    }





//Spinner 1
    public void selectType()
    {



        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-SELECT-");
        arrayList.add("Rent House");
        arrayList.add("Buy House");
        arrayList.add("Rent Apartment");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                if(tutorialsName!="-SELECT-") {
                    Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    //Spinner 2
    public void selectDistrict()
    {



        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("-SELECT-");
        arrayList.add("Colombo");
        arrayList.add("Gampaha");
        arrayList.add("Kalutara");
        arrayList.add("Kurunegala");
        arrayList.add("Puttalam");
        arrayList.add("Galle");
        arrayList.add("Matara");
        arrayList.add("Hambanthota");
        arrayList.add("Ampara");
        arrayList.add("Batticaloa");
        arrayList.add("Trincolamee");
        arrayList.add("Anuradhapura");
        arrayList.add("Polonnaruwa");
        arrayList.add("Matale");
        arrayList.add("Kandy");
        arrayList.add("Nuwara Eliya");
        arrayList.add("Kegalle");
        arrayList.add("Ratnapura");
        arrayList.add("Jaffna");
        arrayList.add("Kilinochchi");
        arrayList.add("Mannar");
        arrayList.add("Mullaitivu");
        arrayList.add("Vavuniya");
        arrayList.add("Badulla");
        arrayList.add("Monaragala");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner2.setAdapter(arrayAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                if(tutorialsName!="-SELECT-") {
                    Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }




    /////////----------------------Cam1-------------------------------------------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void cam1(View view) {

        listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(addnewHouse.this);
        builder.setTitle("Select Image");
        //builder.setIcon(R.drawable.ic_launcher_background);
        builder.setItems(listitems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (listitems[i].equals("Take Photo"))
                {
                    camera1();
                }

                else if(listitems[i].equals("Choose from Gallery"))
                {
                    gallery1();
                }
            }
        });

//cancel button
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void camera1()
    {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,111);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void gallery1()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }














    /////////----------------------Cam2-------------------------------------------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void cam2(View view) {

        listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(addnewHouse.this);
        builder.setTitle("Select Image");
        //builder.setIcon(R.drawable.ic_launcher_background);
        builder.setItems(listitems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (listitems[i].equals("Take Photo"))
                {
                    camera2();
                }

                else if(listitems[i].equals("Choose from Gallery"))
                {
                    gallery2();
                }
//                else if (listitems[i].equals("Remove Image"))
//                {
//
//                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void camera2()
    {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,112);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void gallery2()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }



    /////////----------------------Cam3-------------------------------------------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void cam3(View view) {

        listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(addnewHouse.this);
        builder.setTitle("Select Image");
        //builder.setIcon(R.drawable.ic_launcher_background);
        builder.setItems(listitems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (listitems[i].equals("Take Photo"))
                {
                    camera3();
                }

                else if(listitems[i].equals("Choose from Gallery"))
                {
                    gallery3();
                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void camera3()
    {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,113);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void gallery3()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,3);
    }



    /////////----------------------Cam4-------------------------------------------------\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void cam4(View view) {

        listitems = new String[]{"Take Photo","Choose from Gallery","Remove Image"};
        AlertDialog.Builder builder = new AlertDialog.Builder(addnewHouse.this);
        builder.setTitle("Select Image");
        //builder.setIcon(R.drawable.ic_launcher_background);
        builder.setItems(listitems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (listitems[i].equals("Take Photo"))
                {
                    camera4();
                }

                else if(listitems[i].equals("Choose from Gallery"))
                {
                    gallery4();
                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



    public void camera4()
    {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,114);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }


    public void gallery4()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,4);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==111 & resultCode==RESULT_OK)
        {
            i1=true;
            bitmap1=(Bitmap)data.getExtras().get("data");
            img1.setImageBitmap(bitmap1);
            img2.setVisibility(View.VISIBLE);

        }

        if(requestCode==1 & resultCode==RESULT_OK)
        {
            i1=true;
            uri=data.getData();
            try {
                bitmap1=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img1.setImageBitmap(bitmap1);;
                img2.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode==112 & resultCode==RESULT_OK)
        {
            i2=true;
            bitmap2=(Bitmap)data.getExtras().get("data");
            img2.setImageBitmap(bitmap2);;
            img3.setVisibility(View.VISIBLE);

        }

        if(requestCode==2 & resultCode==RESULT_OK)
        {
            i2=true;
            uri=data.getData();
            try {
                bitmap2=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img2.setImageBitmap(bitmap2);;
                img3.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode==113 & resultCode==RESULT_OK)
        {
            i3=true;
            bitmap3=(Bitmap)data.getExtras().get("data");
            img3.setImageBitmap(bitmap3);;
            img4.setVisibility(View.VISIBLE);

        }

        if(requestCode==3 & resultCode==RESULT_OK)
        {
            i3=true;
            uri=data.getData();
            try {
                bitmap3=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img3.setImageBitmap(bitmap3);;
                img4.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode==114 & resultCode==RESULT_OK)
        {
            i4=true;
            bitmap4=(Bitmap)data.getExtras().get("data");
            img4.setImageBitmap(bitmap4);;
            //img4.setVisibility(View.VISIBLE);

        }

        if(requestCode==4 & resultCode==RESULT_OK)
        {
            i4=true;
            uri=data.getData();
            try {
                bitmap4=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                img4.setImageBitmap(bitmap4);;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    //images convert to string
    public String getStringImage1(Bitmap bitmap1) {
        ByteArrayOutputStream ba1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG,100,ba1);
        byte[] imagebyte1 = ba1.toByteArray();
        String encode1 = Base64.encodeToString(imagebyte1,Base64.DEFAULT);
        return encode1;
    }

    public String getStringImage2(Bitmap bitmap2) {
        ByteArrayOutputStream ba2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG,100,ba2);
        byte[] imagebyte2 = ba2.toByteArray();
        String encode2 = Base64.encodeToString(imagebyte2,Base64.DEFAULT);
        return encode2;
    }

    public String getStringImage3(Bitmap bitmap3) {
        ByteArrayOutputStream ba3 = new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.JPEG,100,ba3);
        byte[] imagebyte3 = ba3.toByteArray();
        String encode3 = Base64.encodeToString(imagebyte3,Base64.DEFAULT);
        return encode3;
    }

    public String getStringImage4(Bitmap bitmap4) {
        ByteArrayOutputStream ba4 = new ByteArrayOutputStream();
        bitmap4.compress(Bitmap.CompressFormat.JPEG,100,ba4);
        byte[] imagebyte4 = ba4.toByteArray();
        String encode4 = Base64.encodeToString(imagebyte4,Base64.DEFAULT);
        return encode4;
    }


    public AlphaAnimation bc = new AlphaAnimation(1F,0.8F);
    public void submit(View view)
    {
        view.startAnimation(bc);
        final String name= etname.getText().toString();
        final String telephone= ettelephone.getText().toString();
        final String email= etemail.getText().toString();
        final String title= ettitle.getText().toString();
        final String type= spinner.getSelectedItem().toString();
        final  String price= etprice.getText().toString();
        final  String town= ettown.getText().toString();
        final String description= etdescription.getText().toString();
        final String district= spinner2.getSelectedItem().toString();
        String img1="";
        String img2="";
        String img3="";
        String img4="";
        //Toast.makeText(this, ""+(bitmap1!=null), Toast.LENGTH_SHORT).show();
        if((bitmap1!=null))
        {
            img1 =getStringImage1(bitmap1);
        }
        if((bitmap2!=null))
        {
             img2=getStringImage2(bitmap2);
        }

        if((bitmap3!=null))
        {
            img3 =getStringImage3(bitmap3);
        }

        if((bitmap4!=null))
        {
            img4 =getStringImage4(bitmap4);
        }



        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //Toast.makeText(this, "date "+date, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/house/insert.php";
        String finalImg = img1;
        String finalImg1 = img2;
        String finalImg2 = img3;
        String finalImg3 = img4;
        StringRequest request = new StringRequest(Request.Method.POST,url,this,this){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();

                params.put("name",name);
                params.put("telephone",telephone);
                params.put("email",email);
                params.put("title",title);
                params.put("type",type);
                params.put("price",price);
                params.put("town",town);
                params.put("district",district);
                params.put("description",description);
                if(i1==true)
                {
                    params.put("photo1", finalImg);
                }
                if(i2==true)
                {
                    params.put("photo2", finalImg1);
                }

                if(i3==true)
                {
                    params.put("photo3", finalImg2);
                }

                if(i3==true)
                {
                    params.put("photo4", finalImg3);
                }
               params.put("date",date);
               params.put("uid",uid);


                return params;
            }
        };
        queue.add(request);


        etname.setText("");
        ettelephone.setText("");
        etemail.setText("");
        ettitle.setText("");
        etprice.setText("");
        ettown.setText("");
        etdescription.setText("");

        Intent intent = new Intent(addnewHouse.this,ProfileActivity.class);
        startActivity(intent);
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