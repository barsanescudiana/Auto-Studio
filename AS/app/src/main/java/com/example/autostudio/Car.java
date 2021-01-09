package com.example.autostudio;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "cars")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int carId;
    private int userId;
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
    private double tankCapacity;

    @Ignore
    public Car() {

    }

    @Ignore
    public Car(String brand, String model, String fuel, double km, String color, int engineCapacity, int engineOutput,
               double avgConsumption, Date expDateRCA, Date expDateITP) {
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.km = km;
        this.color = color;
        this.engineCapacity = engineCapacity;
        this.engineOutput = engineOutput;
        this.avgConsumption = avgConsumption;
        this.expDateRCA = new Date(expDateRCA.getYear() - 1900, expDateRCA.getMonth(), expDateRCA.getDay());
        this.expDateITP = new Date(expDateITP.getYear() - 1900, expDateITP.getMonth(), expDateITP.getDay());
    }

    public Car(int carId, String brand, String model, String fuel, double km, String color, int engineCapacity, int engineOutput,
               double avgConsumption, Date expDateRCA, Date expDateITP) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.km = km;
        this.color = color;
        this.engineCapacity = engineCapacity;
        this.engineOutput = engineOutput;
        this.avgConsumption = avgConsumption;
        this.expDateRCA = new Date(expDateRCA.getYear() - 1900, expDateRCA.getMonth(), expDateRCA.getDay());
        this.expDateITP = new Date(expDateITP.getYear() - 1900, expDateITP.getMonth(), expDateITP.getDay());
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
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

    public double getTankCapacity() {
        return tankCapacity;
    }

    public void setTankCapacity(double tankCapacity) {
        this.tankCapacity = tankCapacity;
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
