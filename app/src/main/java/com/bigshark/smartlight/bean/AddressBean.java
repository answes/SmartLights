package com.bigshark.smartlight.bean;

import java.io.Serializable;

/**
 * Created by ch on 2017/4/16.
 *
 * @email 869360026@qq.com
 */

public class AddressBean implements Serializable{
    private int id;
    private boolean isSelect;
    private String name;
    private String tel;
    private String is_default;
    private String province;    //省
    private String  city;          //市
    private String district;    //县
    private String detail;  //详情地址
    private String cre_tm;
    public AddressBean() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCre_tm() {
        return cre_tm;
    }

    public void setCre_tm(String cre_tm) {
        this.cre_tm = cre_tm;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }
}
