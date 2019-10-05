package com.tkadziolka.retrofit.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuthApi {

    @GET("bearer")
    Call<JsonObject> authorizeApp(@Header("Authorization") String bearerToken);

}
