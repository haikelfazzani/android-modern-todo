package com.example.haike.mytodolist.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.adapter.TodoAdapter;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.util.List;

public class ListTodoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TodoAdapter adapter;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Liste des todos");

        recyclerView = findViewById(R.id.todo_rec);

        dbHandler = new DbHandler(ListTodoActivity.this);

        dbHandler.openDB();

        List<Todo> todoList = dbHandler.getAllTodo();

        // Recycle settings
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        adapter = new TodoAdapter(ListTodoActivity.this, todoList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

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
        if (id == R.id.btnShop) {
            Intent intent = new Intent(ListTodoActivity.this, ShopActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.btnSupprimer) {
            SharedPreferences prefs = getSharedPreferences("user_email_p", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(ListTodoActivity.this, LoginActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
