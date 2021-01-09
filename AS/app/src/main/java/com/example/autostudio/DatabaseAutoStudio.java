package com.example.autostudio;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Car.class, Event.class}, version = 3, exportSchema = false)
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
