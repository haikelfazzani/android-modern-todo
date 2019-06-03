package com.example.haike.mytodolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.haike.mytodolist.activity.TodoForm;
import com.example.haike.mytodolist.api.QuoteApi;
import com.example.haike.mytodolist.model.Quote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private TextView txtSplashText, getTxtSplashAuthor;

    private static int SPLASH_TIME_OUT = 10000;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtSplashText =findViewById(R.id.txtSplashText);
        getTxtSplashAuthor =findViewById(R.id.txtSplashAuthor);
        progressBar = findViewById(R.id.progressBar);

        Call<Quote> quoteCall = QuoteApi.getInstance().getApi().getRandQuote();

        quoteCall.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                if(response.isSuccessful()) {
                    Quote quote = response.body();
                    txtSplashText.setText(quote.getTextQuote());
                    getTxtSplashAuthor.setText(quote.getAuthor());
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doJob();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void doJob() throws InterruptedException {
        for (int i = 10 ; i <= 100 ; i+= 10) {

            Thread.sleep(1000);
            progressBar.setProgress(i);
            if(i >= 100) {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
