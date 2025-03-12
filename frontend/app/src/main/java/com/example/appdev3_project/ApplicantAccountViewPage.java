package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.User;

public class ApplicantAccountViewPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_account_view_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.applicant_account_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(ApplicantAccountViewPage.this);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            User user = (User) intent.getSerializableExtra("user");

            // Display User details
            ((EditText) findViewById(R.id.applicant_view_user_email)).setText(user.getUsername());
            ((EditText) findViewById(R.id.applicant_view_user_pass)).setText(user.getPassword());
            ((EditText) findViewById(R.id.applicant_view_user_name)).setText(user.getName());
            ((EditText) findViewById(R.id.applicant_view_user_phone)).setText(user.getContact());
            ((EditText) findViewById(R.id.applicant_view_user_address)).setText(user.getAddress());

        }

        // Configure Edit Button
        Button viewButton = findViewById(R.id.btn_applicant_account_edit);
        viewButton.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("user")) {
                User user = (User) intent.getSerializableExtra("user");
                Intent intent2 = new Intent(ApplicantAccountViewPage.this, ApplicantAccountEditPage.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }
        });
    }
}
