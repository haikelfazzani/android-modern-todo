package com.example.haike.mytodolist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.config.DbChrono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private Chronometer chronometer;
    private Button btnStart, btnStop, btnReset, btnClear;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    private boolean running = false;
    private long pauseOffset;

    private DbChrono dbChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbChrono = new DbChrono(TimerActivity.this);

        chronometer = findViewById(R.id.txtTimer);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);

        btnClear = findViewById(R.id.btnClear);
        listView = findViewById(R.id.listTimers);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        btnClear.setOnClickListener(this);

        dbChrono.openDB();

        List<String> stringList = dbChrono.getAllTimers();

        if(stringList.size() > 0) {
            Collections.reverse(stringList);
            adapter = new ArrayAdapter<>(TimerActivity.this,
                    android.R.layout.simple_list_item_1, stringList);
            listView.setAdapter(adapter);
        }

        dbChrono.closeDB();

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
            Intent intent = new Intent(TimerActivity.this, TodoForm.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.btnQuotess) {
            Intent intent = new Intent(TimerActivity.this, QuoteActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.btnTimer) {
            Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnStart:
                if(!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    running = true;
                }
                break;

            case R.id.btnStop:
                if(running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                }
                break;

            case R.id.btnReset:
                String txtTimer = chronometer.getText().toString();

                dbChrono.openDB();
                dbChrono.addTimer(txtTimer);
                adapter.add(txtTimer);
                adapter.notifyDataSetChanged();
                dbChrono.closeDB();

                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                break;

            case R.id.btnClear:
                AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
                        builder.setTitle("Suppression")
                                .setMessage("Etes vous sure ?");
                        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbChrono.openDB();
                                dbChrono.deleteAll();
                                dbChrono.closeDB();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }
}
