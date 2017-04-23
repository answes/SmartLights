package com.bigshark.smartlight.bean;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * Created by ch on 2017/4/15.
 *
 * @email 869360026@qq.com
 */

public class City implements IPickerViewData{
    private String name;

    private List<String> area ;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setString(List<String> area){
        this.area = area;
    }
    public List<String> getString(){
        return this.area;
    }


    public City(String name, List<String> area) {
        this.name = name;
        this.area = area;
    }

    public City() {
    }

    @Override
    public String getPickerViewText() {
        return getName();
    }
}
