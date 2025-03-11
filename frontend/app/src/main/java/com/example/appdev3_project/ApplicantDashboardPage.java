package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdev3_project.adapter.AdoptionAdapter;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDashboardPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Dog> dogList;
    private AdoptionAdapter adoptionAdapter;
    private List<Adoption> adoptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_dashboard_page);

        // Initialize NavBar
        HelperFunctions.initializeNavBar(ApplicantDashboardPage.this);

        // RECYCLEVIEW CODE
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_adoptions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid

        // Generate sample data
        dogList = getSampleDogs();
        adoptionList = getSampleAdoptions(dogList);

        // Use RecyclerView adapter
        adoptionAdapter = new AdoptionAdapter(this, adoptionList);
        recyclerView.setAdapter(adoptionAdapter);
    }

    // Sample data
    private List<Dog> getSampleDogs() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Bravo", "Male", "2 years", "Yes", "Yes", R.drawable.bravo_male_adult, ""));
        dogs.add(new Dog("Blackie", "Male", "5 years", "Yes", "Yes", R.drawable.blackie_male_adult, ""));
        dogs.add(new Dog("Biscuit", "Female", "1 years", "No", "Yes", R.drawable.biscuit_female_adult, ""));
        dogs.add(new Dog("Big Whitey", "Male", "2 years", "No", "Yes", R.drawable.big_whitey_male_adult, ""));
        return dogs;
    }

    private List<Adoption> getSampleAdoptions(List<Dog> dogs) {
        List<Adoption> adoptions = new ArrayList<>();
        adoptions.add(new Adoption("Pending", LocalDateTime.now(),dogs.get(0)));
        adoptions.add(new Adoption("Approved", LocalDateTime.now(),dogs.get(1)));
        adoptions.add(new Adoption("Rejected", LocalDateTime.now(),dogs.get(2)));
        adoptions.add(new Adoption("Pending", LocalDateTime.now(),dogs.get(3)));
        return adoptions;
    }

    // go back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
