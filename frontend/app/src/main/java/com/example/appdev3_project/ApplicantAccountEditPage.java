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

public class ApplicantAccountEditPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_account_edit_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.applicant_account_edit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        NavBarUtil.initializeNavBar(ApplicantAccountEditPage.this);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            User user = (User) intent.getSerializableExtra("user");

            EditText username = (EditText) findViewById(R.id.applicant_edit_user_email);
            EditText password = (EditText) findViewById(R.id.applicant_edit_user_pass);
            EditText name = (EditText) findViewById(R.id.applicant_edit_user_name);
            EditText phone = (EditText) findViewById(R.id.applicant_edit_user_phone);
            EditText address = (EditText) findViewById(R.id.applicant_edit_user_address);

            // Display User details
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            name.setText(user.getName());
            phone.setText(user.getContact());
            address.setText(user.getAddress());
        }

        // Configure Cancel Button
        Button cancelButton = findViewById(R.id.btn_applicant_account_cancel);
        cancelButton.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("user")) {
                User user = (User) intent.getSerializableExtra("user");
                Intent intent2 = new Intent(ApplicantAccountEditPage.this, ApplicantAccountViewPage.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }
        });

        // Configure Save Button
        Button saveButton = findViewById(R.id.btn_applicant_account_save);
        saveButton.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("user")) {
                User user = (User) intent.getSerializableExtra("user");

                // add code to persist data

                Intent intent2 = new Intent(ApplicantAccountEditPage.this, ApplicantAccountViewPage.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }
        });
    }
}
