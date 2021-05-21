package com.example.haike.mytodolist.services;


import com.example.haike.mytodolist.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserApi {
    @FormUrlEncoded
    @POST("login")
    Call<List<User>> getUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> createUser(
            @Field("nom") String nom,
            @Field("prenom") String prenom,
            @Field("email") String email,
            @Field("password") String password,
            @Field("ville") String ville
    );

}
