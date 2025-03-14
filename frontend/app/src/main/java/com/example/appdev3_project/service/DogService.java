package com.example.appdev3_project.service;

import android.content.Context;
import android.widget.Toast;

import com.example.appdev3_project.model.Dog;
import com.example.appdev3_project.retrofit.DogApi;
import com.example.appdev3_project.retrofit.RetrofitService;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void addDog(Dog dog, File imageFile, DogAddCallback callback) {
        Gson gson = new Gson();
        String dogJson = gson.toJson(dog);

        RequestBody dogRequestBody = RequestBody.create(MediaType.parse("text/plain"), dogJson);

        MultipartBody.Part imagePart = null;
        if (imageFile != null && imageFile.exists()) {
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
        } else {
            Toast.makeText(context, "Image file does not exist or is null!", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Dog> call = dogApi.addDog(dogRequestBody, imagePart);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(true);
                } else {
                    callback.onSuccess(false);
                }
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                callback.onSuccess(false);
            }
        });
    }

    public void updateDog(Dog dog, File imageFile, DogUpdateCallback callback) {
        Gson gson = new Gson();
        String dogJson = gson.toJson(dog);
        RequestBody dogRequestBody = RequestBody.create(MediaType.parse("application/json"), dogJson);

        MultipartBody.Part imagePart = null;
        if (imageFile != null && imageFile.exists()) {
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageRequestBody);
        }

        Call<Dog> call = dogApi.updateDog(dog.getId(), dogRequestBody, imagePart);
        call.enqueue(new Callback<Dog>() {
            @Override
            public void onResponse(Call<Dog> call, Response<Dog> response) {
                callback.onSuccess(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Dog> call, Throwable t) {
                callback.onSuccess(false);
            }
        });
    }

    public void deleteDog(Long dogId, DogDeleteCallback callback) {
        Call<Void> call = dogApi.deleteDog(dogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onSuccess(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onSuccess(false);
            }
        });
    }

    public interface DogFetchCallback {
        void onSuccess(List<Dog> dogs);
        void onError(String errorMessage);
    }

    public interface DogAddCallback {
        void onSuccess(boolean success);
    }
    public interface DogUpdateCallback {
        void onSuccess(boolean success);
    }

    public interface DogDeleteCallback {
        void onSuccess(boolean success);
    }

}
