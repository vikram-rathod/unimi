package com.unimi.unimiapp.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.unimi.unimiapp.R;
import com.unimi.unimiapp.constant.SharedPreference;
import com.unimi.unimiapp.databinding.ActivityKycFormBinding;

import org.w3c.dom.Text;

import java.util.Arrays;

public class KycFormActivity extends AppCompatActivity {
    ActivityKycFormBinding binding;
    private int currentStep = 0;
    private View[] steps;
    private CardView[] stepIndicators;
    private boolean[] stepCompleted;
    private static final String[] STEP_TITLES = {"Basic info", "Address info", "Other info", "Identity info"};
    private static final int TEXT_SIZE_SP = 16;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKycFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        steps = new View[]{
                LayoutInflater.from(this).inflate(R.layout.fragment_basic_details, binding.containerLayout, false),
                LayoutInflater.from(this).inflate(R.layout.fragment_address_details, binding.containerLayout, false),
                LayoutInflater.from(this).inflate(R.layout.fragment_others_details_kyc, binding.containerLayout, false),
                LayoutInflater.from(this).inflate(R.layout.fragment_identity_details, binding.containerLayout, false),
        };

        sharedPreferences =getSharedPreferences(SharedPreference.PREF_NAME, Context.MODE_PRIVATE);


        stepCompleted = new boolean[steps.length];
        Arrays.fill(stepCompleted, false);
        initializeStepIndicators();
        showCurrentStep();

        String mobileNumber = sharedPreferences.getString(SharedPreference.MOBILE_NUMBER,"0");
        String username = sharedPreferences.getString(SharedPreference.USERNAME,"0");
        String gender = sharedPreferences.getString(SharedPreference.GENDER,"0");

        Log.d(TAG, "onCreateView: +username"+username);
        Log.d(TAG, "onCreateView: "+gender);
        Log.d(TAG, "onCreateView: +mobilenumber + "+mobileNumber);

        // TODO: 1/21/2024 wrong username and gender is comming solve that issue
        TextInputEditText mobileNumberEdit =  steps[0].findViewById(R.id.customerMobileNumber);
        TextInputEditText name = steps[0].findViewById(R.id.customerName);
        TextView customerGender = steps[0].findViewById(R.id.customerGender);
        mobileNumberEdit.setText(mobileNumber);
        name.setText(username);
        customerGender.setText(gender);
        Log.d(TAG, "onCreateView: +mobile number inside currentStep+ "+mobileNumber);

        binding.previousButton.setOnClickListener(v -> {
            if (currentStep > 0) {
                currentStep--;
                onStepIncomplete();
                showCurrentStep();
            }
        });
        binding.nextButton.setOnClickListener(v -> {

            if (currentStep < steps.length) {
                onStepCompleted();
                currentStep++;
                showCurrentStep();
            } else {
                // When Last step reached submit the form
//                    submitForm();
            }
        });

    }

    private void showCurrentStep() {
        binding.containerLayout.removeAllViews();
        binding.containerLayout.addView(steps[currentStep]);
        binding.previousButton.setVisibility(currentStep > 0 ? View.VISIBLE : View.INVISIBLE);
        binding.nextButton.setText(currentStep < steps.length - 1 ? "Next" : "Submit");
        updateStepIndicators();
    }

    private void updateStepIndicators() {
        for (int i = 0; i < stepIndicators.length; i++) {


            if (i == currentStep) {
                stepIndicators[i].setCardBackgroundColor(getResources().getColor(R.color.selected_step_color));
            } else {
                if(stepCompleted[i]){
                    int completedColor = getResources().getColor(R.color.completed_step_color);
                    stepIndicators[i].setCardBackgroundColor(completedColor);
                }else{
                    int defaultColor = getResources().getColor(R.color.default_step_color);
                    stepIndicators[i].setCardBackgroundColor(defaultColor);
                }
            }

            if (i < steps.length - 1) {
                animateStepIn(steps[currentStep]);
            }
        }
    }

    private void onStepCompleted() {
        stepCompleted[currentStep] = true;
        updateStepIndicators();
    }
    private void onStepIncomplete() {
        stepCompleted[currentStep] = false;
        updateStepIndicators();
    }
    private void animateStepIn(View stepView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(stepView, "translationX", binding.containerLayout.getHeight(), 0);
        animator.setDuration(800);
        animator.start();
    }


    private void initializeStepIndicators() {
        stepIndicators = new CardView[steps.length];
        for (int i = 0; i < steps.length; i++) {
            CardView stepCard = createStepCard(STEP_TITLES[i]);
            binding.stepIndicatorsLayout.addView(stepCard);

            stepIndicators[i] = stepCard;

            if (i >0) {
                animateStepIn(binding.stepIndicatorsLayout);
            }
        }
    }

    private CardView createStepCard(String title) {
        CardView stepCard = new CardView(this);
        stepCard.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        stepCard.setRadius(getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
        stepCard.setCardElevation(8);
        cardLayoutParams.setMargins(10, 0, 10, 0);
        stepCard.setLayoutParams(cardLayoutParams);

        TextView stepTitle = new TextView(this);
        stepTitle.setText(title);
        stepTitle.setTextColor(Color.BLACK);
        stepTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_SP);
        stepTitle.setPadding(16, 20, 16, 20);
        stepCard.addView(stepTitle);

        return stepCard;
    }


}