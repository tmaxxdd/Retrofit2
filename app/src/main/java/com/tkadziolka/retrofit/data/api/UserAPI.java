package com.tkadziolka.retrofit.data.api;

import com.tkadziolka.retrofit.data.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    @GET("users/")
    Call<List<User>> users();

    @GET("users/{id}")
    Call<User> user(@Path("id") int id);

    @FormUrlEncoded
    @POST("users/")
    Call<ResponseBody> addUser(
            @Field("name") String name,
            @Field("job") String job
    );

    @FormUrlEncoded
    @PUT("users/{id}")
    Call<ResponseBody> updateUser(
            @Path("id") int id,
            @Field("name") String name,
            @Field("job") String job
    );

    @DELETE("users/{id}")
    Call<ResponseBody> deleteUser(@Path("id") int id);
}
