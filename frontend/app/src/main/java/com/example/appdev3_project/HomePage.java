package com.example.appdev3_project;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import android.widget.Toast;

import android.util.Log;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);

        // Find ImageButton NOT WORKING??? IDK WHY HUHU
        ImageButton goAdoption = findViewById(R.id.go_adoption);

        if (goAdoption == null) {
            Log.e("HomePage", "go_adoption is NULL! Check XML ID.");
        } else {
            Log.d("HomePage", "go_adoption found!");
        }

        // Set click listener
        goAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("HomePage", "go_adoption clicked!");
                Toast.makeText(HomePage.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, DogAdoptionPage.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

}