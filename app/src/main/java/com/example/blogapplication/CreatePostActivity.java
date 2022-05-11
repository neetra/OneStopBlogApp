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
<<<<<<< Updated upstream
=======
            filepath=selectedImg.getPath();
            Log.i("fileinputstreafilepath", filepath);
>>>>>>> Stashed changes
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


<<<<<<< Updated upstream
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
=======
    private int uploadToS3() {
        Log.i("upload s3","upload s3"+filepath);
        String urlString = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/uploadImage?userId=" + 1;
        String filename = filepath;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String path = Environment.getExternalStorageDirectory().getPath();
Log.i("environment path",path);


       //File sourceFile = new File("/storage/emulated/0/Download/badge.png");

        File sourceFile=new File(filepath);
        Log.i("sourcefile",sourceFile.getPath()+":"+sourceFile.isFile()+":"+sourceFile.getPath());
        if (!sourceFile.isFile()) {
            Log.i("first if","first if");
            dialog.dismiss();

            return 0;
        } else {
            Log.i("else","else");
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
               Log.i("fileinputstream", String.valueOf(fileInputStream));
                URL url = new URL(urlString);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                //conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                Log.i("fileinputstream77777", sourceFile.getPath());
                conn.setRequestProperty("file", sourceFile.getPath() + ".png");
                dos=new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens+boundary+lineEnd);
                dos.writeBytes("Content-disposition: form-data; name=\"file\";filename=\""+"badge.png"+"\""+lineEnd);
                dos.writeBytes(lineEnd);
                bytesAvailable=fileInputStream.available();
                bufferSize=Math.min(bytesAvailable,maxBufferSize);
                buffer=new byte[bufferSize];
                bytesRead=fileInputStream.read(buffer,0,bufferSize);
                while (bytesRead>0){
                    dos.write(buffer,0,bufferSize);
                    bytesAvailable=fileInputStream.available();
                    bufferSize=Math.min(bytesAvailable,maxBufferSize);
                    bytesRead=fileInputStream.read(buffer,0,bufferSize);
                }
                Log.i("bytes Read","bytes Read");
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens+boundary+twoHyphens+lineEnd);
                serverResponseCode=conn.getResponseCode();
                String serverMsg=conn.getResponseMessage();
                Log.i("code",serverMsg+":"+serverResponseCode);
                if(serverResponseCode==200){
                    Log.i("Succesful uploading","success upload");
                }
                fileInputStream.close();
                dos.flush();
                dos.close();

>>>>>>> Stashed changes

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
}