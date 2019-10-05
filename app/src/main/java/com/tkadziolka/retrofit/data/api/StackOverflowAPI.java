package com.tkadziolka.retrofit.data.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackOverflowAPI {

    @Headers({
            "Accept: application/json",
            "User-Agent: Retrofit-udemy-app",
            "Cache-Control: max-age=3600",
    })
    @GET("2.2/questions?order=desc&sort=hot&tagged=Android&site=stackoverflow")
    Call<JsonObject> getAndroidQuestions(@Query("pagesize") int pageSize);

    @GET("2.2/questions/{id}?site=stackoverflow")
    Call<JsonObject> getQuestionDetails(@Path("id") int id, @Query("filter") String filter);

    @GET("2.2/posts/")
    Call<JsonObject> getAllPosts();
}
