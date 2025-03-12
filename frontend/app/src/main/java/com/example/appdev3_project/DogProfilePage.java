package com.example.appdev3_project;

import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.retrofit.AdoptionApi;
import com.example.appdev3_project.retrofit.RetrofitService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogProfilePage extends AppCompatActivity {
    private ImageView dogImage;
    private TextView dogName, dogAge, dogGender, dogVaccination, dogSterilization, dogBio, goAdopt;
    private Dog dog;


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

            // Set image
            dogImage.setImageResource(dog.getImageResId());

            checkIfAdoptionExists();

        }

        goAdopt.setOnClickListener(view -> adoptDog());

    }

    private void checkIfAdoptionExists() {

        // retrieve session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        Long userId = sharedPreferences.getLong("userId", -1);

        // initialize retrofitService
        RetrofitService retrofitService = new RetrofitService();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);

        // start api call
        Call<Boolean> call = adoptionApi.checkAdoptionExists(userId, dog.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean exists = response.body();
                    if (exists) {
                        goAdopt.setEnabled(false);
                        goAdopt.setText("Application Submitted");
                    } else {
                        goAdopt.setEnabled(true);
                    }
                } else {
                    goAdopt.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                goAdopt.setEnabled(true);
                Toast.makeText(DogProfilePage.this, "Failed to check adoption status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adoptDog() {

        // retrieve session data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", null);
        Long userId = sharedPreferences.getLong("userId", -1);

        // if not logged in, navigate to sign in page
        if (role == null || userId == -1) {
            Toast.makeText(DogProfilePage.this, "Please sign in to adopt a dog.", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(DogProfilePage.this, SignInApplicantPage.class);
            startActivity(intent2);
        }
        // if logged in as user with role ADMIN, send error message that only applicants can adopt
        else if ("ADMIN".equalsIgnoreCase(role)) {
            Toast.makeText(DogProfilePage.this, "Only applicants can adopt a dog.", Toast.LENGTH_SHORT).show();
        }
        // if logged in as user with role USER, navigate to AdoptionApplicationsPage, and pass dog as extra
        else if ("USER".equalsIgnoreCase(role)) {
            Intent intent3 = new Intent(DogProfilePage.this, AdoptionApplicationsPage.class);
            intent3.putExtra("dog", dog);
            startActivity(intent3);
        }
    }
}
