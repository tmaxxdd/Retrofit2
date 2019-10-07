package com.tkadziolka.retrofit.data;

import androidx.annotation.Nullable;

public interface SimpleCallback<T> {

    void onResult(@Nullable T response);
}
