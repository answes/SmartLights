package com.bigshark.smartlight.pro.index.presenter;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.Result;
import com.bigshark.smartlight.bean.Speed;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.index.model.RecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2017/4/17.
 *
 * @email 869360026@qq.com
 * 地图操作类
 */

public class MapPreseter extends BasePresenter<RecordModel> {
    private List<LatLng> savesLatlng = new ArrayList<>();//保存的经纬度信息
    private List<Speed> savesSpeeds = new ArrayList<>();//保存的速度详情，每隔一分钟就保存一个。
    UpLoadRecord upLoadRecord;
    private float maxSpeed = 0;//最大速度
    private Context context;
    private long startTime = 0;
    private long endTIme = 0;
    private double distance = 0;
    private double height = 0;//海拔

    /**
     * 是否在记录形成中
     */
    private boolean isDuring = false;

    public MapPreseter(Context context) {
        super(context);
        this.context = context;
        initLocation();
    }

    @Override
    public RecordModel bindModel() {
        return new RecordModel(context);
    }

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private MyLocationListener myLocationListener = new MyLocationListener();

    /**
     * 开始骑行
     */
    public void start() {
        if (mlocationClient == null) {
            isDuring = true;
            mlocationClient = new AMapLocationClient(context.getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mlocationClient.setLocationOption(mLocationOption);
            mLocationOption.setSensorEnable(true);
            mLocationOption.setInterval(1000);
            mlocationClient.setLocationListener(myLocationListener);
            reste();
            mlocationClient.startLocation();
            startTime = System.currentTimeMillis();
        }
        upLoadRecord = new UpLoadRecord();

    }

    private void reste() {
        savesLatlng.clear();
        savesSpeeds.clear();
        maxSpeed = 0;//最大速度
        startTime = 0;
        endTIme = 0;
        distance = 0;
        height = 0;//海拔
    }

    /**
     * 暂停骑行
     */
    public void stop() {
        isDuring = false;
    }

    /**
     * 重新开始骑行
     */
    public void restart() {
        isDuring = true;
    }


    /**
     * 停止骑行
     */
    public void finish(final OnUIThreadListener<String> listener) {
        if (mlocationClient != null && mlocationClient.isStarted()) {
            isDuring = false;
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
            mlocationClient = null;
            endTIme = System.currentTimeMillis();
            long time = endTIme - startTime;
            upLoadRecord.setGps(JSON.toJSONString(savesLatlng));
            upLoadRecord.setDistance(distance);
            upLoadRecord.setHeight(height);
            upLoadRecord.setMaxSpeed(maxSpeed);
            upLoadRecord.setK(0.1797 * (time / (1000 * 60)) * 60);
            upLoadRecord.setTime(time);
            upLoadRecord.setAvSpeed((distance / 1000) / (time / (60 * 60 * 1000)));
            upLoadRecord.setAllspeed(JSON.toJSONString(savesSpeeds));
            if (listener == null) {
                return;
            }
            getModel().upLoda(upLoadRecord, new VolleyHttpUtils.HttpResult() {
                @Override
                public void succss(String t) {
                    try {
                        Result result = JSON.parseObject(t, Result.class);
                        if (result.getCode() == 1) {
                            listener.onResult("上传骑行记录成功");
                        } else {
                            listener.onResult("上传骑行记录失败");
                        }
                    } catch (Exception e) {
                        listener.onErro("上传失败");
                    }
                }

                @Override
                public void erro(String msg) {
                    listener.onErro(msg);
                }
            });
        }
    }

    public UpLoadRecord getUplodeRecord() {
        return upLoadRecord;
    }

    public List<LatLng> getSavesLatlng() {
        return savesLatlng;
    }

    private void initLocation() {

    }

    private boolean isFirst = true;

    public class MyLocationListener implements AMapLocationListener {
        LatLng lastLatLng;
        long speedTime = System.currentTimeMillis();

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if(aMapLocation.getCity()!=null) {
                SmartLightsApplication.cityName = aMapLocation.getCity();
            }
            if (isFirst) {
                isFirst = false;
                return;
            }

            if (!isDuring) {
                return;
            }

            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                savesLatlng.add(location);
                long newTime = System.currentTimeMillis();
                int times = 1;
                if(times == 1 |(newTime - speedTime) > 60000){
                    savesSpeeds.add(new Speed(String.valueOf(aMapLocation.getSpeed()),times));
                    speedTime = newTime;
                    times++;
                }
                if (maxSpeed < aMapLocation.getSpeed()) {
                    maxSpeed = aMapLocation.getSpeed();
                }
                if (lastLatLng != null) {
                    distance = distance + AMapUtils.calculateLineDistance(lastLatLng, location);
                }
                height = aMapLocation.getAltitude();
                lastLatLng = location;
                endTIme = System.currentTimeMillis();
                long time = 0;
                try {
                    time = endTIme - startTime;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MapLocationRecive.ACTION);
                upLoadRecord.setGps(JSON.toJSONString(savesLatlng));
                upLoadRecord.setDistance(distance);
                upLoadRecord.setHeight(height);
                upLoadRecord.setMaxSpeed(maxSpeed);
                upLoadRecord.setSpeed(aMapLocation.getSpeed());
                upLoadRecord.setK(0.1797 * (time / (1000 * 60)) * 60);
                upLoadRecord.setTime(time);
                //距离（x/1000）/时间(h) 1000L * 60 * 60;
                upLoadRecord.setAvSpeed(distance * time / (double) (1000 * 1000 * 60 * 60));

                intent.putExtra(MapLocationRecive.EXTRA_DATA, upLoadRecord);
                context.sendBroadcast(intent);
            }
        }
    }
//        private LatLng lastBdLocation;
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
//            savesLatlng.add(latLng);
//            //添加经纬度信息到地图上
//            if(maxSpeed<location.getSpeed()){
//                maxSpeed = location.getSpeed();
//            }
//            if(lastBdLocation !=null){
//               //distance = distance+ DistanceUtil. getDistance(lastBdLocation, latLng);
//            }
//            height = location.getAltitude();
//            lastBdLocation = latLng;
//            endTIme = System.currentTimeMillis();
//            double time = 0;
//            try {
//                 time = (double) ((endTIme-startTime)/(1000*60));
//            }catch (Exception e){
//
//            }
//
//            Intent intent = new Intent(MapLocationRecive.ACTION);
//            upLoadRecord.setGps(JSON.toJSONString(savesLatlng));
//            upLoadRecord.setDistance(distance);
//            upLoadRecord.setHeight(height);
//            upLoadRecord.setMaxSpeed(maxSpeed);
//            upLoadRecord.setSpeed(location.getSpeed());
//            upLoadRecord.setK(0.1797*time*60);
//            try {
//                upLoadRecord.setTime(time/60);
//            }catch (Exception e){
//
//            }
//
//            upLoadRecord.setAvSpeed(distance/1000/(time/60));
//
//            intent.putExtra(MapLocationRecive.EXTRA_DATA,upLoadRecord);
//            context.sendBroadcast(intent);
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//
//        }
//    }


}
