package com.example.appdev3_project;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdev3_project.adapter.AdminAdoptionAdapter;
import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.service.AdoptionService;

import java.util.ArrayList;
import java.util.List;

public class AdminAdoptionsPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameLayout noAdoptions;
    private AdminAdoptionAdapter adoptionAdapter;
    private List<Adoption> adoptionList;
    private AdoptionService adoptionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_adoptions_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminAdoptionsPage.this);

        // Initialize RecyclerView and No Adoptions FrameLayout
        noAdoptions = findViewById(R.id.frameLayout_admin_no_adoptions);
        recyclerView = findViewById(R.id.recyclerView_admin_adoptions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid
        adoptionList = new ArrayList<>();
        adoptionAdapter = new AdminAdoptionAdapter(this, adoptionList);
        recyclerView.setAdapter(adoptionAdapter);
        // Hide both components
        noAdoptions.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // initialize services
        adoptionService = new AdoptionService(this);

        fetchAdoptionApplications();
    }

    private void fetchAdoptionApplications() {
        // get adoption data from database
        adoptionService.fetchAllAdoptions(new AdoptionService.AdoptionsFetchAllCallback() {
            @Override
            public void onSuccess(List<Adoption> adoptions) {
                if(adoptions.isEmpty()){
                    // show the "No Adoptions" message
                    noAdoptions.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    // hide the "No Adoptions" message
                    noAdoptions.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    // update adoption list data
                    adoptionList.clear();
                    adoptionList.addAll(adoptions);
                    adoptionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AdminAdoptionsPage.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
