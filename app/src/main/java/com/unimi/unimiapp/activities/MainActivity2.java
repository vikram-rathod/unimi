package com.unimi.unimiapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unimi.unimiapp.R;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_main2, null);
        LinearLayout mainLayout = rootView.findViewById(R.id.mainLayout);

        View childView = LayoutInflater.from(this).inflate(R.layout.custum_layout, null);
        LinearLayout linearLayout = childView.findViewById(R.id.linear_layout);

        if (linearLayout.getParent() != null) {
            ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
        }

        mainLayout.addView(linearLayout);

        setContentView(rootView);
    }
}
