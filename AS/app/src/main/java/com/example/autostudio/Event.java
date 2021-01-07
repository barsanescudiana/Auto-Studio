package com.example.autostudio;

import java.util.Date;

public class Event {
    private String name;
    private Date date;
    private int carID;

    public Event() { }

    public Event(String name, Date date, int carID) {
        this.name = name;
        this.date = date;
        this.carID = carID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }
}
