package com.example.autostudio;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarsDao {
    @Insert
    void insert(Car car);

    @Query("Select * from cars")
    List<Car> getAll();

    @Query("Select * from cars where carId=:carId")
    Car getCarById(long carId);

    @Query("Delete from cars")
    void deleteAll();

    @Query("Delete from cars where carId = :carId")
    void deleteEventById(long carId);
}
