package com.example.appdev3_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignInAdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_admin_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.admin_sign), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize NavBar
        NavBarUtil.initializeNavBar(SignInAdminPage.this);

        // fields
        EditText username = (EditText) findViewById(R.id.sign_admin_email);
        EditText password = (EditText) findViewById(R.id.sign_admin_password);
        // buttons
        Button loginButton = (Button) findViewById(R.id.btn_submit_admin_sign);

        // Configure Login Button
        loginButton.setOnClickListener(view -> {
            // processing for login
        });
    }
}
