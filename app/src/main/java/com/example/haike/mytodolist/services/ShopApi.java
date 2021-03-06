package com.example.haike.mytodolist.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShopApi {

    private static final String BASE_URL = "https://instagr-server.herokuapp.com/movies/";
    private static ShopApi shopApi;
    private Retrofit retrofit;

    public ShopApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ShopApi getInstance() {
        if(shopApi == null) {
            shopApi = new ShopApi();
        }
        return shopApi;
    }

    public IShopApi getApi() {
        return retrofit.create(IShopApi.class);
    }
}
