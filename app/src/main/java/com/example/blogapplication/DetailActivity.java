package com.example.blogapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.activity_detail);
        TextView title = findViewById(R.id.title);
        title.setText(bundle.getString("BLOG_TITLE"));
        TextView description = findViewById(R.id.blogContent);
        description.setText(bundle.getString("BLOG_DESCRIPTION"));
        ImageView blogImage = findViewById(R.id.blogImage);
        new RetrieveBitmapFromUrl(blogImage).execute(bundle.getString("BLOG_IMAGE_LINK"));
    }
}