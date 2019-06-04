package com.example.haike.mytodolist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.api.UserApi;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class SignActivity extends AppCompatActivity {

    private TextInputEditText nomUser, preUser, emailUser, passUser, villeUser;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences prefs = getSharedPreferences("user_email_p", Context.MODE_PRIVATE);
        String email = prefs.getString("email", "");
        String passs = prefs.getString("pass", "");

        if(!email.isEmpty()) {
            gotToLoginActivity();
        }

        else {
            nomUser = findViewById(R.id.nom);
            preUser = findViewById(R.id.prenom);
            emailUser = findViewById(R.id.email);
            passUser = findViewById(R.id.password);
            villeUser = findViewById(R.id.ville);
            btnSign = findViewById(R.id.btnSign);

            btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nom = nomUser.getText().toString();
                    String prenom = preUser.getText().toString();
                    String email = emailUser.getText().toString();
                    String password = passUser.getText().toString();
                    String ville = villeUser.getText().toString();

                    retrofit2.Call<ResponseBody> call = UserApi.getInstance()
                            .getApi().createUser(nom, prenom, email, password, ville);


                    call.enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()) {
                                try {
                                    String res = response.body().string();

                                    gotToLoginActivity();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(SignActivity.this, "Error response" , Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }
            });
        }

    }

    private void gotToLoginActivity() {
        Intent intent = new Intent(SignActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ins_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnNext:
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
