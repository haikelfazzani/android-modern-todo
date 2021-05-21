package com.example.haike.mytodolist.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteApi {

        private static final String BASE_URL = "http://192.168.56.1:3020/quote/";
        private static QuoteApi quoteApi;
        private Retrofit retrofit;

        public QuoteApi() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static synchronized QuoteApi getInstance() {
            if(quoteApi == null) {
                quoteApi = new QuoteApi();
            }
            return quoteApi;
        }

        public IQuoteApi getApi() {
            return retrofit.create(IQuoteApi.class);
        }

}
