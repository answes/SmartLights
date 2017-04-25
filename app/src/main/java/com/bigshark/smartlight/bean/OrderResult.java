package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class OrderResult {
    private int code;
    private List<Order> data;
    private String extra;

    public static  class Order{
        private String id;
        private String orderNum;
        private String username;
        private String address;
        private String tel;
        private List<Gitem> gitems1;
        private String gitems;
        private String gmoney;
        private String omoney;
        private String buyerId;
        private String creTm;
        private String paytype;
        private String status;
        private String payTm;
        private String sendTm;
        private String expCom;
        private String expNum;
        private String finishTm;
        private String cancelTm;

        public Order() {
        }

        public String getGitems() {
            return gitems;
        }

        public void setGitems(String gitems) {
            this.gitems = gitems;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
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

        public List<Gitem> getGitems1() {
            return gitems1;
        }

        public void setGitems1(List<Gitem> gitems1) {
            this.gitems1 = gitems1;
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

        public String getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(String buyerId) {
            this.buyerId = buyerId;
        }

        public String getCreTm() {
            return creTm;
        }

        public void setCreTm(String creTm) {
            this.creTm = creTm;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayTm() {
            return payTm;
        }

        public void setPayTm(String payTm) {
            this.payTm = payTm;
        }

        public String getSendTm() {
            return sendTm;
        }

        public void setSendTm(String sendTm) {
            this.sendTm = sendTm;
        }

        public String getExpCom() {
            return expCom;
        }

        public void setExpCom(String expCom) {
            this.expCom = expCom;
        }

        public String getExpNum() {
            return expNum;
        }

        public void setExpNum(String expNum) {
            this.expNum = expNum;
        }

        public String getFinishTm() {
            return finishTm;
        }

        public void setFinishTm(String finishTm) {
            this.finishTm = finishTm;
        }

        public String getCancelTm() {
            return cancelTm;
        }

        public void setCancelTm(String cancelTm) {
            this.cancelTm = cancelTm;
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

    public List<Order> getData() {
        return data;
    }

    public void setData(List<Order>  data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
