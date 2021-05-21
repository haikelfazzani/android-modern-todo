package com.example.haike.mytodolist.api;

import com.example.haike.mytodolist.model.Shop;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface IShopApi {
    @GET("all")
    Call<List<Shop>> getShops();
}
