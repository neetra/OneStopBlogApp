package com.example.blogapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.http.HttpClient;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity {
    Button post;
    ImageButton addImg;
    TextInputEditText title;
    TextInputEditText desc;
    File file;
    Bitmap bitmap;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      ActionBar actionBar=getSupportActionBar();
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_create_post);
        post = (Button) findViewById(R.id.post);
        title=(TextInputEditText) findViewById(R.id.title);
        desc=(TextInputEditText) findViewById(R.id.desc);
        addImg = (ImageButton) findViewById(R.id.addImage);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               Log.i("intent", String.valueOf(i));
                 startActivityForResult(i, 3);
//                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(intent.resolveActivity(getPackageManager())!=null){
//                    File photoFile=null;
//                    photoFile=createImageFile();
//                }
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadPost();
               // uploadToS3();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("resultcode", String.valueOf(resultCode));
        if (resultCode == RESULT_OK && data != null) {
            Uri selectedImg = data.getData();
            Log.i("selected", String.valueOf(selectedImg));
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageURI(selectedImg);
            addImg.setVisibility(View.GONE);
            file=new File(selectedImg.getPath());
//            try {
//                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Log.i("file---->", String.valueOf(file));
            //String path = getAbsolutePath(selectedImg);

        }
    }


    private void uploadToS3() {
        String urlString = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/uploadImage?userId="+1;
        URL url= null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection urlConnection=null;
        try {
         urlConnection= url.openConnection();
          urlConnection.setDoInput(true);
          urlConnection.setDoOutput(true);
          if(urlConnection instanceof HttpURLConnection){
              ((HttpURLConnection) urlConnection).setRequestMethod("POST");
              ((HttpURLConnection) urlConnection).connect();
          }
            BufferedOutputStream bos=new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
            int i;
            while((i=bis.read())>0){
                bos.write(i);

            }
            bis.close();
            bos.close();
            Log.i("responsemsg",((HttpURLConnection) urlConnection).getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    private void uploadPost() {
        SharedPreferences sharedPreferences=getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId=sharedPreferences.getString("userId","defaultValue");
        Log.i("shared ",userId);
        String url = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/blog?userId="+userId;
        JSONObject obj = new JSONObject();
        try {
            obj.put("BlogTitle",title );
            obj.put("BlogDescription", desc);
            obj.put("ImageLink","https://one-stop-blogs.s3.us-east-2.amazonaws.com/fZnLfUn9UiuNq3iuPjqnw8_Capture.PNG");
            obj.put("ThumbnailLink","https://one-stop-blogs.s3.us-east-2.amazonaws.com/fZnLfUn9UiuNq3iuPjqnw8_Capture.PNG");



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

                            String access_token = response.getString("Blog_id");
                            Log.i("Blog id", access_token);
                            //if (access_token.length()>0){


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

    public static JSONObject postFile(String url,String filePath,int id){
        String result="";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        File file = new File(filePath);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(file, "image/jpeg");
        StringBody stringBody= null;
        JSONObject responseObject=null;
        try {
            stringBody = new StringBody(id+"");
            mpEntity.addPart("file", cbFile);
            mpEntity.addPart("id",stringBody);
            httpPost.setEntity(mpEntity);
            System.out.println("executing request " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            result=resEntity.toString();
            responseObject=new JSONObject(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseObject;
    }
}