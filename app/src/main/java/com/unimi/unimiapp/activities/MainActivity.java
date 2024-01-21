package com.unimi.unimiapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unimi.unimiapp.fragments.HomeFragment;
import com.unimi.unimiapp.R;
import com.unimi.unimiapp.fragments.AccountFragment;
import com.unimi.unimiapp.fragments.BlogFragment;
import com.unimi.unimiapp.fragments.MoreFragment;
import com.unimi.unimiapp.fragments.SMSFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, new HomeFragment()).commit();
        setupBottomNavigationView();

    }

    private void setupBottomNavigationView() {
        navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (item.getItemId() == R.id.nav_sms) {
                replaceFragment(new SMSFragment());
                return true;
            }else if (item.getItemId() == R.id.nav_account) {
                replaceFragment(new AccountFragment());
                return true;
            }else if (item.getItemId() == R.id.nav_blog) {
                replaceFragment(new BlogFragment());
                return true;
            }else if (item.getItemId() == R.id.nav_more) {
                replaceFragment(new MoreFragment());
                return true;
            }
            return false;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment);
        fragmentTransaction.commit();
    }
}
