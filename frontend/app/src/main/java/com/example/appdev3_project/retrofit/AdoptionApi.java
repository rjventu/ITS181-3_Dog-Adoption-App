package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.Adoption;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AdoptionApi {
    @POST("/api/adoptions/{userId}/{dogId}")
    Call<Adoption> addAdoption(@Path("userId") Long userId, @Path("dogId") Long dogId, @Body Adoption adoption);

    @GET("/api/adoptions/exists")
    Call<Boolean> checkAdoptionExists(@Query("userId") Long userId, @Query("dogId") Long dogId);

}
