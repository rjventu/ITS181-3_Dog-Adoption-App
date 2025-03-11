package com.example.appdev3_project;

import com.example.appdev3_project.model.Dog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DogProfilePage extends AppCompatActivity {
    private ImageView dogImage;
    private TextView dogName, dogAge, dogGender, dogVaccination, dogSterilization, dogBio;

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
        NavBarUtil.initializeNavBar(DogProfilePage.this);

        // Initialize views
        dogImage = findViewById(R.id.dog_image);
        dogName = findViewById(R.id.dog_name);
        dogAge = findViewById(R.id.dog_age);
        dogGender = findViewById(R.id.dog_gender);
        dogVaccination = findViewById(R.id.dog_vaccination);
        dogSterilization = findViewById(R.id.dog_sterilization);
        dogBio = findViewById(R.id.dog_bio);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dog")) {
            Dog dog = (Dog) intent.getSerializableExtra("dog");

            // Display Dog details
            dogName.setText(dog.getName());
            dogAge.setText("Age: " + dog.getAge() + " years");
            dogGender.setText("Gender: " + dog.getGender());
            dogVaccination.setText("Vaccinated: " + (dog.isVaccinated() ? "Yes" : "No"));
            dogSterilization.setText("Sterilized: " + (dog.isSterilized() ? "Yes" : "No"));
            dogBio.setText(dog.getBio());

            // Set image
            dogImage.setImageResource(dog.getImageResId());
        }

        // Configure adopt button
        TextView goAdopt = findViewById(R.id.go_adopt);
        goAdopt.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("dog")) {
                Dog dog = (Dog) intent.getSerializableExtra("dog");

                Toast.makeText(DogProfilePage.this, "Navigating to Adoption Application Page", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(DogProfilePage.this, AdoptionApplicationsPage.class);
                intent2.putExtra("dog", dog);
                startActivity(intent2);
            } else {
                Toast.makeText(DogProfilePage.this, "ERROR: Dog not found!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
