package com.example.appdev3_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appdev3_project.model.User;
import com.example.appdev3_project.retrofit.AuthApi;
import com.example.appdev3_project.retrofit.RetrofitService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterApplicantPage extends AppCompatActivity {

    private EditText nameField, emailField, passwordField, passwordConfirmField, phoneField, addressField;
    private Button registerButton;
    private AuthApi authApi;

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
        MyUtil.initializeNavBar(RegisterApplicantPage.this);

        // fields
        nameField = (EditText) findViewById(R.id.register_user_name);
        emailField = (EditText) findViewById(R.id.register_user_email);
        passwordField = (EditText) findViewById(R.id.register_user_password);
        passwordConfirmField = (EditText) findViewById(R.id.register_user_password_conf);
        phoneField = (EditText) findViewById(R.id.register_user_phone);
        addressField = (EditText) findViewById(R.id.register_user_address);

        // buttons
        registerButton = (Button) findViewById(R.id.btn_submit_user_register_);

        authApi = new RetrofitService().getRetrofit().create(AuthApi.class);

        // Configure Register Button
        registerButton.setOnClickListener(view -> registerUser());

    }

    private void registerUser() {
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = passwordConfirmField.getText().toString();
        String phone = phoneField.getText().toString();
        String address = addressField.getText().toString();

        // check if passwords match before sending request
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(email, password, name, phone, address, "USER");

        // start API call
        Call<User> call = authApi.register(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterApplicantPage.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterApplicantPage.this, SignInApplicantPage.class));
                    finish();
                } else {
                    // extract error message from response and display it
                    try {
                        String errorMessage = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorMessage);
                        String message = jsonObject.optString("message", "Login failed");

                        Toast.makeText(RegisterApplicantPage.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterApplicantPage.this, "Unexpected error occurred.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterApplicantPage.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
