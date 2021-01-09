package com.example.autostudio;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "events",
        foreignKeys = @ForeignKey(entity = Car.class, parentColumns = "carId", childColumns = "eventId")
//        indices = @Index("carId")
)
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int eventId;
    private String category;
    private String name;
    private Date date;
    private String info;
    private Double cost;
    private String carId;

    @Ignore
    public Event() {
    }

    @Ignore
    public Event(String category, String name, Date date, String info, Double cost, String carId) {
        this.category = category;
        this.name = name;
        this.date = date;
        this.info = info;
        this.cost = cost;
        this.carId = carId;
    }

    public Event(int eventId, String category, String name, Date date, String info, Double cost, String carId) {
        this.eventId = eventId;
        this.category = category;
        this.name = name;
        this.date = date;
        this.info = info;
        this.cost = cost;
        this.carId = carId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getInfo() {
        if ("Service Visit".equals(category)) {
            info += "\n" + category + ": " + name + "\nDate: " + date + "\nCost: " + cost;
        } else {
            info += "\n" + category + " - Date: " + date + "\nCost: " + cost;
        }

        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID" + eventId +
                " name='" + name + '\'' +
                ", date=" + date +
                ", carID=" + carId +
                '}';
    }
}
