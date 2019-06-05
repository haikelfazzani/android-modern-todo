package com.example.haike.mytodolist.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.adapter.ShopAdapter;
import com.example.haike.mytodolist.api.ShopApi;
import com.example.haike.mytodolist.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rec_shop);

        Call<List<Shop>> listCall = ShopApi.getInstance().getApi().getShops();

        listCall.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                if(response.isSuccessful()) {

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    List<Shop> shopList = response.body();
                    ShopAdapter adapter = new ShopAdapter(ShopActivity.this, shopList);

                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(
                            new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {

            }
        });

    }
}
