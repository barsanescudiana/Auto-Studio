package com.example.autostudio.classes;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DateConverter {
    @TypeConverter
    public static String fromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String newDate = sdf.format(date);
        return newDate != null ? newDate : "";
    }

    @TypeConverter
    public static Date fromString(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date newDate = null;
        try {
            newDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
