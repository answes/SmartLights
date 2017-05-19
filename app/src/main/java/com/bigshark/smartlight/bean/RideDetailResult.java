package com.bigshark.smartlight.bean;

/**
 * Created by jlbs1 on 2017/5/19.
 */

public class RideDetailResult {
    private int code;
    private String extra;
    private RideDetail data;

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

    public RideDetail getData() {
        return data;
    }

    public void setData(RideDetail data) {
        this.data = data;
    }

    public class RideDetail {
        private String id;
        private String user_id;
        private String date;
        private String maxspeed;    //最大速度
        private String avgspeed;    //平均速度
        private String distance;
        private String time;
        private String height;
        private String heat;
        private String cre_tm;
        private String tel;
        private String gps;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getMaxspeed() {
            return maxspeed;
        }

        public void setMaxspeed(String maxspeed) {
            this.maxspeed = maxspeed;
        }

        public String getAvgspeed() {
            return avgspeed;
        }

        public void setAvgspeed(String avgspeed) {
            this.avgspeed = avgspeed;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHeat() {
            return heat;
        }

        public void setHeat(String heat) {
            this.heat = heat;
        }

        public String getCre_tm() {
            return cre_tm;
        }

        public void setCre_tm(String cre_tm) {
            this.cre_tm = cre_tm;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getGps() {
            return gps;
        }

        public void setGps(String gps) {
            this.gps = gps;
        }
    }
}
