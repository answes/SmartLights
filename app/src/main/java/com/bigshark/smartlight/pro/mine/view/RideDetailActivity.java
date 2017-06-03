package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.RideDetailResult;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.view.SpeedDetailActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DateFomat;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RideDetailActivity extends BaseActivity {
    private static final String RIDE_ID = "id";

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
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.activity_map_detail)
    LinearLayout activityMap;

    private MinePresenter minePresenter;
    private String rideId;
    private RideDetailResult.RideDetail rideDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityMap);
        mapview.onCreate(savedInstanceState);
        getIntentAndData();
    }

    private void getIntentAndData() {
        rideId = getIntent().getStringExtra(RIDE_ID);
        minePresenter.getRideDetail(rideId, new BasePresenter.OnUIThreadListener<RideDetailResult.RideDetail>() {
            @Override
            public void onResult(RideDetailResult.RideDetail result) {
                rideDetail = result;
                setData(result);
            }

            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }


    public static void openRideDetailActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, RideDetailActivity.class);
        intent.putExtra(RIDE_ID, id);
        activity.startActivity(intent);
    }

    private void setData(RideDetailResult.RideDetail result) {
        List<LatLng> latLngs = new Gson().fromJson(result.getGps(),new TypeToken<List<LatLng>>(){}.getType());
        int time = Integer.parseInt(result.getTime());

        tvDate.setText(DateFomat.convertSecond2Date(result.getCre_tm()).concat("的骑行"));
        if (null != latLngs && latLngs.size() != 0) {
            mapview.getMap().clear();
            mapview.getMap().addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
            mapview.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size()-1),18));
        }
        tvHeight.setText(result.getHeight());
        tvMax.setText(result.getMaxspeed());
        tvCal.setText(result.getHeat());
        tvAvgSpeed.setText(result.getAvgspeed());
        tvTotal.setText(String.format("%.2f", (Double.parseDouble(result.getDistance())) / 1000));
        tvHour.setText(new StringBuffer()
                .append(String.format("%02d", time / (60 * 60 * 1000)))
                .append(":")
                .append(String.format("%02d", time % (60 * 60 * 1000) / (60 * 1000)))
                .append(":")
                .append((String.format("%02d", time % (60 * 60 * 1000) % (60 * 1000) / (1000))))
                .toString());

    }

    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("骑行记录").createAndBind(activityMap);
    }

    @OnClick(R.id.tv_info)
    public void onViewClicked() {
        UpLoadRecord upLoadRecord = new UpLoadRecord();
        upLoadRecord.setAllspeed(rideDetail.getAllspeed());
        upLoadRecord.setAvSpeed(Double.valueOf(rideDetail.getAvgspeed()));
        upLoadRecord.setMaxSpeed(Float.valueOf(rideDetail.getMaxspeed()));
        SpeedDetailActivity.openSpeedDetailActivity(this,upLoadRecord);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }
}
