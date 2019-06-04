package com.example.haike.mytodolist.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

public class TodoPreviewActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private EditText txtTitle, txtDesc, txtDate, txtTime;
    private Button btnUpdate;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_preview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHandler = new DbHandler(TodoPreviewActivity.this);

        relativeLayout = findViewById(R.id.layoutTodoPrev);
        txtTitle = findViewById(R.id.todoTitle);
        txtDesc = findViewById(R.id.todoDesc);
        txtDate = findViewById(R.id.todoDate);
        txtTime = findViewById(R.id.todoTime);

        btnUpdate = findViewById(R.id.btnUpdate);

        Intent intent = getIntent();

        final int id = Integer.parseInt(intent.getStringExtra("todo-id"));
        String title = intent.getStringExtra("todo-title");
        String desc = intent.getStringExtra("todo-desc");
        String date = intent.getStringExtra("todo-date");
        String time = intent.getStringExtra("todo-time");

        Todo todo = new Todo(title, desc, date, time);
        todo.setId(id);

        txtTitle.setText(title);
        txtDesc.setText(desc);
        txtDate.setText(date);
        txtTime.setText(time);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo myTodo = new Todo(
                        txtTitle.getText().toString(),
                        txtDesc.getText().toString(),
                        txtDate.getText().toString(),
                        txtTime.getText().toString()
                );
                myTodo.setId(id);

                dbHandler.openDB();
                dbHandler.updateTodo(myTodo);
                dbHandler.closeDB();

                Toast.makeText(TodoPreviewActivity.this ,"Votre todo a été modifiée", Toast.LENGTH_LONG)
                        .show();
            }
        });

    }


}
