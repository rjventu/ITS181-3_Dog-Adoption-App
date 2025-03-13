package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.Adoption;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AdoptionApi {
    @POST("/api/adoptions/{userId}/{dogId}")
    Call<Adoption> addAdoption(@Path("userId") Long userId, @Path("dogId") Long dogId, @Body Adoption adoption);

    @GET("/api/adoptions/exists")
    Call<Boolean> checkAdoptionExists(@Query("userId") Long userId, @Query("dogId") Long dogId);

    @GET("/api/adoptions")
    Call<List<Adoption>> getAllAdoptions();

    @GET("/api/adoptions/user/{userId}")
    Call<List<Adoption>> getAdoptionsByUserId(@Path("userId") long userId);

    @DELETE("/api/adoptions/{id}")
    Call<Void> deleteAdoption(@Path("id") long adoptionId);

    @DELETE("/api/adoptions/user/{userId}/dog/{dogId}")
    Call<Void> deleteAdoptionByUserAndDog(@Path("userId") long userId, @Path("dogId") long dogId);

}
