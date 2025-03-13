package com.example.appdev3_project.service;

import android.content.Context;
import android.widget.Toast;

import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.retrofit.DogApi;
import com.example.appdev3_project.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogService {
    private final DogApi dogApi;
    private final Context context;

    public DogService(Context context) {
        this.context = context;
        RetrofitService retrofitService = new RetrofitService();
        this.dogApi = retrofitService.getRetrofit().create(DogApi.class);
    }

    public void fetchAllDogs(DogFetchCallback callback) {
        Call<List<Dog>> call = dogApi.getAllDogs();
        call.enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to fetch dogs");
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                callback.onError(t.getMessage());
                Toast.makeText(context, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface DogFetchCallback {
        void onSuccess(List<Dog> dogs);
        void onError(String errorMessage);
    }
}
