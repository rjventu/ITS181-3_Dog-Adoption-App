package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.Dog;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DogApi {
    @GET("/api/dogs")
    Call<List<Dog>> getAllDogs();

    @Multipart
    @POST("/api/dogs")
    Call<Dog> addDog(@Part("dog") RequestBody dogJson, @Part MultipartBody.Part image);

    @Multipart
    @PUT("/api/dogs/{id}")
    Call<Dog> updateDog(@Path("id") Long dogId, @Part("dog") RequestBody dogJson, @Part MultipartBody.Part image);

    @DELETE("/api/dogs/{id}")
    Call<Void> deleteDog(@Path("id") Long dogId);

}
