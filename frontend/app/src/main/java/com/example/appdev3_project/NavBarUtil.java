package com.example.appdev3_project;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class NavBarUtil {

    public static void initializeNavBar(Activity activity) {
        TextView appTitle = activity.findViewById(R.id.app_title);
        ImageButton goAdoption = activity.findViewById(R.id.go_adoption);
        ImageButton goProfile = activity.findViewById(R.id.go_profile);

        if (goAdoption == null || goProfile == null) {
            android.util.Log.e("HelperFunctions", "Navbar button(s) not found in " + activity.getLocalClassName());
            return;
        }

        // Set appTitle click listener
        appTitle.setOnClickListener(view -> {
//            Toast.makeText(activity, "Navigating to Home Page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, HomePage.class);
            activity.startActivity(intent);
        });

        // Set goAdoption click listener
        goAdoption.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DogAdoptionPage.class);
            activity.startActivity(intent);
        });

        // Set goProfile click listener
        goProfile.setOnClickListener(v -> {
            Intent intent = new Intent(activity, SignInApplicantPage.class);
            activity.startActivity(intent);
        });
    }

}
