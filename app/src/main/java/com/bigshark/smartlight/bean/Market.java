package com.bigshark.smartlight.bean;

/**
 * Created by bigShark on 2016/12/28.
 */

public class Market {
    private int id;
    private String minuteName;
    private String minuteImgUrl;

    public Market() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMinuteName() {
        return minuteName;
    }

    public void setMinuteName(String minuteName) {
        this.minuteName = minuteName;
    }

    public String getMinuteImgUrl() {
        return minuteImgUrl;
    }

    public void setMinuteImgUrl(String minuteImgUrl) {
        this.minuteImgUrl = minuteImgUrl;
    }

    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", minuteName='" + minuteName + '\'' +
                ", minuteImgUrl='" + minuteImgUrl + '\'' +
                '}';
    }
}
