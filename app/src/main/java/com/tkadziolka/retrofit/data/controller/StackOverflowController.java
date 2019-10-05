package com.tkadziolka.retrofit.data.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tkadziolka.retrofit.data.ErrorInterpreter;
import com.tkadziolka.retrofit.data.api.ResponseCallback;
import com.tkadziolka.retrofit.data.api.StackOverflowAPI;
import com.tkadziolka.retrofit.data.model.Question;
import com.tkadziolka.retrofit.data.model.QuestionDetail;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackOverflowController {

    private static final String TAG = "StackOverflowController";
    private static final String STACK_EXCHANGE_BASE_URL = "https://api.stackexchange.com/";

    private StackOverflowAPI api;
    private Gson gson = new Gson();


    public StackOverflowController() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(STACK_EXCHANGE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getLoggingClient())
                .client(getErrorClient())
                .build();

        api = retrofit.create(StackOverflowAPI.class);
    }

    public void loadQuestions(final ResponseCallback<Question> callback, int pageSize) {
        Call<JsonObject> call = api.getAndroidQuestions(pageSize);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<Question> questions = new ArrayList<>();
                if (response.body() != null) {

                    if (response.body().has("items")) {
                        JsonArray items = response.body().getAsJsonArray("items");

                        for (JsonElement element : items)
                            questions.add(gson.fromJson(element, Question.class));
                    }

                } else {
                    Log.d(TAG, "Empty response body!");
                }

                for (Question item : questions)
                    Log.d(TAG, "Title: " + item.getTitle());

                callback.onSuccess(questions);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                if (throwable.getMessage() != null) {
                    Log.e(TAG, throwable.getMessage());
                    callback.onError(throwable);
                }
            }
        });
    }

    public void loadContent(final ResponseCallback<QuestionDetail> callback, int id, String filter) {
        Call<JsonObject> call = api.getQuestionDetails(id, filter);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ArrayList<QuestionDetail> questionDetails = new ArrayList<>();
                if (response.body() != null) {

                    if (response.body().has("items")) {
                        JsonArray items = response.body().getAsJsonArray("items");

                        for (JsonElement element : items)
                            questionDetails.add(gson.fromJson(element, QuestionDetail.class));

                    } else {
                        Log.d(TAG, "Empty response body!");
                    }

                    callback.onSuccess(questionDetails);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                if (throwable.getMessage() != null) {
                    Log.e(TAG, throwable.getMessage());
                    callback.onError(throwable);
                }
            }
        });
    }

    public void loadPosts(final ResponseCallback<QuestionDetail> callback) {
        Call<JsonObject> call = api.getAllPosts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private OkHttpClient getLoggingClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.redactHeader("Authorization");
        interceptor.redactHeader("Cookie");
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    private OkHttpClient getErrorClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        okhttp3.Response response = chain.proceed(request);

                        ErrorInterpreter.parse(response.code());

                        return response;
                    }
                }).build();
    }
}
