package com.example.blogapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class InterestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        RecyclerView interest=(RecyclerView) findViewById(R.id.interestList);
        interest.setLayoutManager(new GridLayoutManager(this,2));
        String[] lang={"Java","Python","php"};
        interest.setAdapter((new InterestAdapter(lang)));

    }
}