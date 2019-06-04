package com.example.haike.mytodolist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.adapter.QuoteAdapter;
import com.example.haike.mytodolist.api.QuoteApi;
import com.example.haike.mytodolist.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuoteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Liste des citations");

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rec_quote);

        retrofit2.Call<List<Quote>> call = QuoteApi.getInstance().getApi().getQuotes();

        call.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {

                if(response.isSuccessful()) {

                    List<Quote> quoteList = response.body();

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    QuoteAdapter adapter = new QuoteAdapter(recyclerView.getContext(), quoteList);
                    recyclerView.setAdapter(adapter);

                    recyclerView.addItemDecoration(
                            new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                }

            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                Toast.makeText(QuoteActivity.this, "BAD", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
