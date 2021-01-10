package com.example.autostudio;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "events"
//        indices = @Index("carId")
)
public class Event {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    private long eventId;
    private String category;
    private String name;
    private Date date;
    private String info;
    private Double cost;

    @ForeignKey(entity = Car.class, parentColumns = "car_id", childColumns = "event_id")
    @ColumnInfo(name = "car_id")
    private long carId;

    @Ignore
    public Event() {
    }

    @Ignore
    public Event(String category, String name, Date date, String info, Double cost, long carId) {
        this.category = category;
        this.name = name;
        this.date = date;
        this.info = info;
        this.cost = cost;
        this.carId = carId;
    }

    public Event(long eventId, String category, String name, Date date, String info, Double cost, long carId) {
        this.eventId = eventId;
        this.category = category;
        this.name = name;
        this.date = date;
        this.info = info;
        this.cost = cost;
        this.carId = carId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
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

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
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
