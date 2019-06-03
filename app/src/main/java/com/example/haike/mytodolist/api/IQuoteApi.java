package com.example.haike.mytodolist.api;

import com.example.haike.mytodolist.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IQuoteApi {

    @GET("quotes")
    Call<List<Quote>> getQuotes();

    @GET("quotes/rand")
    Call<Quote> getRandQuote();
}
