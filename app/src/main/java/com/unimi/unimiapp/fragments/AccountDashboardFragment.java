package com.unimi.unimiapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.unimi.unimiapp.R;
import com.unimi.unimiapp.activities.KycFormActivity;
import com.unimi.unimiapp.constant.SharedPreference;
import com.unimi.unimiapp.databinding.FragmentAccountDashboardBinding;

public class AccountDashboardFragment extends Fragment {
    private SharedPreferences sharedPreferences;

    FragmentAccountDashboardBinding binding;
    Button logout;

    public AccountDashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountDashboardBinding.inflate(getLayoutInflater(), container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);

        binding.profileInfoKyc.setOnClickListener((view)->startActivity(new Intent(getContext(), KycFormActivity.class)));
        binding.logoutBtn.setOnClickListener((log) -> logout());

        return binding.getRoot();
    }
    private void  logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SharedPreference.ISLOGEDIN, false);
        editor.apply();
        replaceFragment(new AccountFragment());
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment);
        fragmentTransaction.commit();
    }
}
