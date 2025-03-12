package com.example.appdev3_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;
import com.example.appdev3_project.retrofit.RetrofitService;
import com.example.appdev3_project.retrofit.UserApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyUtil {

    public static void initializeNavBar(Activity activity) {
        TextView appTitle = activity.findViewById(R.id.app_title);
        ImageButton goAdoption = activity.findViewById(R.id.go_adoption);
        ImageButton goProfile = activity.findViewById(R.id.go_profile);

        if (goAdoption == null || goProfile == null) {
            android.util.Log.e("HelperFunctions", "Navbar button(s) not found in " + activity.getLocalClassName());
            return;
        }

        // Set appTitle click listener
        appTitle.setOnClickListener(view -> {
            Intent intent = new Intent(activity, HomePage.class);
            activity.startActivity(intent);
        });

        // Set goAdoption click listener
        goAdoption.setOnClickListener(v -> {
            Intent intent = new Intent(activity, DogAdoptionPage.class);
            activity.startActivity(intent);
        });

        // Set goProfile click listener with authentication check
        goProfile.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = activity.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
            String userRole = sharedPreferences.getString("role", null);

            if ("USER".equals(userRole)) {
                Intent intent = new Intent(activity, ApplicantDashboardPage.class);
                activity.startActivity(intent);
            } else if ("ADMIN".equals(userRole)) {
                Intent intent = new Intent(activity, AdminDashboardPage.class);
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent(activity, SignInApplicantPage.class);
                activity.startActivity(intent);
            }
        });
    }

    public User getSampleUser(){
        User user = new User("myemailaddress@gmail.com", "password", "John Doe", "09221234567", "101 Sunshine Boulevard", "Applicant");
        return user;
    }

    public List<Dog> getSampleDogs() {
        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog((long) 1, "Bravo", "Male", 2, true, true, R.drawable.bravo_male_adult, "text text text"));
        dogs.add(new Dog((long) 2, "Blackie", "Male", 5, true, true, R.drawable.blackie_male_adult, "text text text"));
        dogs.add(new Dog((long) 3, "Biscuit", "Female", 1, false, true, R.drawable.biscuit_female_adult, "text text text"));
        dogs.add(new Dog((long) 4, "Big Whitey", "Male", 2, false, true, R.drawable.big_whitey_male_adult, "text text text")); //wont show up in db
        return dogs;
    }

    public List<Adoption> getSampleAdoptions(User user, List<Dog> dogs) {
        List<Adoption> adoptions = new ArrayList<>();
        adoptions.add(new Adoption("Pending", LocalDateTime.now(),user, dogs.get(0)));
        adoptions.add(new Adoption("Approved", LocalDateTime.now(),user, dogs.get(1)));
        adoptions.add(new Adoption("Rejected", LocalDateTime.now(),user, dogs.get(2)));
        adoptions.add(new Adoption("Pending", LocalDateTime.now(),user, dogs.get(3)));
        return adoptions;
    }

    public interface UserFetchCallback {
        void onUserFetched(User user);
        void onError(String errorMessage);
    }

    public static void fetchUserDetails(Context context, UserFetchCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(context, "User not logged in.", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, SignInApplicantPage.class));
            return;
        }

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        Call<User> call = userApi.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onUserFetched(response.body());
                } else {
                    callback.onError("Failed to load user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

}
