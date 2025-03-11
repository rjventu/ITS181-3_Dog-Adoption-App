package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdev3_project.adapter.AdoptionAdapter;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        HelperFunctions.initializeNavBar(ApplicantDashboardPage.this);

        // RECYCLEVIEW CODE
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_adoptions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid

        // Generate sample data
        dogList = getSampleDogs();
        adoptionList = getSampleAdoptions(dogList);

        // Set welcome message
        User user = getSampleUser();
        TextView welcome = (TextView) findViewById(R.id.welcome_message);
        welcome.setText("Welcome, " + user.getName().split(" ")[0] + "!");

        // Use RecyclerView adapter
        adoptionAdapter = new AdoptionAdapter(this, adoptionList);
        recyclerView.setAdapter(adoptionAdapter);

        // Configure View button
        Button viewButton = findViewById(R.id.button_account_view);
        viewButton.setOnClickListener(view -> {
            Intent intent = new Intent(ApplicantDashboardPage.this, ApplicantViewPage.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }

    private User getSampleUser(){
        User user = new User("myemailaddress@gmail.com", "password", "John Doe", "09221234567", "101 Sunshine Boulevard", "Applicant");
        return user;
    }

    // Sample data
    private List<Dog> getSampleDogs() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Bravo", "Male", 2, true, true, R.drawable.bravo_male_adult, "text text text"));
        dogs.add(new Dog("Blackie", "Male", 5, true, true, R.drawable.blackie_male_adult, "text text text"));
        dogs.add(new Dog("Biscuit", "Female", 1, false, true, R.drawable.biscuit_female_adult, "text text text"));
        dogs.add(new Dog("Big Whitey", "Male", 2, false, true, R.drawable.big_whitey_male_adult, "text text text"));
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

}
