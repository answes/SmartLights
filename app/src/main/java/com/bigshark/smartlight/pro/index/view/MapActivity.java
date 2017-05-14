package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.market.view.CarActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity {

    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.activity_map)
    LinearLayout activityMap;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_avg_speed)
    TextView tvAvgSpeed;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_cal)
    TextView tvCal;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日的骑行");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapview.onCreate(savedInstanceState);
        initToolBar();
        SupportMultipleScreensUtil.scale(activityMap);
        tvDate.setText(dateFormat.format(new Date()));
        if (latLngs.size() != 0) {
            mapview.getMap().clear();
            mapview.getMap().addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
            mapview.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size()-1),18));
        }
    }

    private void initToolBar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("骑行记录").createAndBind(activityMap);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    private static UpLoadRecord upLoadRecord;
    private static boolean isDoing = false;//正在骑行
    private static List<LatLng> latLngs;
    public static void openMapActivity(Activity activity, UpLoadRecord upLoadRecord, boolean isDoing,List<LatLng> latLngs) {
        MapActivity.upLoadRecord = upLoadRecord;
        MapActivity.isDoing = isDoing;
        MapActivity.latLngs = latLngs;
        activity.startActivity(new Intent(activity, MapActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
        registerBroadCasst();
    }

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private MapLocationRecive mapLocationRecive;
    private boolean isFist = true;
    private void registerBroadCasst() {
        if (mapLocationRecive == null) {
            mapLocationRecive = new MapLocationRecive(new MapLocationRecive.OnLocationReciveListener() {
                @Override
                public void onRevice(final UpLoadRecord record) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            latLngs = new Gson().fromJson(record.getGps(),new TypeToken<List<LatLng>>(){}.getType());
                            tvHeight.setText(String.valueOf((double)record.getHeight()));
                            tvMax.setText(String.format("%.2f",record.getMaxSpeed()));
                            tvCal.setText(String.format("%.2f",record.getK()));
                            tvAvgSpeed.setText(String.format("%.2f",record.getAvSpeed()));
                            tvTotal.setText(String.format("%.2f",(record.getDistance())/1000));
                            tvHour.setText(new StringBuffer()
                            .append(String.format("%02d",record.getTime()/(60*60*1000)))
                            .append(":")
                            .append(String.format("%02d",record.getTime()%(60*60*1000)/(60*1000)))
                                    .append(":")
                                    .append((String.format("%02d",record.getTime()%(60*60*1000)%(60*1000)/(1000))))
                                    .toString()
                            );
                            mapview.getMap().clear();
                            mapview.getMap().addPolyline(new PolylineOptions().
                                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
                            if(isFist) {
                                isFist = false;
                                mapview.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size() - 1), 18));
                            }
                        }
                    });
                }
            });
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MapLocationRecive.ACTION);
        registerReceiver(mapLocationRecive, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
        unRegisterBroadCast();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapview.onSaveInstanceState(outState);
    }

    private void unRegisterBroadCast() {
        if (mapLocationRecive != null) {
            unregisterReceiver(mapLocationRecive);
        }
    }

}
