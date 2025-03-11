package com.example.appdev3_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HelperFunctions {

    public static void initializeNavBar(Activity activity) {
        TextView appTitle = activity.findViewById(R.id.app_title);
        ImageButton goAdoption = activity.findViewById(R.id.go_adoption);
        ImageButton goProfile = activity.findViewById(R.id.go_profile);

        if (goAdoption == null || goProfile == null) {
            return; // Prevents crashes if buttons are missing
        }

        // Set appTitle click listener
        appTitle.setOnClickListener(view -> {
            Toast.makeText(activity, "Navigating to Home Page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, HomePage.class);
            activity.startActivity(intent);
        });

        // Set goAdoption click listener
        goAdoption.setOnClickListener(v -> {
            Toast.makeText(activity, "Navigating to Adoption Page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, DogAdoptionPage.class);
            activity.startActivity(intent);
        });

        // Set goProfile click listener
        goProfile.setOnClickListener(v -> {
            Toast.makeText(activity, "Navigating to Profile Page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, ApplicantDashboardPage.class);
            activity.startActivity(intent);
        });
    }

}
