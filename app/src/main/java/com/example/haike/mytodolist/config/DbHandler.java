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

public class DbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME="mytodo";

    private static final String TABLE_NAME="todo";
    private static final String KEY_ID="id" ;
    private static final String KEY_TITLE="title" ;
    private static final String KEY_DESC="description" ;
    private static final String KEY_DATE="date" ;
    private static final String KEY_TIME="time" ;

    private final static String CREATE_TABLE= "CREATE TABLE "+TABLE_NAME+
            " ("+KEY_ID+" integer primary key autoincrement," +
            KEY_TITLE +" text not null, " +
            KEY_DESC + " text, " + KEY_DATE + " text, " + KEY_TIME + " text)";

    private SQLiteDatabase myDB;

    public DbHandler(Context ctx) {
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

    public long addTodo(Todo todo) {
        myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, todo.getTitle());
        contentValues.put(KEY_DESC, todo.getDesc());
        contentValues.put(KEY_DATE, todo.getDate());
        contentValues.put(KEY_TIME, todo.getTime());

        return myDB.insert(TABLE_NAME, null, contentValues);
    }

    public List<Todo> getAllTodo() {
        List<Todo> todoList = new ArrayList<>();
        myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        if(cursor.getCount() == 0) return new ArrayList<>(0);
        cursor.moveToFirst();

        while(cursor.moveToNext()) {
            Todo todo = new Todo();
            todo.setId(Integer.parseInt(cursor.getString(0)));
            todo.setTitle(cursor.getString(1));
            todo.setDesc(cursor.getString(2));
            todo.setDate(cursor.getString(3));
            todo.setTime(cursor.getString(4));
            todoList.add(todo);
        }

        cursor.close();
        Collections.reverse(todoList);
        return todoList;
    }

    /*public void updateContact(Todo contact) {
        myDB = this.getWritableDatabase();
        String sql = "UPDATE "+TABLE_NAME+" SET "+KEY_TEL +" = ? WHERE " +KEY_NAME +" = ?";
        Cursor cursor = myDB.rawQuery(sql,
                new String[]{contact.getTel(), contact.getName()});
        cursor.moveToFirst();
    }*/

    public void deleteTodo(int id) {
        myDB = this.getWritableDatabase();
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE id "+ " = ?";
        Cursor cursor = myDB.rawQuery(sql, new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        cursor.close();
    }


}
