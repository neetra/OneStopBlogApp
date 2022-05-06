package com.example.blogapplication;

import static com.android.volley.VolleyLog.e;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InterestActivity extends AppCompatActivity {
    List<InterestSataModel> interestData;
    InterestAdapter adapter;
RequestQueue requestQueue;
RecyclerView interest;
Button submit;
FirebaseAuth mAuth;

ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        mAuth=FirebaseAuth.getInstance();
        Intent i=getIntent();
        String fname=i.getStringExtra("fname");
       // Log.i("fname-->",fname);
        String lname=i.getStringExtra("lname");
        String email=i.getStringExtra("emailId");
        String pass=i.getStringExtra("password");
         interest = (RecyclerView) findViewById(R.id.interestList);
        requestQueue= Volley.newRequestQueue(this);
        interestData=new ArrayList<>();
//        interestData.add();
//        interestData.add(new InterestSataModel(25,"women"));
        //fetchInterests();
       // interest.setLayoutManager(new GridLayoutManager(this,2));
        interest.setLayoutManager(new LinearLayoutManager(this));
        adapter=new InterestAdapter(interestData);
    fetchInterests();
        String[] lang = {"Java", "Python", "php"};



        //interest.setAdapter((new InterestAdapter(lang)));
        submit=(Button) findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             //Log.i("sizee->", String.valueOf(adapter.getSelected().size()));
             //adapter.getSelected();


//addInterests("JKJHJKN");

            createUserinFirebase(fname,lname,email,pass);
            //Intent intent=new Intent(InterestActivity.this,HomeActivity.class);
            }
        });

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
    private void createUserinFirebase(String fname,String lname,String email,String pass){
//        String userEmail=email.getText().toString().trim();
//        Log.e("email",userEmail.toString());
//        String userPass=password.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userId=mAuth.getCurrentUser().getUid();
                    String userEmail=mAuth.getCurrentUser().getEmail();
                    Log.i("current userId",userId);
                    Log.i("current email",userEmail);
                    Log.d("Success", String.valueOf(task.getResult().getAdditionalUserInfo()));
                    Toast.makeText(InterestActivity.this, "Succesfull", Toast.LENGTH_SHORT).show();
                createUser(email,fname,lname,userId);
                }
                else{
                    Log.e("Fail", String.valueOf(task.getException()));
                    Toast.makeText(InterestActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void createUser(String email,String fname,String lname,String userId){
        String url="https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/user";
        JSONObject obj=new JSONObject();
        try{
            obj.put("UserId",userId);

            obj.put("EmailId",email);
            obj.put("FName",fname);
            obj.put("LName",lname);
            obj.put("Type",0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(MainActivity.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            Log.d("JSON reponse", String.valueOf(response));
                            // loading.dismiss();
                            //String Error = response.getString("httpStatus");
                            String user_id=response.getString("userId");
                            //Log.i("access_token",access_token);
                            //if (access_token.length()>0){
                            //Intent intent=new Intent(MainActivity.this,InterestActivity.class);
                            //startActivity(intent);
                            addInterests(user_id);

                            // }
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // loading.dismiss();
                        }
//                        resultTextView.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //loading.dismiss();
                Toast.makeText(getApplicationContext(), "User could not login", Toast.LENGTH_SHORT).show();
                VolleyLog.d("Error", "Error: " + error.getMessage());
                //Toast.makeText(Login_screen.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }
    public void addInterests(String userId){
        String inturl="https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/usertags?userId="+userId;
        JSONObject json=new JSONObject();
        ArrayList<InterestSataModel> selectedItems=new ArrayList<>();
        selectedItems=adapter.getSelected();
        JSONArray array=new JSONArray();

        Log.i("selected from fn-->", String.valueOf(selectedItems));

       for(int i=0;i<selectedItems.size();i++){
          // list.add(selectedItems.get(i).getTagName());
           array.put(selectedItems.get(i).getTagName());

       }
       String [] arr={"Advertising","2016 Election"};
       JSONArray a=new JSONArray();
        try{

            json.put("tags",array);


        } catch (JSONException e) {
            Log.e("exception in register", String.valueOf(e));
        }

        a.put(json);
        Log.i("object", String.valueOf(json));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, inturl, json,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(MainActivity.this,"String Response : "+ response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            Log.d("reponse interestsa dd", String.valueOf(response));
                            // loading.dismiss();
                            //String Error = response.getString("httpStatus");
                            // String user_id=response[0];
                            //Log.i("access_token",access_token);
                            //if (access_token.length()>0){
                            //Intent intent=new Intent(MainActivity.this,InterestActivity.class);
                            //startActivity(intent);
                            //addInterests(user_id);


                            // }
                            Toast.makeText(InterestActivity.this,"User resgistartion successful",Toast.LENGTH_LONG);
                            Intent i=new Intent(InterestActivity.this,MainActivity.class);
                            startActivity(i);























                        } finally {
                            Log.i("finall","Executed");
                        }
//                        resultTextView.setText("String Response : "+ response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //loading.dismiss();
                Toast.makeText(getApplicationContext(), "User registartion failed", Toast.LENGTH_SHORT).show();
                //e("volley error int",error.printStackTrace());
                //Toast.makeText(Login_screen.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("volley error", String.valueOf(error.getMessage()));
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);




    }

}