package com.bigshark.smartlight.bean;

/**
 * Created by bigShark on 2017/1/14.
 */

public class Ride {

    private String time;
    private double distance;

    public Ride() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "time='" + time + '\'' +
                ", distance=" + distance +
                '}';
    }
}
