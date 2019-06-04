package com.example.haike.mytodolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.adapter.TodoAdapter;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.util.List;

public class ListTodoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.todo_rec);

        dbHandler = new DbHandler(ListTodoActivity.this);

        dbHandler.openDB();

        List<Todo> todoList = dbHandler.getAllTodo();

        // Recycle settings
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        TodoAdapter adapter = new TodoAdapter(ListTodoActivity.this, todoList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.btnAdd) {
            Intent intent = new Intent(ListTodoActivity.this, TodoForm.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.btnQuotess) {
            Intent intent = new Intent(ListTodoActivity.this, QuoteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.btnTimer) {
            Intent intent = new Intent(ListTodoActivity.this, TimerActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
