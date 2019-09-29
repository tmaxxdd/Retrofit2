package com.tkadziolka.retrofit.data.api;

import androidx.annotation.NonNull;

import java.util.List;

public interface ResponseCallback<T> {

    void onSuccess(@NonNull List<T> list);

    void onError(@NonNull Throwable throwable);
}
