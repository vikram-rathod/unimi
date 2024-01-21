package com.unimi.unimiapp.fragments;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.constant.SharedPreference;
import com.unimi.unimiapp.databinding.FragmentBasicDetailsBinding;

public class BasicDetailsFragment extends Fragment {
    FragmentBasicDetailsBinding binding;
    private SharedPreferences sharedPreferences;


    public BasicDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBasicDetailsBinding.inflate(getLayoutInflater(), container, false);

        sharedPreferences =this.getActivity().getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);

        String mobileNumber = sharedPreferences.getString(SharedPreference.MOBILE_NUMBER,"0");
        String username = sharedPreferences.getString(SharedPreference.USERNAME,"0");
        String gender = sharedPreferences.getString(SharedPreference.GENDER,"0");

        Log.d(TAG, "onCreateView: +username"+username);
        Log.d(TAG, "onCreateView: "+gender);
        Log.d(TAG, "onCreateView: +mobilenumber + "+mobileNumber);

       binding.customerMobileNumber.setText(mobileNumber);


        return binding.getRoot();
    }
}