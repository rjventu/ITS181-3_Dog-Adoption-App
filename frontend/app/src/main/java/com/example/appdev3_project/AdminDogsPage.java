package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdev3_project.adapter.AdminDogAdapter;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.service.DogService;

import java.util.ArrayList;
import java.util.List;

public class AdminDogsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameLayout noDogs;
    private AdminDogAdapter dogAdapter;
    private List<Dog> dogList;
    private DogService dogService;
    private Button addDog;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_dogs_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminDogsPage.this);

        // Initialize RecyclerView and No Dogs FrameLayout
        noDogs = findViewById(R.id.frameLayout_admin_no_dogs);
        recyclerView = findViewById(R.id.recyclerView_admin_dogs);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid
        dogList = new ArrayList<>();
        dogAdapter = new AdminDogAdapter(this, dogList);
        recyclerView.setAdapter(dogAdapter);
        // Hide both components
        noDogs.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // initialize services
        dogService = new DogService(this);

        // fetch dog records from database
        fetchDogRecords();

        // configure add dog button
        addDog = findViewById(R.id.btn_admin_dogs_add);
        addDog.setOnClickListener(view -> {
            startActivity(new Intent(AdminDogsPage.this, AdminDogsAddPage.class));
        });

    }

    private void fetchDogRecords(){

        // get dog data from database
        dogService.fetchAllDogs(new DogService.DogFetchCallback() {
            @Override
            public void onSuccess(List<Dog> dogs) {
                if(dogs.isEmpty()){
                    // show the "No Dogs" message
                    noDogs.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    // hide the "No Dogs" message
                    noDogs.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    // update dog list data
                    dogList.clear();
                    dogList.addAll(dogs);
                    dogAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AdminDogsPage.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
    
}
