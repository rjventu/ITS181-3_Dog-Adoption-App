package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;
import com.example.appdev3_project.retrofit.RetrofitService;
import com.example.appdev3_project.retrofit.AdoptionApi;
import com.example.appdev3_project.retrofit.UserApi;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdoptionApplicationsPage extends AppCompatActivity {

    private User user;
    private Dog dog;

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
        MyUtil.initializeNavBar(AdoptionApplicationsPage.this);

        // Get dog data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dog")) {
            dog = (Dog) intent.getSerializableExtra("dog");
            TextView dogName = findViewById(R.id.application_dog_name);
            dogName.setText(dog.getName());
        }

        // Get user data from database
        MyUtil.fetchUserDetails(this, new MyUtil.UserFetchCallback() {
            @Override
            public void onUserFetched(User fetchedUser) {
                user = fetchedUser;
                TextView name = findViewById(R.id.application_name);
                TextView email = findViewById(R.id.application_email);
                TextView phone = findViewById(R.id.application_phone);
                TextView address = findViewById(R.id.application_address);

                name.setText("Name: " + user.getName());
                email.setText("Email: " + user.getUsername());
                phone.setText("Phone: " + user.getContact());
                address.setText("Address: " + user.getAddress());
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AdoptionApplicationsPage.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        Button submitButton = findViewById(R.id.btn_application_submit);
        submitButton.setOnClickListener(view -> submitAdoption());
    }

    // submit adoption request to backend
    private void submitAdoption() {
        if (user == null || dog == null) {
            Toast.makeText(this, "Error: Missing user or dog data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize Retrofit
        RetrofitService retrofitService = new RetrofitService();
        AdoptionApi adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);

        Adoption adoption = new Adoption();
        Call<Adoption> call = adoptionApi.addAdoption(user.getId(), dog.getId(), adoption);

        call.enqueue(new Callback<Adoption>() {
            @Override
            public void onResponse(Call<Adoption> call, Response<Adoption> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdoptionApplicationsPage.this, "Adoption application submitted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdoptionApplicationsPage.this, ApplicantDashboardPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AdoptionApplicationsPage.this, "Submission failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Adoption> call, Throwable t) {
                Log.e("AdoptionApp", "Network request failed", t);
                Toast.makeText(AdoptionApplicationsPage.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}