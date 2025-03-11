package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.Dog;


public class AdoptionApplicationsPage extends AppCompatActivity {

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
        NavBarUtil.initializeNavBar(AdoptionApplicationsPage.this);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dog")) {
            Dog dog = (Dog) intent.getSerializableExtra("dog");
        }
    }
}