package com.aariyan.noteme.Constant;

import android.view.View;


import com.aariyan.noteme.Model.TaskModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Constant {

    public static String[] taskStatus = {"Open","In-Progress","Test","Done"};
    public static List<TaskModel> listOfTask = new ArrayList<>();
    public static View rootView;

    //For getting today's date
    public static String todayDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
