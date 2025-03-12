package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.applicant_dashboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(ApplicantDashboardPage.this);

        // Set welcome message
        User user = new MyUtil().getSampleUser();
        TextView welcome = (TextView) findViewById(R.id.applicant_dashboard_welcome_message);
        welcome.setText("Welcome, " + user.getName().split(" ")[0] + "!");

        // RECYCLEVIEW CODE
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView_adoptions);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 1 column grid

//        // Generate sample data
//        dogList = new MyUtil().getSampleDogs();
//        adoptionList = new MyUtil().getSampleAdoptions(user, dogList);
//
//        // Use RecyclerView adapter
//        adoptionAdapter = new AdoptionAdapter(this, adoptionList);
//        recyclerView.setAdapter(adoptionAdapter);

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
