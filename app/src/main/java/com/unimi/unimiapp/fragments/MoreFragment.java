package com.unimi.unimiapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unimi.unimiapp.R;


public class MoreFragment extends Fragment {
    String[] options = {"Blog","Education","Request us","Find Service Area","Find Retailer","Find Depot","About Us","Contact Us","Privacy Policy","Term and condition","Be out Partner"};

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more, container, false);

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options);
        ListView listView = (ListView) view.findViewById(R.id.optionDetailslistView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
}