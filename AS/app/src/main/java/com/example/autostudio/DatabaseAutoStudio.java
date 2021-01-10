package com.example.autostudio;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.autostudio.classes.Car;
import com.example.autostudio.classes.CarsDao;
import com.example.autostudio.classes.DateConverter;
import com.example.autostudio.classes.Event;
import com.example.autostudio.classes.EventsDao;

@Database(entities = {Car.class, Event.class}, version = 13, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class DatabaseAutoStudio extends RoomDatabase {
    private final static String DB_NAME = "dbAutoStudio.db";
    private static DatabaseAutoStudio instance;

    public static DatabaseAutoStudio getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseAutoStudio.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract CarsDao getCarsDao();
    public abstract EventsDao getEventsDao();
}
