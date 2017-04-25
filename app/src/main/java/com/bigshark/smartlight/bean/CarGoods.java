package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by bigShark on 2017/1/9.
 */

public class CarGoods {

    private int code;
    private List<Good> data;
    private String extra;

    public CarGoods() {
    }

    public CarGoods(int code, List<Good> data, String extra) {
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

    public List<Good> getData() {
        return data;
    }

    public void setData(List<Good> data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public  class Good {
        private int id;
        private int gid;
        private String name;
        private String price;
        private String imgUrl;
        private String num;
        private int uid;
        private String creTm;
        private boolean isCheck;

        public Good(){
        }
        public Good(int id, int gid, String name, String price, String imgUrl, String num, int uid, String creTm) {
            this.id = id;
            this.gid = gid;
            this.name = name;
            this.price = price;
            this.imgUrl = imgUrl;
            this.num = num;
            this.uid = uid;
            this.creTm = creTm;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getCreTm() {
            return creTm;
        }

        public void setCreTm(String creTm) {
            this.creTm = creTm;
        }
    }



}
