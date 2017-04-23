package com.bigshark.smartlight.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by ch on 2017/4/12.
 *
 * @email 869360026@qq.com
 */

public class Province implements IPickerViewData {
    private String name;
    private List<City> city;

    public Province() {
    }

    public Province(String name, List<City> city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }
}
