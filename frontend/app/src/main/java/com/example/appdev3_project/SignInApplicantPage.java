package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInApplicantPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_applicant_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_sign), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        NavBarUtil.initializeNavBar(SignInApplicantPage.this);

        // fields
        EditText username = (EditText) findViewById(R.id.sign_user_email);
        EditText password = (EditText) findViewById(R.id.sign_user_password);
        // buttons
        Button loginButton = (Button) findViewById(R.id.btn_submit_user_sign_);
        Button goToUserRegister = (Button) findViewById(R.id.btn_go_to_register);
        Button goToAdminSign = (Button) findViewById(R.id.btn_go_to_admin_sign);

        // Configure Login Button
        loginButton.setOnClickListener(view -> {
            // processing for login
        });

        // Configure Register Button
        goToUserRegister.setOnClickListener(view -> {
            Intent intent = new Intent(SignInApplicantPage.this, RegisterApplicantPage.class);
            startActivity(intent);
        });

        // Configure Admin Login Button
        goToAdminSign.setOnClickListener(view -> {
            Intent intent = new Intent(SignInApplicantPage.this, SignInAdminPage.class);
            startActivity(intent);
        });
    }
}
