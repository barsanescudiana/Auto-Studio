package com.example.autostudio;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface EventsDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event event);

    @Query("Select * from events")
    List<Event> getAll();

    @Query("Select * from events where car_id=:carId")
    List<Event> getAll(long carId);

    @Query("Select * from events where event_id=:eventId")
    Event getEventById(long eventId);

    @Query("Delete from events where car_id=:carId")
    void deleteAllByCarId(long carId);

    @Query("Delete from events")
    void deleteAll();

    @Query("Delete from events where event_id=:eventId")
    void deleteEventById(long eventId);
}
