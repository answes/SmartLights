package com.bigshark.smartlight.bean;

/**
 * Created by jlbs1 on 2017/6/9.
 */

public class Speed {
    private String speed;
    private int time;

    public Speed(String speed, int time) {
        this.speed = speed;
        this.time = time;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
