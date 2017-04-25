package com.bigshark.smartlight.bean;

/**
 * Created by ch on 2017/2/21.
 *
 * @email 869360026@qq.com
 */

public class RegisterResult {
    private int code;
    private String data;
    private String extra;

    public RegisterResult() {

    }

    public RegisterResult(int code, String data, String extra) {
        this.code = code;
        this.data = data;
        this.extra = extra;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
