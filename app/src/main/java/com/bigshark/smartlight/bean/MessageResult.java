package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class MessageResult {
    private int code;
    private List<Messge> data;
    private String extra;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Messge> getData() {
        return data;
    }

    public void setData(List<Messge> data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
