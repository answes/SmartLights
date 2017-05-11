package com.bigshark.smartlight.bean;

/**
 * Created by jlbs1 on 2017/5/10.
 */

public class SQLUser {
    private String phone;
    private String password;


    public SQLUser(String phone, String password){
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
