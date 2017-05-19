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
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DateFomat;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.activity_map)
    LinearLayout activityMap;

    private MinePresenter minePresenter;
    private String rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);
        initToolbar();
        ButterKnife.bind(this);
        SupportMultipleScreensUtil.scale(activityMap);
        //getIntentAndData();
    }

    private void getIntentAndData() {
        rideId = getIntent().getStringExtra(RIDE_ID);
        minePresenter.getRideDetail(rideId, new BasePresenter.OnUIThreadListener<RideDetailResult.RideDetail>() {
            @Override
            public void onResult(RideDetailResult.RideDetail result) {
                if(null != result){
                   // setData(result);
                }
            }

            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }




    public static void openRideDetailActivity(Activity activity , String id){
        Intent intent  = new Intent(activity,RideDetailActivity.class);
        intent.putExtra(RIDE_ID,id);
        activity.startActivity(intent);
    }

    private void setData(RideDetailResult.RideDetail result) {
        List<LatLng> latLngs = JSONUtil.getObjects(result.getGps(),LatLng.class);
        int time = Integer.parseInt(result.getTime());

        tvDate.setText(DateFomat.convertSecond2Date(result.getCre_tm()).concat("的骑行"));
        if (latLngs.size() != 0) {
            mapview.getMap().clear();
            mapview.getMap().addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
            mapview.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size()-1),18));
        }
        tvHeight.setText(result.getHeight());
        tvMax.setText(String.format("%.2f",result.getMaxspeed()));
        tvCal.setText(String.format("%.2f",result.getHeat()));
        tvAvgSpeed.setText(String.format("%.2f",result.getAvgspeed()));
        tvTotal.setText(String.format("%.2f",(Double.parseDouble(result.getDistance()))/1000));
        tvHour.setText(new StringBuffer()
                .append(String.format("%02d",time/(60*60*1000)))
                .append(":")
                .append(String.format("%02d",time%(60*60*1000)/(60*1000)))
                .append(":")
                .append((String.format("%02d",time%(60*60*1000)%(60*1000)/(1000))))
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
                }).setTitle("骑行记录") .createAndBind(activityMap);
    }
}
