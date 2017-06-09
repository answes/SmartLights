package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Speed;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.CustomCurveChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeedDetailActivity extends BaseActivity {
    @BindView(R.id.curveChart1)
    LinearLayout curveChartView;
    @BindView(R.id.activity_speed)
    LinearLayout activitySpeed;
    @BindView(R.id.tv_maxspeed)
    TextView tvMaxspeed;
    @BindView(R.id.tv_avgspeed)
    TextView tvAvgspeed;

    private static  UpLoadRecord upLoadRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_detail);
        ButterKnife.bind(this);
        initToolBar();
        SupportMultipleScreensUtil.scale(activitySpeed);
        initCurveChart1();
    }

    /**
     * 初始化曲线图数据
     */
    private void initCurveChart1() {
        tvMaxspeed.setText(upLoadRecord.getMaxSpeed()+"  km/h\n最高速度");
        tvAvgspeed.setText(upLoadRecord.getAvSpeed()+"  km/h\n最高速度");
        List<Speed> speeds =  JSON.parseArray(upLoadRecord.getAllspeed(),Speed.class);
        String[] xLabel;
        int[] data1;
        if(null != speeds && speeds.size() != 0){
            xLabel= new String[speeds.size()];
            data1 = new int[speeds.size()];
            for(int i= 0;i<speeds.size();i++){
                xLabel[i] = String.valueOf(speeds.get(i).getTime());
                data1[i] =Integer.valueOf(speeds.get(i).getSpeed());
            }
        }else{
            xLabel = new String[]{"1", "2", "3", "4", "5", "6", "7"};
            data1 = new int[]{0,0,0,0,0,0,0};
        }
        String []yLabel ={"0","10", "20", "30", "40", "50", "60", "70"};
        List<int[]> data = new ArrayList<>();
        List<Integer> color = new ArrayList<>();
        data.add(data1);
        color.add(R.color.white);
        CustomCurveChart chart = new CustomCurveChart(this, xLabel, yLabel, data, color, false);
        curveChartView.addView(chart);
    }


    private void initToolBar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("速度详情").createAndBind(activitySpeed);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }


    public static void openSpeedDetailActivity(Activity activity,UpLoadRecord upLoadRecord) {
        SpeedDetailActivity.upLoadRecord = upLoadRecord;
        activity.startActivity(new Intent(activity, SpeedDetailActivity.class));
    }
}
