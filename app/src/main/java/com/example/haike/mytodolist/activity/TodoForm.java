package com.example.haike.mytodolist.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TodoForm extends AppCompatActivity implements View.OnClickListener {

    private DbHandler dbHandler;

    private ImageView imgList;
    private TextInputEditText txtTitre, txtDesc, txtDate, txtTime;
    private Button btnAjouter;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);

        txtTitre = findViewById(R.id.txtTitre);
        txtDesc = findViewById(R.id.txtDesc);
        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);

        imgList = findViewById(R.id.imgList);

        btnAjouter = findViewById(R.id.btnAjouter);

        btnAjouter.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        imgList.setOnClickListener(this);

        datePickerListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM-dd-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                txtDate.setText(sdf.format(myCalendar.getTime()));
            }

        };
    }

    private boolean validateInput(String input) {
        return !input.isEmpty() && input.length() > 2;
    }

    @Override
    public void onClick(View v) {

        String myTxtTitre = txtTitre.getText().toString();
        String myTxtDesc = txtDesc.getText().toString();
        String myTxtDate = txtDate.getText().toString();
        String myTxtTime = txtTime.getText().toString();

        switch (v.getId()) {
            case R.id.btnAjouter:
                if(validateInput(myTxtTitre) && validateInput(myTxtDesc) && (validInteger(myTxtTime))) {
                    dbHandler = new DbHandler(TodoForm.this);
                    dbHandler.openDB();

                    Todo todo = new Todo();
                    todo.setTitle(myTxtTitre);
                    todo.setDesc(myTxtDesc);
                    todo.setDate(myTxtDate);
                    todo.setTime(myTxtTime + " h");

                    dbHandler.addTodo(todo);
                    dbHandler.closeDB();

                    clearEditText();
                    redirectToList();
                }
                else {
                    Toast.makeText(TodoForm.this, "il faut remplir tous les champs !",
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txtDate:

                new DatePickerDialog(TodoForm.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;

            case R.id.imgList:
                Intent intent = new Intent(TodoForm.this, ListTodoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean validInteger(String input) {
        return input.matches("\\d+");
    }

    public void clearEditText() {
        txtTitre.setText("");
        txtDesc.setText("");
        txtDate.setText("");
        txtTime.setText("");
    }

    public void redirectToList() {
        Intent intent = new Intent(TodoForm.this, ListTodoActivity.class);
        startActivity(intent);
    }
}
