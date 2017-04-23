package com.bigshark.smartlight.bean;

import android.app.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class Address {

    private int code;
    private String extra;
    private List<AddressBean> data;

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

    public  List<AddressBean> getData() {
        return data;
    }

    public void setData( List<AddressBean> data) {
        this.data = data;
    }
}
