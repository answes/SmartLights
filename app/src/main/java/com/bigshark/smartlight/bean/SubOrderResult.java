package com.bigshark.smartlight.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ch on 2017/5/14.
 *
 * @email 869360026@qq.com
 */

public class SubOrderResult {

    /**
     * code : 1
     * data : 104
     * extra : 添加订单成功
     */

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private int data;
    @SerializedName("extra")
    private String extra;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
