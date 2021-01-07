package com.example.autostudio;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class EventsDB extends RoomDatabase {
    private final static String DB_NAME = "events.db";
    private static EventsDB instance;

    public static EventsDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, EventsDB.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract EventsDao getEventsDao();
}
