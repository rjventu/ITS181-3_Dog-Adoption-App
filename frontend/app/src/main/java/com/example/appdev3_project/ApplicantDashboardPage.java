package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.appdev3_project.service.AdoptionService;
import com.example.appdev3_project.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ApplicantDashboardPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FrameLayout noAdoptions;
    private AdoptionAdapter adoptionAdapter;
    private List<Adoption> adoptionList;
    private UserService userService;
    private AdoptionService adoptionService;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_dashboard_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.applicant_dashboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(ApplicantDashboardPage.this);

        // Initialize RecyclerView and No Adoptions FrameLayout
        noAdoptions = findViewById(R.id.frameLayout_no_adoptions);
        recyclerView = findViewById(R.id.recyclerView_adoptions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid
        adoptionList = new ArrayList<>();
        adoptionAdapter = new AdoptionAdapter(this, adoptionList);
        recyclerView.setAdapter(adoptionAdapter);
        // Hide both components
        noAdoptions.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // Get user data from database
        userService = new UserService(this);
        userService.fetchUserDetails(new UserService.UserFetchCallback() {
            @Override
            public void onSuccess(User newUser) {
                user = newUser;
                ((TextView) findViewById(R.id.applicant_dashboard_welcome_message))
                        .setText("Welcome, " + user.getName().split(" ")[0] + "!");

                // fetch and display adoption applications
                fetchAdoptionApplications();
            }
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(ApplicantDashboardPage.this, "User not found!", Toast.LENGTH_SHORT).show();
            }

        });

        // Configure View button
        Button viewButton = findViewById(R.id.btn_applicant_account_view);
        viewButton.setOnClickListener(view -> {
            Intent intent = new Intent(ApplicantDashboardPage.this, ApplicantAccountViewPage.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });

        // Configure Logout Button
        Button logoutButton = findViewById(R.id.btn_applicant_account_logout);
        logoutButton.setOnClickListener(view -> logoutUser());

        // Configure Adopt Button
        Button adoptButton = findViewById(R.id.btn_applicant_adopt);
        adoptButton.setOnClickListener(view -> {
            startActivity(new Intent(ApplicantDashboardPage.this, DogAdoptionPage.class));
        });

//        // Initialize RecyclerView
//        recyclerView = findViewById(R.id.recyclerView_adoptions);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid
//
//        // Generate sample data
//        dogList = new MyUtil().getSampleDogs();
//        adoptionList = new MyUtil().getSampleAdoptions(user, dogList);
//
//        // Use RecyclerView adapter
//        adoptionAdapter = new AdoptionAdapter(this, adoptionList);
//        recyclerView.setAdapter(adoptionAdapter);
    }

    private void fetchAdoptionApplications() {
        if (user == null) return;

        // Get adoption data from database
        adoptionService = new AdoptionService(this);
        adoptionService.fetchUserAdoptions(user.getId(), new AdoptionService.AdoptionFetchCallback() {
            @Override
            public void onSuccess(List<Adoption> adoptions) {
                FrameLayout noAdoptions = (FrameLayout) findViewById(R.id.frameLayout_no_adoptions);
                if(adoptions.isEmpty()){
                    // show the "No Adoptions" message
                    noAdoptions.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
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
                Toast.makeText(ApplicantDashboardPage.this, "Failed to load adoptions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logoutUser() {
        // clear SharedPreferences session
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // redirect user to Sign-In page
        Intent intent = new Intent(ApplicantDashboardPage.this, SignInApplicantPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // prevent going back to dashboard
        startActivity(intent);
        finish(); // close current activity
    }
}
