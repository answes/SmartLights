package com.bigshark.smartlight;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bigshark.smartlight.bean.City;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.bean.Province;
import com.bigshark.smartlight.utils.GetJsonDataUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigShark on 2017/1/16.
 */

public class SmartLightsApplication extends Application {

    public static  LoginResult.User USER= null;
    public static RequestQueue queue;//volley 队列



    //开启线程初始化 省市区数据 及附属数据
    public static List<Province> provinces = new ArrayList<>();
    public static List<List<City>> cities = new ArrayList<>();
    public static List<List<List<String>>> areas = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        SupportMultipleScreensUtil.init(this);
        initVolley();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //initJson();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(SmartLightsApplication.this, null);
                // 将该app注册到微信
                msgApi.registerApp("wx35bd3eeb5d531eaf");
                initJson();
            }
        }).start();
        initErrorHandler();
    }


    private void initErrorHandler(){
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
    }
    public void initJson() {
        Gson gson = new Gson();
        String jsonData= new GetJsonDataUtil().getJson(this,"province.json").trim();
        provinces = gson.fromJson(jsonData,new TypeToken<List<Province>>(){}.getType());
        for(int i=0;i<provinces.size();i++){
            List<City> listCity = provinces.get(i).getCity();
            List<List<String>> province_Area = new ArrayList<>();
            cities.add(listCity);
            for (int j=0;j<listCity.size();j++){
                List<String> city_area = new ArrayList<>();
                for(int a = 0;a<listCity.get(j).getString().size();a++){
                    city_area.add(listCity.get(j).getString().get(a));
                }
                province_Area.add(city_area);
            }
            areas.add(province_Area);
        }
    }

    /**
     * c初始化Volley
     */
    private void initVolley(){
        queue = Volley.newRequestQueue(this);
    }

}
