package com.unimi.unimiapp.fragments;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.activities.MainActivity2;
import com.unimi.unimiapp.adapters.CategoriesRecyclerViewAdapter;
import com.unimi.unimiapp.model.CategoryModel;
import com.unimi.unimiapp.model.ProductModel;
import com.unimi.unimiapp.utility.CustomImageSlider;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<CategoryModel> categoryModelList = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

               View view =  inflater.inflate(R.layout.fragment_home, container, false);

        CustomImageSlider customImageSlider = view.findViewById(R.id.customImageSlider);
        RecyclerView categoriesImageRecyclerview = view.findViewById(R.id.categoriesImageRecyclerView);

        //Removable code
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setOnClickListener((views)->{
            startActivity(new Intent(requireContext(), MainActivity2.class));
        });

        List<Integer> imageResIds = new ArrayList<>();
        imageResIds.add(R.drawable.banner_1);
        imageResIds.add(R.drawable.banner_2);

        customImageSlider.setImages(imageResIds);

        categoryModelList.add(new CategoryModel("Milk",R.drawable.b4));
        categoryModelList.add(new CategoryModel("ghee",R.drawable.b5));
        categoryModelList.add(new CategoryModel("eggs",R.drawable.b6));



        categoriesImageRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(categoryModelList,getActivity());
        categoriesImageRecyclerview.setAdapter(adapter);





























               return view;
    }
}