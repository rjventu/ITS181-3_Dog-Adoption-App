package com.example.appdev3_project;

import com.example.appdev3_project.adapter.DogAdapter;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.service.DogService;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DogAdoptionPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private DogService dogService;

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
        MyUtil.initializeNavBar(DogAdoptionPage.this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_dogs);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns grid

        // Fetch dog records from backend
        dogService = new DogService(this);
        dogService.fetchAllDogs(new DogService.DogFetchCallback() {
            @Override
            public void onSuccess(List<Dog> dogs) {
                dogAdapter = new DogAdapter(dogs);
                recyclerView.setAdapter(dogAdapter);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(DogAdoptionPage.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
