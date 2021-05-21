package com.example.haike.mytodolist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.services.UserApi;
import com.example.haike.mytodolist.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailUser , passUser;
    private Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emailUser = findViewById(R.id.email);
        passUser = findViewById(R.id.password);
        btnSign = findViewById(R.id.btnSign);

        SharedPreferences prefs = getSharedPreferences("user_email_p", Context.MODE_PRIVATE);
        String emaill = prefs.getString("email", "");
        String passs = prefs.getString("pass", "");

        if(!emaill.isEmpty()) {
            emailUser.setText(emaill);
            passUser.setText(passs);
        }

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = emailUser.getText().toString();
                final String pass = passUser.getText().toString();

                final Call<List<User>> user = UserApi.getInstance().getApi().getUser(email, pass);

                user.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful()) {
                            List<User> userList = response.body();

                            if (userList.size() > 0) {
                                int userID = userList.get(0).getId();
                                saveUser(email, pass,userID, userList.get(0).getNom());
                                goToRendList();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Account does not exist", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        });

    }

    public void goToRendList() {
        Intent intent = new Intent(LoginActivity.this, ListTodoActivity.class);
        startActivity(intent);
    }

    private void saveUser(String email, String pass,int userId, String userNom) {
        SharedPreferences preferences = getSharedPreferences("user_email_p" ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(!email.isEmpty() && !pass.isEmpty()) {
            editor.putString("email", email);
            editor.putString("pass", pass);
            editor.putString("user_id", Integer.toString(userId));
            editor.putString("nom", userNom);
            editor.apply();
        }
    }

}
