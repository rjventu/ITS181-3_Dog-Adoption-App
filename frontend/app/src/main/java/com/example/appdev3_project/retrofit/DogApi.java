package com.example.appdev3_project.retrofit;

import com.example.appdev3_project.model.Dog;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DogApi {
    @GET("/api/dogs")
    Call<List<Dog>> getAllDogs();

}
