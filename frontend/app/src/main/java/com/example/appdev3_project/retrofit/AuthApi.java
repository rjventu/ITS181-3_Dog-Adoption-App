package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/api/sign-in")
    Call<User> login(@Body User user);

    @POST("/api/sign-in-admin")
    Call<User> loginAdmin(@Body User user);

    @POST("/api/sign-up")
    Call<User> register(@Body User user);
}
