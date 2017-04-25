package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by bigShark on 2017/1/14.
 */

public class Ride {


    private int code;
    private String extra;
    private List<Bike> data;


    public Ride() {
    }

    public static class Bike {
        private String id;
        private String cre_tm;
        private String distance;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCre_tm() {
            return cre_tm;
        }

        public void setCre_tm(String cre_tm) {
            this.cre_tm = cre_tm;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public  List<Bike> getData() {
        return data;
    }

    public void setData( List<Bike> data) {
        this.data = data;
    }
}
