package com.example.blogapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogapplication.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    Button btn1;
    Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUser();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        SharedPreferences sharedPreferences=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("userId","defaultValue");
        Log.i("shared ",userId);
        binding.navView.setOnNavigationItemSelectedListener(item ->{
            switch(item.getItemId()){
                case R.id.navigation_home :
                    replaceFragment(new HomeFragment());

                    break;
                case R.id.navigation_dashboard :
                    replaceFragment( new SavedFragment());
                    break;
                case R.id.navigation_notifications :
                    replaceFragment( new ProfileFragment());
                    break;
            }
            return true;


        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
fragmentTransaction.replace(R.id.fragmentLayout,fragment);
fragmentTransaction.commit();
    }


    public void getUser(){
        SharedPreferences sp= getSharedPreferences("userInfo",MODE_PRIVATE);
      String userId=  sp.getString("userId","default");
      Log.i("userId",userId);
        String url = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/user/"+userId;
        StringRequest myRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try{
                        //Create a JSON object containing information from the API.
                        JSONObject myJsonObject = new JSONObject(response);
                        Log.i("json", String.valueOf(myJsonObject));
                      String fname=  myJsonObject.getString("firstName");
                     String lname=   myJsonObject.getString("lastName");
                       String fullname=fname+" "+lname;
                       // SharedPreferences sp= getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("fullname",fullname);

                        editor.commit();
//                        totalCasesWorld.setText(myJsonObject.getString("cases"));
//                        totalRecoveredWorld.setText(myJsonObject.getString("recovered"));
//                        totalDeathsWorld.setText(myJsonObject.getString("deaths"));
                    } catch ( JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(HomeActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);

    }





}