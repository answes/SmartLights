package com.bigshark.smartlight.bean;

import java.io.Serializable;

/**
 * Created by ch on 2017/4/17.
 *
 * @email 869360026@qq.com
 */

public class UpLoadRecord implements Serializable {
    private float maxSpeed = 0;//最大速度 km/h
    private long time;//用时 h
    private double distance = 0;//距离 单位KM
    private double height = 0;//海拔 单位M
    private double avSpeed;
    private double k;
    private double speed;//当前速度
    private String allspeed;    //速度详情

    public String getAllspeed() {
        return allspeed;
    }

    public void setAllspeed(String allspeed) {
        this.allspeed = allspeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private String gps;

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getAvSpeed() {
        return avSpeed;
    }

    public void setAvSpeed(double avSpeed) {
        this.avSpeed = avSpeed;
    }

    public UpLoadRecord() {
    }

    public UpLoadRecord(float maxSpeed, long time, double distance, double height) {

        this.maxSpeed = maxSpeed;
        this.time = time;
        this.distance = distance;
        this.height = height;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
