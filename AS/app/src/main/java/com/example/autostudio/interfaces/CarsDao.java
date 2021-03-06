package com.example.autostudio.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.autostudio.classes.Car;

import java.util.Date;
import java.util.List;

@Dao
public interface CarsDao {

    @Transaction
    @Insert
    long insertCar(Car car);

    @Query("Select * from cars")
    List<Car> getAll();

    @Query("Select * from cars where car_id=:carId")
    Car getCarById(long carId);

    @Query("Update cars set km=:km where car_id=:carId")
    void updateCarKmById(double km, long carId);

    @Query("Update cars set tankCapacity=:tankCapacity where car_id=:carId")
    void updateCarTankCapacityById(double tankCapacity, long carId);

    @Query("Update cars set expDateRCA=:expDateRCA where car_id=:carId")
    void updateCarRCAById(Date expDateRCA, long carId);

    @Query("Update cars set expDateITP=:expDateITP where car_id=:carId")
    void updateCarITPById(Date expDateITP, long carId);

    @Query("Delete from cars")
    void deleteAll();

    @Query("Delete from cars where car_id = :carId")
    void deleteEventById(long carId);

    @Query("Select * from cars where fuel=:fuel and km>=:km")
    List<Car> getCarsByFuelAndKm(String fuel, double km);
}
