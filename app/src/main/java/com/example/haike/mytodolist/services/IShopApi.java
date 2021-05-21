package com.example.haike.mytodolist.services;

import com.example.haike.mytodolist.model.Shop;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IShopApi {
    @GET("all")
    Call<List<Shop>> getShops();
}
