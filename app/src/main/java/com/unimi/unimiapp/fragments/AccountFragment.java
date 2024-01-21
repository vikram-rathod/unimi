package com.unimi.unimiapp.fragments;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.unimi.unimiapp.R;
import com.unimi.unimiapp.constant.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    MaterialButtonToggleGroup authToggle;
    CardView signUpCardview,signInCardView;
    SharedPreference sharedPreference;
    private SharedPreferences sharedPreferences;
    TextInputEditText userSignUp,userPassSignUp,userPassLogin,mobileSignUp,mobileSignIn,usernameSignUp;
    Button registerBtn ,loginBtn;
    RadioGroup radioGroup ;
    boolean isLoggedIn;
    RadioButton selectedGender;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        viewIds(view);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selected=group.getCheckedRadioButtonId();
            selectedGender= view.findViewById(selected);

        });
        sharedPreferences =getContext().getSharedPreferences(sharedPreference.PREF_NAME, getContext().MODE_PRIVATE);


        int selectedColor = ContextCompat.getColor(getContext(), R.color.black);
        int defaultColor = ContextCompat.getColor(getContext(), R.color.selected_step_color);

        authToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            MaterialButton selectedButton = view.findViewById(checkedId);
            selectedButton.setTextColor(getResources().getColor(R.color.black));

            for (int i = 0; i < group.getChildCount(); i++) {
                MaterialButton button = (MaterialButton) group.getChildAt(i);
                button.setBackgroundColor(defaultColor);
                button.setTextColor(getResources().getColor(R.color.white));
            }

            selectedButton.setBackgroundColor(selectedColor);

            if (checkedId == R.id.sign_up_toggle_btn) {
                signUpCardview.setVisibility(View.VISIBLE);
                signInCardView.setVisibility(View.GONE);
            } else {
                signUpCardview.setVisibility(View.GONE);
                signInCardView.setVisibility(View.VISIBLE);
            }
        });



        registerBtn.setOnClickListener((reg)->{
            String customerEmail = "NA";
            String customerName = usernameSignUp.getText().toString();
            String customerMobileNumberSignUp = mobileSignUp.getText().toString();
            String customerPasswordSignUp = userPassSignUp.getText().toString();
            registerUser(customerEmail,customerName,customerMobileNumberSignUp,customerPasswordSignUp,selectedGender);
        });
        isLoggedIn = sharedPreferences.getBoolean(SharedPreference.ISLOGEDIN,false);
        loginBtn.setOnClickListener((login)->{
            String customerMobileNumberSignIn = mobileSignIn.getText().toString();
            String customerPasswordSignIn = userPassLogin.getText().toString();
            Log.d(TAG, "onCreateView: "+customerMobileNumberSignIn+customerPasswordSignIn);
            authenticateUser(customerMobileNumberSignIn,customerPasswordSignIn);
        });
        if(isLoggedIn){
            Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
            replaceFragment(new AccountDashboardFragment());
        }
        return view;
    }

    private void authenticateUser(String customerMobileNumber,String customerPassword) {
        String url = "https://1052533.in/unimiapp/ecomdash/apis/userlogin.php";
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("status");
                String message = jsonObject.getString("message");
                if (status){
                    Toast.makeText(requireContext(), "Login Successfully...!", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(SharedPreference.ISLOGEDIN,true);
                    editor.putString(SharedPreference.MOBILE_NUMBER,customerMobileNumber);
                    editor.apply();
                    replaceFragment(new AccountDashboardFragment());

                }else{
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage())){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cust_mobile",customerMobileNumber);
                params.put("cust_password",customerPassword);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(loginRequest);
    }

    private void registerUser(String customerEmail, String customerName, String customerMobileNumber, String customerPassword, RadioButton selectedGender) {


        // Validate input fields
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(getContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(customerMobileNumber)) {
            Toast.makeText(getContext(), "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(customerPassword)) {
            Toast.makeText(getContext(), "Please enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://1052533.in/unimiapp/ecomdash/apis/user_create_account.php";

        StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    if (status) {
                        Toast.makeText(getContext(), "Registration Successfully...", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPreference.EMAIL, customerEmail);
                        editor.putString(SharedPreference.USERNAME, customerName);
                        editor.putString(SharedPreference.MOBILE_NUMBER, customerMobileNumber);
                        editor.putString(SharedPreference.PASSWORD, customerPassword);
                        editor.putString(SharedPreference.GENDER, String.valueOf(AccountFragment.this.selectedGender.getText()));
                        editor.apply();
                        signUpCardview.setVisibility(View.GONE);
                        signInCardView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getContext(), "This Mobile No. Already Exists, Please try Logging In..!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:" + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();

                param.put("cust_name", customerName);
                param.put("cust_gender", String.valueOf(selectedGender.getText()));
                param.put("cust_mobile", customerMobileNumber);
                param.put("cust_email", customerEmail);
                param.put("cust_password", customerPassword);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(registerRequest);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment);
        fragmentTransaction.commit();
    }
    private void viewIds(@NonNull View view) {
        authToggle = view.findViewById(R.id.auth_toggle);
        signInCardView = view.findViewById(R.id.show_sign_in_card);
        signUpCardview = view.findViewById(R.id.show_sign_up_card);

        userSignUp = view.findViewById(R.id.userpass_signup);
        userPassLogin = view.findViewById(R.id.userpass_login);
        userPassSignUp = view.findViewById(R.id.userpass_signup);
        mobileSignUp = view.findViewById(R.id.user_mobile_number_signUp);
        mobileSignIn = view.findViewById(R.id.user_mobile_number_signIn);
        usernameSignUp = view.findViewById(R.id.user_name);

        registerBtn = view.findViewById(R.id.register_btn);
        loginBtn = view.findViewById(R.id.login_btn);
        radioGroup = view.findViewById(R.id.genderButtonGroup);

    }
}
