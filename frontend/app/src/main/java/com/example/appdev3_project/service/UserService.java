package com.example.appdev3_project.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.appdev3_project.model.User;
import com.example.appdev3_project.retrofit.RetrofitService;
import com.example.appdev3_project.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final UserApi userApi;
    private final Context context;

    public UserService(Context context) {
        this.context = context;
        RetrofitService retrofitService = new RetrofitService();
        this.userApi = retrofitService.getRetrofit().create(UserApi.class);
    }

    public void fetchUserDetails(UserFetchCallback callback) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId == -1) {
            Toast.makeText(context, "User not logged in!", Toast.LENGTH_SHORT).show();
            callback.onError("User not logged in");
            return;
        }

        Call<User> call = userApi.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface UserFetchCallback {
        void onSuccess(User user);
        void onError(String errorMessage);
    }
}
