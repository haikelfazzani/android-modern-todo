package com.example.haike.mytodolist.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.haike.mytodolist.R;
import com.example.haike.mytodolist.adapter.TodoAdapter;
import com.example.haike.mytodolist.config.DbHandler;
import com.example.haike.mytodolist.model.Todo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodoForm extends AppCompatActivity implements View.OnClickListener {

    private DbHandler dbHandler;

    private RelativeLayout relativeLayout;
    private ImageView imgList;
    private TextInputEditText txtTitre, txtDesc, txtDate;
    private Spinner txtTime;
    private Button btnAjouter;

    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_form);

        relativeLayout = findViewById(R.id.layoutTodoForm);
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
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                txtDate.setText(formatDate(sdf.format(myCalendar.getTime())));
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
        String myTxtTime = txtTime.getSelectedItem().toString();

        switch (v.getId()) {
            case R.id.btnAjouter:
                if(validateInput(myTxtTitre) && validateInput(myTxtDesc) ) {
                    dbHandler = new DbHandler(TodoForm.this);
                    dbHandler.openDB();

                    Todo todo = new Todo();
                    todo.setTitle(myTxtTitre);
                    todo.setDesc(myTxtDesc);
                    todo.setDate(myTxtDate);
                    todo.setTime(myTxtTime);

                    dbHandler.addTodo(todo);
                    dbHandler.closeDB();

                    clearEditText();
                    redirectToList();
                }
                else {
                    //Toast.makeText(TodoForm.this, "il faut remplir tous les champs !",
                      //      Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar
                            .make(relativeLayout, "il faut remplir tous les champs !", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
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
    }

    public void redirectToList() {
        Intent intent = new Intent(TodoForm.this, ListTodoActivity.class);
        startActivity(intent);
    }


    public String formatDate(String input) {

        String[] list = new String[] {
                "janv",	"févr",	"mars",	"avr",	"mai",	"juin",
                "juil", "août","sept" , "oct",	"nov",	"déc"
        };

        String[] date = input.split("-");

        return date[1] +" "+ list[Integer.parseInt(date[0])-1] +" "+ date[2];
    }
}
