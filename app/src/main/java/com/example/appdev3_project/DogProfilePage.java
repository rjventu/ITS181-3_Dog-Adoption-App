package com.example.appdev3_project;

import com.example.appdev3_project.model.Dog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DogProfilePage extends AppCompatActivity {
    private ImageView dogImage;
    private TextView dogName, dogAge, dogGender, dogVaccination, dogSterilization, dogBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_profile_page);

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

            // Set values
            dogName.setText(dog.getName());
            dogAge.setText("Age: " + dog.getAge() + " years");
            dogGender.setText("Gender: " + dog.getGender());
            dogVaccination.setText("Vaccinated: " + (dog.getVaccination()));
            dogSterilization.setText("Sterilized: " + (dog.getSterilization()));
            dogBio.setText(dog.getBio());

            // Set image (assuming drawable resource ID)
            dogImage.setImageResource(dog.getImageResId());
        }
    }
}
