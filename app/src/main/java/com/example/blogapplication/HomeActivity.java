package com.example.blogapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.blogapplication.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        binding.navView.setOnNavigationItemSelectedListener(item ->{
            switch(item.getItemId()){
                case R.id.navigation_home :
                    replaceFragment(new HomeFragment());

                    break;
                case R.id.navigation_dashboard :
                    replaceFragment( new SavedFragment());
                    break;
                case R.id.navigation_notifications :
                    replaceFragment( new ProfileFragment());
                    break;
            }
            return true;


        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
fragmentTransaction.replace(R.id.fragmentLayout,fragment);
fragmentTransaction.commit();
    }

}