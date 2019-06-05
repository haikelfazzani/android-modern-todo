package com.example.haike.mytodolist.api;


import com.example.haike.mytodolist.model.Quote;
import com.example.haike.mytodolist.model.Shop;
import com.example.haike.mytodolist.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IShopApi {

    @GET("cards")
    Call<List<Shop>> getShops();

}
