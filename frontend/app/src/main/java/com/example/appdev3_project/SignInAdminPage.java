package com.example.appdev3_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

public class SignInAdminPage extends AppCompatActivity {

    private EditText usernameField, passwordField;
    private Button loginButton;
    private AuthApi authApi;

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
        MyUtil.initializeNavBar(SignInAdminPage.this);

        // fields
        usernameField = (EditText) findViewById(R.id.sign_admin_email);
        passwordField = (EditText) findViewById(R.id.sign_admin_password);
        // buttons
        loginButton = (Button) findViewById(R.id.btn_submit_admin_sign);

        authApi = new RetrofitService().getRetrofit().create(AuthApi.class);

        // Configure Login Button
        loginButton.setOnClickListener(view -> loginAdmin());
    }

    private void loginAdmin() {
        String email = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        User user = new User();
        user.setUsername(email);
        user.setPassword(password);

        // start API call
        Call<User> call = authApi.loginAdmin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User loggedInUser = response.body();
                    String role = loggedInUser.getRole();

                    // save session in SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", loggedInUser.getId());
                    editor.putString("role", role);
                    editor.apply();

                    // redirect to Admin Dashboard
                    Intent intent = new Intent(SignInAdminPage.this, AdminDashboardPage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // extract error message from response and display it
                    try {
                        String errorMessage = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorMessage);
                        String message = jsonObject.optString("message", "Login failed");

                        Toast.makeText(SignInAdminPage.this, message, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(SignInAdminPage.this, "Unexpected error occurred.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("SignInAdminPage", "error: ", t);
                Toast.makeText(SignInAdminPage.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
