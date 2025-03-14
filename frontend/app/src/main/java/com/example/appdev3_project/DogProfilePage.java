package com.example.appdev3_project;

import com.bumptech.glide.Glide;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.service.AdoptionService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class DogProfilePage extends AppCompatActivity {
    private ImageView dogImage;
    private TextView dogName, dogAge, dogGender, dogVaccination, dogSterilization, dogBio, goAdopt;
    private Dog dog;
    private SharedPreferences sharedPreferences;
    private Long userId;
    private String role;
    private AdoptionService adoptionService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dog_profile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(DogProfilePage.this);

        // Initialize views
        dogImage = findViewById(R.id.dog_image);
        dogName = findViewById(R.id.dog_name);
        dogAge = findViewById(R.id.dog_age);
        dogGender = findViewById(R.id.dog_gender);
        dogVaccination = findViewById(R.id.dog_vaccination);
        dogSterilization = findViewById(R.id.dog_sterilization);
        dogBio = findViewById(R.id.dog_bio);
        goAdopt = findViewById(R.id.go_adopt);

        // initialize services
        adoptionService = new AdoptionService(this);

        // Retrieve user session
        sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);
        role = sharedPreferences.getString("role", null);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dog")) {
            dog = (Dog) intent.getSerializableExtra("dog");

            // Display Dog details
            dogName.setText(dog.getName());
            dogAge.setText("Age: " + dog.getAge() + " years");
            dogGender.setText("Gender: " + dog.getGender());
            dogVaccination.setText("Vaccinated: " + (dog.isVaccinated() ? "Yes" : "No"));
            dogSterilization.setText("Sterilized: " + (dog.isSterilized() ? "Yes" : "No"));
            dogBio.setText(dog.getBio());

            // Load image using Glide
            Glide.with(this)
                    .load(MyUtil.getImgUrl(dog.getImg()))
                    .placeholder(R.drawable.default_dog)
                    .error(R.drawable.default_dog)
                    .into(dogImage);

            // Check adoption if it exists
            if (userId != -1 && "USER".equalsIgnoreCase(role)) {
                checkIfAdoptionExists();
            }

        }

        // Add listener to adopt button
        goAdopt.setOnClickListener(view -> adoptDog());

    }

    private void checkIfAdoptionExists() {
        adoptionService.checkIfAdoptionExists(userId, dog.getId(), new AdoptionService.AdoptionCheckCallback() {
            @Override
            public void onResult(boolean exists) {
                if (exists) {
                    goAdopt.setText("CANCEL APPLICATION");
                    goAdopt.setOnClickListener(view -> deleteAdoption());
                }else{
                    goAdopt.setText("SUBMIT APPLICATION");
                }
            }

            @Override
            public void onError(String errorMessage) {
                goAdopt.setEnabled(true);
                Toast.makeText(DogProfilePage.this, "Failed to check adoption status", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteAdoption(){
        new AlertDialog.Builder(this)
                .setTitle("Cancel Adoption")
                .setMessage("Are you sure you want to cancel your adoption application?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    adoptionService.deleteAdoptionByUserAndDog(userId, dog.getId(), new AdoptionService.AdoptionDeleteCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(DogProfilePage.this, "Adoption application canceled.", Toast.LENGTH_SHORT).show();
                            // redirect to applicant dashboard
                            Intent intent = new Intent(DogProfilePage.this, ApplicantDashboardPage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(DogProfilePage.this, "Failed to cancel application: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void adoptDog() {
        // if not logged in, navigate to sign in page
        if (role == null || userId == -1) {
            Toast.makeText(DogProfilePage.this, "Please sign in to adopt a dog.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DogProfilePage.this, SignInApplicantPage.class));
        }
        // if logged in as user with role ADMIN, send error message that only applicants can adopt
        else if ("ADMIN".equalsIgnoreCase(role)) {
            Toast.makeText(DogProfilePage.this, "Only applicants can adopt a dog.", Toast.LENGTH_SHORT).show();
        }
        // if logged in as user with role USER, navigate to AdoptionApplicationsPage, and pass dog as extra
        else if ("USER".equalsIgnoreCase(role)) {
            Intent intent = new Intent(DogProfilePage.this, AdoptionApplicationsPage.class);
            intent.putExtra("dog", dog);
            startActivity(intent);
        }
    }
}
