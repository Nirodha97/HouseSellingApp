package com.nirodha.houseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
//implements CallBackItemTouch
    String email,uid;
    RecyclerView recyclerView;
    LinearLayout layout;
    Adapter adapter;
    List<Model> List;
    LinearLayoutManager linearLayoutManager;
    TextView profile_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recycle2);
        layout=findViewById(R.id.lid);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        List = new ArrayList<>();
        adapter = new Adapter(this, List);
        recyclerView.setAdapter(adapter);
//        {
//           @NonNull
//            @Override
//            public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//       };

//        ItemTouchHelper.Callback callback = new MyItemTouchHelerCallBack(this);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(recyclerView);


///bottom navigation
        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.setting:
                        Toast.makeText(ProfileActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                        setting();
                        break;
                    case R.id.home2:
                        Toast.makeText(ProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        home();
                        break;
                    case R.id.addnew2:
                        Toast.makeText(ProfileActivity.this, "New", Toast.LENGTH_SHORT).show();
                        addnew();
                        break;

                }


                return false;
            }
        });


        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userdata= sessionManager.getUserData();

        String f= userdata.get(SessionManager.KEY_FIRSTNAME);
        String l= userdata.get(SessionManager.KEY_LASTNAME);
        email = userdata.get(SessionManager.KEY_EMAIL);
        uid = userdata.get(SessionManager.KEY_ID);
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();


        profile_name= findViewById(R.id.profile_name);
        String fn = getIntent().getStringExtra("fn");
        String ln = getIntent().getStringExtra("ln");

        profile_name.setText(f+" "+l);

        loadhouseslist();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadhouseslist();
    }

    public void loadhouseslist()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://beezzserver.com/nirodha/sweethomes/register/houselist.php?userid="+uid;

        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        sethouselist(response);
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
    public void sethouselist(JSONArray response)
    {

        try {

            for(int i=0;i<response.length();i++)
            {
                JSONObject obj = response.getJSONObject(i);
                Model model = new Model();

                model.setId(obj.getString("id"));
                model.setTitle(obj.getString("title"));
                model.setTown(obj.getString("town"));
                model.setTown(obj.getString("district"));
                model.setDate(obj.getString("date"));
                List.add(model);
                adapter.notifyDataSetChanged();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }




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

    public void setting()
    {
        Intent i = new Intent(this,settingActivity.class);
        startActivity(i);
    }

    public void logout(View view)
    {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.logoutUser();
        Intent i = new Intent(this,dashboard.class);
        startActivity(i);
    }

//    @Override
//    public void itemTouchOnMove(int oldPosition, int newPosition) {
//        List.add(newPosition,List.remove(oldPosition));
//        adapter.notifyItemMoved(oldPosition,newPosition);
//    }
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
//        String name = List.get(viewHolder.getAdapterPosition()).getTitle();
//
//       // final Item deletedItem = List.get(viewHolder.getAdapterPosition());
//        final Item deletedItem = (Item) List.get(viewHolder.getAdapterPosition());
//        final int deletedIndex = viewHolder.getAdapterPosition();
//
//        adapter.removeItem(viewHolder.getAdapterPosition());
//
//        Snackbar snackbar = Snackbar.make(layout,name+"Removed...!",Snackbar.LENGTH_SHORT);
//        snackbar.setAction("UNDO", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.restoreItem(deletedItem,deletedIndex);
//            }
//        });
//
//        snackbar.setActionTextColor(Color.GREEN);
//        snackbar.show();
//    }
}