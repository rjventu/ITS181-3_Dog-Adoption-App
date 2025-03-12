package com.example.appdev3_project.service;

import android.content.Context;

import com.example.appdev3_project.model.Adoption;
import com.example.appdev3_project.retrofit.AdoptionApi;
import com.example.appdev3_project.retrofit.RetrofitService;

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

    public interface AdoptionCheckCallback {
        void onResult(boolean exists);
        void onError(String errorMessage);
    }

    public interface AdoptionSubmitCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
