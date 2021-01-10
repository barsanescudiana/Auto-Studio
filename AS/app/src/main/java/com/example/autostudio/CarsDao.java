package com.example.autostudio;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CarsDao {

    @Transaction
    @Insert
    long insertCar(Car car);

//    @Insert
//    void insertEvents(List<Event> events);

    @Query("Select * from cars")
    List<Car> getAll();

    @Query("Select * from cars where car_id=:carId")
    Car getCarById(long carId);

    @Query("Delete from cars")
    void deleteAll();

    @Query("Delete from cars where car_id = :carId")
    void deleteEventById(long carId);
}
