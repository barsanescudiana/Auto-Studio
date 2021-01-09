package com.example.autostudio;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "events",
        foreignKeys = @ForeignKey(entity = Car.class, parentColumns = "carId", childColumns = "eventId"),
        indices = @Index("carId")
)
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int eventId;
    private String name;
    private Date date;
    private int carId;

    @Ignore
    public Event() {
    }

    @Ignore
    public Event(String name, Date date, int carId) {
        this.name = name;
        this.date = date;
        this.carId = carId;
    }

    public Event(int eventId, String name, Date date, int carId) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.carId = carId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
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

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", carID=" + carId +
                '}';
    }
}
