package com.bigshark.smartlight.pro.index.model;

import android.content.Context;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.pro.base.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ch on 2017/4/17.
 *
 * @email 869360026@qq.com
 */

public class RecordModel extends BaseModel {
    private String getUpLoadUrl(){
        return getServerUrl()+"/Bike/up_bike_record";
    }
    public RecordModel(Context context) {
        super(context);
    }

    @Override
    public String getServerUrl() {
        return super.getServerUrl();
    }

    public void upLoda(UpLoadRecord upLoadRecord, VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map<String,String> request = new HashMap<>();
        request.put("user_id", SmartLightsApplication.USER.getId()+"");
        request.put("maxspeed", String.valueOf(upLoadRecord.getMaxSpeed()));
        request.put("avgspeed", String.valueOf(upLoadRecord.getAvSpeed()));
        request.put("distance", String.valueOf(upLoadRecord.getDistance()/1000d));

        String time = new StringBuffer()
                .append(String.format("%02d",upLoadRecord.getTime()/(60*60*1000)))
                .append(":")
                .append(String.format("%02d",upLoadRecord.getTime()%(60*60*1000)/(60*1000)))
                .append(":")
                .append((String.format("%02d",upLoadRecord.getTime()%(60*60*1000)%(60*1000)/(1000))))
                .toString();
        request.put("time", time);

        request.put("height",String.valueOf(upLoadRecord.getHeight()));
        request.put("heat",String.valueOf(upLoadRecord.getK()));
        request.put("tel",SmartLightsApplication.USER.getTel()+"");
        request.put("gps",upLoadRecord.getGps());
        request.put("allspeed",upLoadRecord.getAllspeed());
        httpUtils.postData(getUpLoadUrl(),request,httpResult);
    }
}
