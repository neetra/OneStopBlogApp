package com.example.blogapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SavedFragment extends Fragment {

    View view;
    ImageButton add;
    List<BlogDataModel> blogsData = new ArrayList<BlogDataModel>();;
    RecyclerView blog;
    BlogAdapterSave adapter;
    RequestQueue requestQueue;
    DBHandler dbHandler;
    MaterialToolbar toolbar;

    private String LOG_TAG = "SAVEFRAGMENT" ;
    public SavedFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home2, container, false);
        requestQueue= Volley.newRequestQueue(getContext());


//      Log.i(LOG_TAG, "toolbar");
     toolbar=(MaterialToolbar) view.findViewById(R.id.allblogstoolbar);
//      AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
//
//
//     appCompatActivity.setSupportActionBar(toolbar);
//    appCompatActivity.getSupportActionBar().setTitle("My Saved Blogs");
        toolbar.setTitle("My Saved Blogs");
//
//        Log.i(LOG_TAG, "toolbar");

        add= (ImageButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent i=new Intent(getActivity(),CreatePostActivity.class);
                                       startActivity(i);
                                   }
                               }

        );
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        blog = (RecyclerView) getView().findViewById(R.id.blogsList);
        blog.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new BlogAdapterSave(blogsData, getContext());
        fetchBlogs();
    }

    private void fetchBlogs() {
        dbHandler = new DBHandler(getContext());
        String url = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/blogs";

                List<Model> jsonArray = dbHandler.getAllBlogs();
                try {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Model jsonObject = jsonArray.get(i);
                        String blog_id = jsonObject.blogId;
                        String blog_title = jsonObject.blogTitle;
                        String blog_description = jsonObject.blogDescrition;
                        String blog_image_url = jsonObject.blogImageLink;
                        String blog_thumbnail = jsonObject.blogThumbNailLink;
                        BlogDataModel blogDataModel=new BlogDataModel(blog_id,blog_title,blog_description, blog_image_url, blog_thumbnail, false);
                        blogsData.add(blogDataModel);
                        Log.i("data-->",blog_title);
                    }


                    blog.setAdapter(adapter);

                    //adapter.notifyDataSetChanged();//To prevent app from crashing when updating
                    //UI through background Thread
                } catch (Exception w) {
                    Log.e("error in exception", String.valueOf(w));
                    Log.i("error except","error");
                    // Toast.makeText(MainActivity.this, w.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

    
}