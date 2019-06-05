package com.example.haike.mytodolist.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceEmitter {

    public ServiceEmitter() {

    }

    public static String getCurrentDay() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String[] dateArray = formatter.format(formatter).split("-");
        return dateArray[0];
    }
}
