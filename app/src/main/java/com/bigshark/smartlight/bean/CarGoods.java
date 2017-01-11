package com.bigshark.smartlight.bean;

/**
 * Created by bigShark on 2017/1/9.
 */

public class CarGoods {
    private boolean isSelect;
    private int number;
    private String name;
    private String imgUrl;
    private double price;
    private boolean isDelete;

    public CarGoods() {
    }

    public boolean isSelect() {

        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public String toString() {
        return "CarGoods{" +
                "isSelect=" + isSelect +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", isDelete=" + isDelete +
                '}';
    }
}
