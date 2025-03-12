package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;
import com.example.appdev3_project.service.AdoptionService;
import com.example.appdev3_project.service.UserService;


public class AdoptionApplicationsPage extends AppCompatActivity {

    private User user;
    private Dog dog;
    private UserService userService;
    private AdoptionService adoptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.adoption_applications_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adoption_applications), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdoptionApplicationsPage.this);

        // Get dog data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dog")) {
            dog = (Dog) intent.getSerializableExtra("dog");
            ((TextView) findViewById(R.id.application_dog_name)).setText(dog.getName());
        }

        // Get user data from database
        userService = new UserService(this);
        userService.fetchUserDetails(new UserService.UserFetchCallback() {
            @Override
            public void onSuccess(User newUser) {
                user = newUser;
                ((TextView) findViewById(R.id.application_name)).setText("Name: " + user.getName());
                ((TextView) findViewById(R.id.application_email)).setText("Email: " + user.getUsername());
                ((TextView) findViewById(R.id.application_phone)).setText("Phone: " + user.getContact());
                ((TextView) findViewById(R.id.application_address)).setText("Address: " + user.getAddress());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AdoptionApplicationsPage.this, "User not found!", Toast.LENGTH_SHORT).show();
            }
        });

        // Submit adoption request
        Button submitButton = findViewById(R.id.btn_application_submit);
        submitButton.setOnClickListener(view -> {
            adoptionService = new AdoptionService(this);
            adoptionService.submitAdoption(user.getId(), dog.getId(), new AdoptionService.AdoptionSubmitCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(AdoptionApplicationsPage.this, "Adoption application submitted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdoptionApplicationsPage.this, ApplicantDashboardPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(String errorMessage) {
                    Log.e("AdoptionApp", "Network request failed: " + errorMessage);
                    Toast.makeText(AdoptionApplicationsPage.this, "Network error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

}