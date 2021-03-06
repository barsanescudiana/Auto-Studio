package com.example.autostudio.classes;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "cars")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    private long carId;
    private long userId;
    private String brand;
    private String model;
    private String fuel;
    private double km;
    private String color;
    private int engineCapacity;
    private int engineOutput;
    private double avgConsumption;
    private Date expDateRCA;
    private Date expDateITP;
    private Double tankCapacity;

    @Ignore
    public Car() {

    }

    @Ignore
    public Car(long userId, String brand, String model, String fuel, double km, String color, int engineCapacity, int engineOutput, double avgConsumption, Date expDateRCA, Date expDateITP, double tankCapacity) {
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.km = km;
        this.color = color;
        this.engineCapacity = engineCapacity;
        this.engineOutput = engineOutput;
        this.avgConsumption = avgConsumption;
        this.expDateRCA = new Date(expDateRCA.toString());
        this.expDateITP = new Date(expDateITP.toString());
        this.tankCapacity = tankCapacity;
    }


    public Car(long carId, long userId, String brand, String model, String fuel, double km, String color, int engineCapacity, int engineOutput, double avgConsumption, Date expDateRCA, Date expDateITP, double tankCapacity) {
        this.carId = carId;
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.km = km;
        this.color = color;
        this.engineCapacity = engineCapacity;
        this.engineOutput = engineOutput;
        this.avgConsumption = avgConsumption;
        this.expDateRCA = new Date(expDateRCA.toString());
        this.expDateITP = new Date(expDateITP.toString());
        this.tankCapacity = tankCapacity;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getUserId() {
        return userId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getEngineOutput() {
        return engineOutput;
    }

    public void setEngineOutput(int engineOutput) {
        this.engineOutput = engineOutput;
    }

    public double getAvgConsumption() {
        return avgConsumption;
    }

    public void setAvgConsumption(double avgConsumption) {
        this.avgConsumption = avgConsumption;
    }

    public Date getExpDateRCA() {
        return expDateRCA;
    }

    public void setExpDateRCA(Date expDateRCA) {
        this.expDateRCA = expDateRCA;
    }

    public Date getExpDateITP() {
        return expDateITP;
    }

    public void setExpDateITP(Date expDateITP) {
        this.expDateITP = expDateITP;
    }

    public Double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(Double tankCapacity) {
        this.tankCapacity = tankCapacity;
    }

    public String getDetails() {
        String motor = getEngineCapacity() / 1000 + "." + getEngineCapacity() / 100 % 10;
        String carDetails = getBrand() + " " + getModel() + " " + motor + " " + getEngineOutput() + "hp " + getFuel() + " " + getColor();
        return carDetails;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", fuel='" + fuel + '\'' +
                ", km=" + km +
                ", color='" + color + '\'' +
                ", engineCapacity=" + engineCapacity +
                ", engineOutput=" + engineOutput +
                ", avgConsumption=" + avgConsumption +
                ", expDateRCA=" + expDateRCA +
                ", expDateITP=" + expDateITP +
                ", tankCapacity=" + tankCapacity +
                '}';
    }
}
