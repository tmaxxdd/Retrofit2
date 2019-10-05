package com.tkadziolka.retrofit.data.controller;

import android.util.Log;

import com.google.gson.JsonObject;
import com.tkadziolka.retrofit.data.api.AuthApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthController {

    public static final String TAG = "AUTHORIZATION";
    private AuthApi api;

    public AuthController() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(AuthApi.class);
    }

    public void authorize(String token) {
        Call<JsonObject> call = api.authorizeApp("Bearer " + token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()) {
                    case 200:
                        Log.d(TAG, "App successfully authorized");
                        break;
                    case 401:
                        Log.d(TAG, "App not authorized!");
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
