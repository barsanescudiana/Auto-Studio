package com.example.autostudio;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Car.class, Event.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class CarsDB extends RoomDatabase {
    private final static String DB_NAME = "cars.db";
    private static CarsDB instance;

    public static CarsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CarsDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract CarsDao getCarsDao();
}
