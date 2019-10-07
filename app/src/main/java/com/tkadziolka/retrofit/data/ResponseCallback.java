package com.tkadziolka.retrofit.data;

import androidx.annotation.NonNull;

import com.tkadziolka.retrofit.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface ResponseCallback<T> {

    void onSuccess(@NonNull T response);

    void onError(@NonNull Throwable throwable);

}
