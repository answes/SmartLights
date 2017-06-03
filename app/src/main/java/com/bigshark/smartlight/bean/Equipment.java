package com.bigshark.smartlight.bean;

/**
 * Created by bigShark on 2017/1/14.
 */

public class Equipment {

    private String name;
    private String numbering;

    public Equipment() {

    }

    public Equipment(String name, String numbering) {
        this.name = name;
        this.numbering = numbering;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumbering() {
        return numbering;
    }

    public void setNumbering(String numbering) {
        this.numbering = numbering;
    }
}
