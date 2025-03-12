package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/users/username/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") long id);
}
