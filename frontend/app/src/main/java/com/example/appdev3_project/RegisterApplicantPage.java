package com.example.appdev3_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterApplicantPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_applicant_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        NavBarUtil.initializeNavBar(RegisterApplicantPage.this);

        // fields
        EditText name = (EditText) findViewById(R.id.register_user_name);
        EditText email = (EditText) findViewById(R.id.register_user_email);
        EditText password = (EditText) findViewById(R.id.register_user_password);
        EditText passwordConfirm = (EditText) findViewById(R.id.register_user_password_conf);
        EditText phone = (EditText) findViewById(R.id.register_user_phone);
        EditText address = (EditText) findViewById(R.id.register_user_address);

        // buttons
        Button registerButton = (Button) findViewById(R.id.btn_submit_user_register_);

        // Configure Register Button
        registerButton.setOnClickListener(view -> {
            // processing for register
        });

    }
}
