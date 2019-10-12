package com.tkadziolka.retrofit.data.controller;

import android.content.Context;

import com.tkadziolka.retrofit.NetworkManager;
import com.tkadziolka.retrofit.data.ErrorInterpreter;
import com.tkadziolka.retrofit.data.SimpleCallback;
import com.tkadziolka.retrofit.data.api.UserAPI;
import com.tkadziolka.retrofit.data.model.User;

import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserController {

    public static final String TAG = "UserController";
    private static final String MOCKAPI_BASE_URL = "https://5d988f1b61c84c00147d7025.mockapi.io/api/";
    private Context context;
    private UserAPI api;

    public UserController(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOCKAPI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getCacheClient())
                .build();

        api = retrofit.create(UserAPI.class);
    }

    public void getAll(SimpleCallback<List<User>> callback) {
        Call<List<User>> call = api.users();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                callback.onResult(parseResponse(response));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void get(int userId, SimpleCallback<User> callback) {
        Call<User> call = api.user(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                callback.onResult(parseResponse(response));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void add(User user, SimpleCallback<Boolean> callback) {
        Call<ResponseBody> call = api.addUser(user.getName(), user.getJob());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResult(validateResponse(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void update(User user, SimpleCallback<Boolean> callback) {
        Call<ResponseBody> call = api.updateUser(user.getId(), user.getName(), user.getJob());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResult(validateResponse(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void delete(int userId, SimpleCallback<Boolean> callback) {
        Call<ResponseBody> call = api.deleteUser(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                callback.onResult(validateResponse(response));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private <T> T parseResponse(Response<T> response) {
        if (response.isSuccessful())
            return response.body();
        else
            ErrorInterpreter.parse(response.code());

        return null;
    }

    private <T> boolean validateResponse(Response<T> response) {
        if (response.isSuccessful()) {
            return true;
        } else {
            ErrorInterpreter.parse(response.code());
            return false;
        }
    }

    private OkHttpClient getCacheClient() {
        if (context != null) {
            NetworkManager network = new NetworkManager();
            Cache cache = new Cache(context.getCacheDir(), 1024 * 1024 * 5);
            return new OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        Request newRequest;
                        if (network.isConntected(context))
                            newRequest = request.newBuilder().addHeader("Cache-Control", "public, max-age=" + 10).build(); // 10 sec
                        else
                            newRequest = request.newBuilder()
                                    .addHeader("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24).build(); // 1 day

                        return chain.proceed(newRequest);
                    }).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

}
