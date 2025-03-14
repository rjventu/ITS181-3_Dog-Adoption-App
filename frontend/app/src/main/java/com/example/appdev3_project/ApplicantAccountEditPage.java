package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.User;
import com.example.appdev3_project.service.UserService;

public class ApplicantAccountEditPage extends AppCompatActivity {

    EditText emailField, passwordField, nameField, phoneField, addressField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_account_edit_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_account_edit), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        MyUtil.initializeNavBar(ApplicantAccountEditPage.this);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            User user = (User) intent.getSerializableExtra("user");

            // Display User details
            emailField = (EditText) findViewById(R.id.admin_edit_user_email);
            passwordField = (EditText) findViewById(R.id.admin_edit_user_pass);
            nameField = (EditText) findViewById(R.id.admin_edit_user_name);
            phoneField = (EditText) findViewById(R.id.admin_edit_user_phone);
            addressField = (EditText) findViewById(R.id.admin_edit_user_address);

            emailField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            nameField.setText(user.getName());
            phoneField.setText(user.getContact());
            addressField.setText(user.getAddress());
        }

        // Configure Cancel Button
        Button cancelButton = findViewById(R.id.btn_admin_account_cancel);
        cancelButton.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("user")) {
                User user = (User) intent.getSerializableExtra("user");
                Intent intent2 = new Intent(ApplicantAccountEditPage.this, ApplicantAccountViewPage.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }
        });

        // Configure Save Button
        Button saveButton = findViewById(R.id.btn_admin_account_save);
        saveButton.setOnClickListener(view -> {
            if (intent != null && intent.hasExtra("user")) {
                User user = (User) intent.getSerializableExtra("user");
                user.setName(nameField.getText().toString());
                user.setContact(phoneField.getText().toString());
                user.setAddress(addressField.getText().toString());

                UserService userService = new UserService(this);
                userService.updateUser(user, new UserService.UserUpdateCallback() {
                    @Override
                    public void onSuccess(User updatedUser) {
                        Toast.makeText(getApplicationContext(), "User updated successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(ApplicantAccountEditPage.this, ApplicantAccountViewPage.class);
                        intent2.putExtra("user", updatedUser);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        finish();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
