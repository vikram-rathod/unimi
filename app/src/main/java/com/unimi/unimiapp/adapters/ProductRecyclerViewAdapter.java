package com.unimi.unimiapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.activities.ProductDetailsActivity;
import com.unimi.unimiapp.activities.ProductsActivity;
import com.unimi.unimiapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class ProductRecyclerViewAdapter extends  RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewModel>{


    List<ProductModel> productModelList = new ArrayList<>();
    Activity activity;



    public ProductRecyclerViewAdapter(List<ProductModel> productModelList, Activity activity) {
        this.productModelList = productModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        ProductModel model = productModelList.get(position);

        holder.productName.setText(model.getProductName());
        holder.productPrice.setText(model.getProductPrice());
        holder.itemView.setOnClickListener((view)->{
            activity.startActivity(new Intent(activity, ProductDetailsActivity.class));
        });

    }

    @Override
    public int getItemCount() {
      return  productModelList.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView productName,productPrice;
        public ViewModel(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
