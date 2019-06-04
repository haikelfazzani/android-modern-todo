package com.example.haike.mytodolist.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.haike.mytodolist.R;

public class QuotePreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtQuoteText, txtQuoteAuthor;
    private Button btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_preview);

        txtQuoteText = findViewById(R.id.txtQuoteText);
        txtQuoteAuthor = findViewById(R.id.txtQuoteAuthor);
        btnRetour = findViewById(R.id.btnRetour);

        Intent intent = getIntent();
        txtQuoteText.setText(intent.getStringExtra("text-quote"));
        txtQuoteAuthor.setText(intent.getStringExtra("author-quote"));

        btnRetour.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRetour:
                Intent intent = new Intent(QuotePreviewActivity.this, QuoteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
