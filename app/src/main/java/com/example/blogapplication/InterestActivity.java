package com.example.blogapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InterestActivity extends AppCompatActivity {
    List<InterestSataModel> interestData;
    InterestAdapter adapter;
RequestQueue requestQueue;
RecyclerView interest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
         interest = (RecyclerView) findViewById(R.id.interestList);
        requestQueue= Volley.newRequestQueue(this);
        interestData=new ArrayList<>();
//        interestData.add();
//        interestData.add(new InterestSataModel(25,"women"));
        //fetchInterests();
       // interest.setLayoutManager(new GridLayoutManager(this,2));
        interest.setLayoutManager(new LinearLayoutManager(this));
    fetchInterests();
        String[] lang = {"Java", "Python", "php"};



        //interest.setAdapter((new InterestAdapter(lang)));

    }

    private void fetchInterests() {
        String url = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/tags";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String tag_name = jsonObject.getString("tag_name");
                        Integer tag_id = jsonObject.getInt("tag_id");
                        //alist.add(new Albums(albumname, albumimageurl));
                        InterestSataModel interestSataModel=new InterestSataModel(tag_id,tag_name);
                        interestData.add(interestSataModel);
                        Log.i("data-->",tag_name);
                    }

                    adapter=new InterestAdapter(interestData);
                    interest.setAdapter(adapter);

                    //adapter.notifyDataSetChanged();//To prevent app from crashing when updating
                    //UI through background Thread
                } catch (Exception w) {
                    Log.e("error in exception", String.valueOf(w));
                    Log.i("error except","error");
                   // Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error voley", String.valueOf(error));
              //  Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}