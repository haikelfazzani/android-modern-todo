package com.example.haike.mytodolist.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApi {

    private static final String BASE_URL = "https://instagr-server.herokuapp.com/users/";
    private static UserApi userApi;
    private Retrofit retrofit;

    public UserApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized UserApi getInstance() {
        if(userApi == null) {
            userApi = new UserApi();
        }
        return userApi;
    }

    public IUserApi getApi() {
        return retrofit.create(IUserApi.class);
    }
}
