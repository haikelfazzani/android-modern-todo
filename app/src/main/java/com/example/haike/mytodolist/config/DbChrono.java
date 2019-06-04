package com.example.haike.mytodolist.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.haike.mytodolist.model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbChrono extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;

    private static final String DATABASE_NAME="chrono";

    private static final String TABLE_NAME="todo";
    private static final String KEY_ID="id" ;
    private static final String TIMER ="timer" ;

    private final static String CREATE_TABLE= "CREATE TABLE "+TABLE_NAME+
            " ("+KEY_ID+" integer primary key autoincrement," +
            TIMER +" text not null " +")";

    private SQLiteDatabase myDB;

    public DbChrono(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table " + DATABASE_NAME);
            this.onCreate(db);
        }
    }

    public SQLiteDatabase openDB() {
        myDB = this.getWritableDatabase();
        return myDB;
    }

    public void closeDB() { myDB.close(); }

    public long addTimer(String txtTimer) {
        myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMER, txtTimer);

        return myDB.insert(TABLE_NAME, null, contentValues);
    }

    public List<String> getAllTimers() {
        List<String> timerList = new ArrayList<>();
        myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if(cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();

        while(cursor.moveToNext()) {
            timerList.add(cursor.getString(1));
        }

        cursor.close();
        Collections.reverse(timerList);
        return timerList;
    }


    public void deleteTimer(int id) {
        myDB = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE id "+ " = ?";
        Cursor cursor = myDB.rawQuery(sql, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        cursor.close();
    }

    public void deleteAll() {
        myDB = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_NAME;
        Cursor cursor = myDB.rawQuery(sql, new String[]{});
        cursor.moveToFirst();
        cursor.close();
    }
}
