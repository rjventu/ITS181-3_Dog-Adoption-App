package com.example.appdev3_project.service;

import android.content.Context;

import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.retrofit.AdoptionApi;
import com.example.appdev3_project.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdoptionService {
    private final AdoptionApi adoptionApi;
    private final Context context;

    public AdoptionService(Context context) {
        this.context = context;
        RetrofitService retrofitService = new RetrofitService();
        this.adoptionApi = retrofitService.getRetrofit().create(AdoptionApi.class);
    }

    public void checkIfAdoptionExists(long userId, long dogId, AdoptionCheckCallback callback) {
        Call<Boolean> call = adoptionApi.checkAdoptionExists(userId, dogId);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body());
                } else {
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void submitAdoption(long userId, long dogId, AdoptionSubmitCallback callback) {
        Adoption adoption = new Adoption();
        Call<Adoption> call = adoptionApi.addAdoption(userId, dogId, adoption);
        call.enqueue(new Callback<Adoption>() {
            @Override
            public void onResponse(Call<Adoption> call, Response<Adoption> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Submission failed");
                }
            }

            @Override
            public void onFailure(Call<Adoption> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void fetchUserAdoptions(long userId, AdoptionFetchCallback callback) {
        Call<List<Adoption>> call = adoptionApi.getAdoptionsByUserId(userId);
        call.enqueue(new Callback<List<Adoption>>() {
            @Override
            public void onResponse(Call<List<Adoption>> call, Response<List<Adoption>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to load adoptions.");
                }
            }

            @Override
            public void onFailure(Call<List<Adoption>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void deleteAdoption(long adoptionId, AdoptionDeleteCallback callback) {
        Call<Void> call = adoptionApi.deleteAdoption(adoptionId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Failed to delete adoption");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void deleteAdoptionByUserAndDog(long userId, long dogId, AdoptionDeleteCallback callback) {
        Call<Void> call = adoptionApi.deleteAdoptionByUserAndDog(userId, dogId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Failed to delete adoption");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public interface AdoptionCheckCallback {
        void onResult(boolean exists);
        void onError(String errorMessage);
    }

    public interface AdoptionSubmitCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface AdoptionFetchCallback {
        void onSuccess(List<Adoption> adoptions);
        void onError(String errorMessage);
    }

    public interface AdoptionDeleteCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
