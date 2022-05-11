package com.example.blogapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBlogsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBlogsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    View view;
    ImageButton add;
    List<BlogDataModel> blogsData = new ArrayList<BlogDataModel>();;
    RecyclerView blog;
    BlogAdapter adapter;
    RequestQueue requestQueue;
    MaterialToolbar toolbar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyBlogsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBlogsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBlogsFragment newInstance(String param1, String param2) {
        MyBlogsFragment fragment = new MyBlogsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_blogs, container, false);
        requestQueue= Volley.newRequestQueue(getContext());
        toolbar=(MaterialToolbar) view.findViewById(R.id.myblogstoolbar);
        toolbar.setTitle("My Blogs");
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

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        blog = (RecyclerView) getView().findViewById(R.id.blogsList);
        blog.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new BlogAdapter(blogsData, getContext());
        fetchBlogs();
    }

    private void fetchBlogs() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId","defaultValue");
        String url = "https://z2gennof6g.execute-api.us-east-2.amazonaws.com/dev/blogs?userId=" + userId;
        DBHandler dbHandler = new DBHandler(getContext());
        List<Model> allBlogs = dbHandler.getAllBlogs();
        Log.e("MyBlogsfragement", "fetch blogs");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray jsonArray = response;
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String blog_id = jsonObject.getString("Blog_id");
                        String blog_title = jsonObject.getString("blog_title");
                        String blog_description = jsonObject.getString("blog_description");
                        String blog_image_url = jsonObject.getString("image_link");
                        String blog_thumbnail = jsonObject.getString("Thumbnail");
                        boolean isSaved = false;
                        Log.e("Homefragement","blog id" + blog_id );
                        Log.e("Homefragement", String.valueOf(allBlogs.size()));

                        isSaved= allBlogs.stream().anyMatch(n -> n.blogId.equalsIgnoreCase(blog_id));
                        Log.e("Homefragement", String.valueOf((isSaved) + blog_id));
                        BlogDataModel blogDataModel=new BlogDataModel(blog_id,blog_title,blog_description, blog_image_url, blog_thumbnail, isSaved);
                        blogsData.add(blogDataModel);
//                        Log.i("data-->",blog_title);
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