package com.example.blogapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private EditText fname,lname,emailId,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
   Button register = (Button) findViewById(R.id.registerBtn);
      fname = (EditText) findViewById(R.id.fname);
      lname = (EditText) findViewById(R.id.lname);
     emailId = (EditText) findViewById(R.id.emailId);
       password = (EditText) findViewById(R.id.password);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("onclick","onclick");
                String first = fname.toString().trim();
                String last = lname.toString().trim();
                String email = emailId.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Intent intent=new Intent(RegisterActivity.this,InterestActivity.class);
                startActivity(intent);
//                mAuth=FirebaseAuth.getInstance();
//               // Log.i("mAuth",mAuth)
//                mAuth.createUserWithEmailAndPassword(email,pass)
//                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        //Log.i("oncomplete","oncomplete");
//                        if(task.isSuccessful()){
//
//                           // Log.d("Success", String.valueOf(task.getResult()));
//                            //Toast.makeText(getApplicationContext(), "Succesfull", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                           // Log.e("Fail", String.valueOf(task.getException()));
//                            //Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


            }
        });
        }
        public void Register(){

        }

}