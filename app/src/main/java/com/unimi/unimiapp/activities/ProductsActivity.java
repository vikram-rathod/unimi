package com.unimi.unimiapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.adapters.ProductRecyclerViewAdapter;
import com.unimi.unimiapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView ProductRecyclerView;
    List<ProductModel> productModelList = new ArrayList<>();
    Button productDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_products);
        ProductRecyclerView = findViewById(R.id.categoriesWiseProductRecyclerView);
        productDetails = findViewById(R.id.productDetails);

        String categoryName = getIntent().getStringExtra("category_name");

        productModelList.add(new ProductModel("1","milk one","521","2"));
        productModelList.add(new ProductModel("1","egg one","457","8"));
        productModelList.add(new ProductModel("1","ghee one","127","8"));
        productModelList.add(new ProductModel("1","milk two","785","1"));




//        productDetails.setOnClickListener((view)->{
//            startActivity(new Intent(this,ProductDetailsActivity.class));
//        });

        ProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductRecyclerViewAdapter adapter = new ProductRecyclerViewAdapter(productModelList,this);
        ProductRecyclerView.setAdapter(adapter);



    }
}