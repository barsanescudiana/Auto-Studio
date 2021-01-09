package com.example.autostudio;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventsDao {
    @Insert
    void insert(Event event);

    @Query("Select * from events where carId=:carId")
    List<Event> getAll(int carId);

    @Query("Select * from events where eventId=:eventId")
    Event getEventById(long eventId);

    @Query("Delete from events where carId=:carId")
    void deleteAll(int carId);

    @Query("Delete from events where eventId=:eventId")
    void deleteEventById(long eventId);
}
