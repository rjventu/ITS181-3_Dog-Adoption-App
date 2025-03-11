package com.example.appdev3_project;

import com.example.appdev3_project.adapter.DogAdapter;
import com.example.appdev3_project.model.Dog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DogAdoptionPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private List<Dog> dogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_adoption_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dog_adoption), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        NavBarUtil.initializeNavBar(DogAdoptionPage.this);

        // RECYCLEVIEW CODE
            // Initialize RecyclerView
            recyclerView = findViewById(R.id.recyclerView_dogs);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns grid

            // Load dog data (sample data for now)
            dogList = getSampleDogs();
            dogAdapter = new DogAdapter(dogList);

            recyclerView.setAdapter(dogAdapter);
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

}
