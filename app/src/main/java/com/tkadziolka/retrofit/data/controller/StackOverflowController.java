package com.tkadziolka.retrofit.data.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tkadziolka.retrofit.data.RetrofitInstance;
import com.tkadziolka.retrofit.data.api.ResponseCallback;
import com.tkadziolka.retrofit.data.model.Question;
import com.tkadziolka.retrofit.data.model.QuestionDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StackOverflowController {

    private static final String TAG = "StackOverflowController";

    private Gson gson = new Gson();


    public StackOverflowController() {
    }

    public void loadQuestions(final ResponseCallback<Question> callback, int pageSize) {

        Call<JsonObject> call = RetrofitInstance.getStackOverflowApi().getAndroidQuestions(pageSize);
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
        Call<JsonObject> call = RetrofitInstance.getStackOverflowApi().getQuestionDetails(id, filter);
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
}
