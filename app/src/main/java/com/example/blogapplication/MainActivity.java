package com.example.blogapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email,password;
    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login=(Button) findViewById(R.id.loginBtn);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                // mProgress= (ProgressBar) findViewById(R.id.progressBar);
               //
                // mProgress.show
//                mProgress =new ProgressDialog(MainActivity.this);
//                mProgress.show();
                //mProgress.

                auth=FirebaseAuth.getInstance();
                email=(EditText) findViewById(R.id.emailId);
                password=(EditText) findViewById(R.id.password);
                String emailId=email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                auth.signInWithEmailAndPassword(emailId
                        , pass)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Log.i("suucess","success");
//                                            Toast.makeText(getApplicationContext(),
//                                                    "Login successful!!",
//                                                    Toast.LENGTH_LONG)
//                                                    .show();
                                            callLogin(emailId);


                                            // hide the progress bar
                                            //progressBar.setVisibility(View.GONE);

                                            // if sign-in is successful
                                            // intent to home activity
//                                            Intent intent
//                                                    = new Intent(LoginActivity.this,
//                                                    MainActivity.class);
//                                            startActivity(intent);
                                        }

                                        else {
                                            Log.e("error-->", String.valueOf(task.getException()));

                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                                           Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
              startActivity(intent);

                                            // hide the progress bar
                                            // progressbar.setVisibility(View.GONE);
                                        }
                                    }
                                });




            }
        });
    }
    public void callLogin(String email){
        String url="https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/auth";
        JSONObject obj=new JSONObject();
        try{
            obj.put("username",email);
            obj.put("password","abc");

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
                            String access_token=response.getString("access_token");
                            Log.i("access_token",access_token);
                            //if (access_token.length()>0){
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);

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

}