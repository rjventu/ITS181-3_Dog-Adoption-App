package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboardPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_dashboard), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(AdminDashboardPage.this);

        // Set welcome message
        User user = new MyUtil().getSampleUser();
        TextView welcome = (TextView) findViewById(R.id.admin_dashboard_welcome_message);
        welcome.setText("Welcome, Admin " + user.getName().split(" ")[0] + "!");

        // Configure View button
        Button viewButton = findViewById(R.id.btn_admin_account_view);
        viewButton.setOnClickListener(view -> {
            Toast.makeText(AdminDashboardPage.this, "Clicked view button", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(AdminDashboardPage.this, ApplicantAccountViewPage.class);
//            intent.putExtra("user", user);
//            startActivity(intent);
        });

        // Configure Manage Dogs button
        Button dogsButton = findViewById(R.id.btn_admin_manage_dogs);
        dogsButton.setOnClickListener(view -> {
            Toast.makeText(AdminDashboardPage.this, "Clicked manage dogs button", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(AdminDashboardPage.this, ApplicantAccountViewPage.class);
//            intent.putExtra("user", user);
//            startActivity(intent);
        });

        // Configure Manage Adoptions button
        Button adoptionsButton = findViewById(R.id.btn_admin_manage_adoptions);
        adoptionsButton.setOnClickListener(view -> {
            Toast.makeText(AdminDashboardPage.this, "Clicked manage adoptions button", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(AdminDashboardPage.this, ApplicantAccountViewPage.class);
//            intent.putExtra("user", user);
//            startActivity(intent);
        });

        // Configure Logout button
        Button logoutButton = findViewById(R.id.btn_admin_account_logout);
        logoutButton.setOnClickListener(view -> logoutAdmin());

    }

    private void logoutAdmin() {
        // clear SharedPreferences session
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // redirect user to Sign-In page
        Intent intent = new Intent(AdminDashboardPage.this, SignInAdminPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // prevent going back to dashboard
        startActivity(intent);
        finish(); // close current activity
    }

}
