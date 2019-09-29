package com.tkadziolka.retrofit.data;

import com.tkadziolka.retrofit.data.api.StackOverflowAPI;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static final String STACK_EXCHANGE_BASE_URL = "https://api.stackexchange.com/";

    private static RetrofitInstance instance = null;
    private static Retrofit retrofit;

    public RetrofitInstance() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.redactHeader("Authorization");
        interceptor.redactHeader("Cookie");
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient interceptorClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(STACK_EXCHANGE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(interceptorClient)
                .build();
    }

    public static StackOverflowAPI getStackOverflowApi() {
        return retrofit.create(StackOverflowAPI.class);
    }

    public static RetrofitInstance get() {
        if (instance == null) {
            instance = new RetrofitInstance();
        }

        return instance;
    }

}
