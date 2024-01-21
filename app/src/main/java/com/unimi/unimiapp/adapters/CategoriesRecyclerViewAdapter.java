package com.unimi.unimiapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.activities.ProductsActivity;
import com.unimi.unimiapp.model.CategoryModel;

import java.util.List;

public class CategoriesRecyclerViewAdapter extends  RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    List<CategoryModel> categoryModelList;
    Activity activity;

    public CategoriesRecyclerViewAdapter(List<CategoryModel> categoryModelList, Activity activity) {
        this.categoryModelList = categoryModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.categories_image_items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model = categoryModelList.get(position);
//
        holder.categoriesImages.setImageResource(model.getCategoryImage());
        holder.categoriesImages.setOnClickListener((view)->{
            Intent intent = new Intent(activity, ProductsActivity.class);
            intent.putExtra("category_name",model.getCategoryName());
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoriesImages;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesImages = itemView.findViewById(R.id.categoriesImage);
        }
    }
}
