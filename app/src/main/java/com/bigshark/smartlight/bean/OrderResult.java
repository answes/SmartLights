package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class OrderResult {
    private int code;
    private Order data;
    private String extra;

    public static  class Order{
        private int id;
        private String username;
        private String address;
        private String tel;
        private List<Gitem> gitemsl;
        private String gmoney;
        private String omoney;

        public Order() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public List<Gitem> getGitemsl() {
            return gitemsl;
        }

        public void setGitemsl(List<Gitem> gitemsl) {
            this.gitemsl = gitemsl;
        }

        public String getGmoney() {
            return gmoney;
        }

        public void setGmoney(String gmoney) {
            this.gmoney = gmoney;
        }

        public String getOmoney() {
            return omoney;
        }

        public void setOmoney(String omoney) {
            this.omoney = omoney;
        }
    }

    public static  class Gitem{
        private int gid;
        private String fig;
        private String name;
        private String price;
        private String tprice;
        private String num;

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getFig() {
            return fig;
        }

        public void setFig(String fig) {
            this.fig = fig;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTprice() {
            return tprice;
        }

        public void setTprice(String tprice) {
            this.tprice = tprice;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Order getData() {
        return data;
    }

    public void setData(Order data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
