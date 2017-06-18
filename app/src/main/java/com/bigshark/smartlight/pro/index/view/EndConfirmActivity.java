package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolylineOptions;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.presenter.MapPreseter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by ch on 2017/5/12.
 *
 * @email 869360026@qq.com
 * 这个页面为结算时候  不开启定位 只绘制轨迹以及是否上传等
 */

public class EndConfirmActivity extends BaseActivity {
    public static  final int REQUEST_END_CONFIRM = 60;
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
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.activity_map)
    LinearLayout activityMap;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日的骑行");

    private static MapPreseter mapseter;
    @Override
    public MVPBasePresenter bindPresneter() {
        return mapseter;
    }


    private void  initTool(){
          GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle(getString(R.string.end_confirm_text)).createAndBind(activityMap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapview.onCreate(savedInstanceState);
        initTool();
        SupportMultipleScreensUtil.scale(activityMap);
        btnCancel.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
        tvDate.setText(dateFormat.format(new Date()));
        if (latLngs.size() != 0) {
            mapview.getMap().clear();
            mapview.getMap().addPolyline(new PolylineOptions().
                    addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
            mapview.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLngs.get(latLngs.size()-1),18));
        }
        tvHeight.setText(String.valueOf((double)upLoadRecord.getHeight()));
        tvMax.setText(String.format("%.2f",upLoadRecord.getMaxSpeed()));
        tvCal.setText(String.format("%.2f",upLoadRecord.getK()));
        tvAvgSpeed.setText(String.format("%.2f",upLoadRecord.getAvSpeed()));
        tvTotal.setText(String.format("%.2f",(upLoadRecord.getDistance())/1000));
        tvHour.setText(new StringBuffer()
                .append(String.format("%02d",upLoadRecord.getTime()/(60*60*1000)))
                .append(":")
                .append(String.format("%02d",upLoadRecord.getTime()%(60*60*1000)/(60*1000)))
                .append(":")
                .append((String.format("%02d",upLoadRecord.getTime()%(60*60*1000)%(60*1000)/(1000))))
                .toString());
    }

    private static UpLoadRecord upLoadRecord;
    private static List<LatLng> latLngs;//结束骑行时候的经纬度
    public static void openMapActivity(Activity activity, UpLoadRecord upLoadRecord,List<LatLng> latLngs,MapPreseter preseter) {
        if(latLngs == null || latLngs.size() == 0){
            ToastUtil.showToast(activity,"骑行时间太短，暂时计算不出骑行记录");
            return;
        }
        EndConfirmActivity.mapseter = preseter;
        EndConfirmActivity.upLoadRecord = upLoadRecord;
        EndConfirmActivity.latLngs = latLngs;
        activity.startActivityForResult(new Intent(activity, EndConfirmActivity.class),REQUEST_END_CONFIRM);
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
        mapview.onDestroy();
        super.onDestroy();
        mapseter.restart();
        mapseter = null;
        upLoadRecord = null;
        latLngs = null;
    }

    @OnClick({R.id.btn_cancel,R.id.btn_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_cancel:
                mapseter.finish(null);
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_confirm:
                mapseter.finish(new BasePresenter.OnUIThreadListener<String>() {
                    @Override
                    public void onResult(String result) {
                        showMsg(result);
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                break;
        }
    }


}
