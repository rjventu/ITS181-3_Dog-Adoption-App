package com.example.appdev3_project;

import com.example.appdev3_project.adapter.DogAdapter;
import com.example.appdev3_project.model.Dog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
        dogs.add(new Dog("Bravo", "Male", "2 years", "Yes", "Yes", R.drawable.bravo_male_adult, ""));
        dogs.add(new Dog("Blackie", "Male", "5 years", "Yes", "Yes", R.drawable.blackie_male_adult, ""));
        dogs.add(new Dog("Biscuit", "Female", "1 years", "No", "Yes", R.drawable.biscuit_female_adult, ""));
        dogs.add(new Dog("Big Whitey", "Male", "2 years", "No", "Yes", R.drawable.big_whitey_male_adult, ""));
        return dogs;
    }

    // go back
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
