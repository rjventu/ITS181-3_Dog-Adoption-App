package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") long id);

    @PUT("/api/users/{id}")
    Call<User> updateUser(@Path("id") long id, @Body User user);
}
