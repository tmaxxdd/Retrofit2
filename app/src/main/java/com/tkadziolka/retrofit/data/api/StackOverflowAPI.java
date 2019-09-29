package com.tkadziolka.retrofit.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackOverflowAPI {

    @GET("2.2/questions?order=desc&sort=hot&tagged=Android&site=stackoverflow")
    Call<JsonObject> getAndroidQuestions(@Query("pagesize") int pageSize);

    @GET("2.2/questions/{id}?site=stackoverflow")
    Call<JsonObject> getQuestionDetails(@Path("id") int id, @Query("filter") String filter);
}
