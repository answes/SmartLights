package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.CurveChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpeedDetailActivity extends BaseActivity {

    @BindView(R.id.ccv_curve)
    CurveChartView curveChartView;
    @BindView(R.id.activity_speed)
    LinearLayout activitySpeed;
    private List<Integer> points = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_detail);
        ButterKnife.bind(this);
        initToolBar();
        SupportMultipleScreensUtil.scale(activitySpeed);

        initPoint();
        curveChartView.setPointsY(points);
        curveChartView.setShowModel(CurveChartView.MODEL_WEEK);
    }

    private void initPoint() {
        for (int i=0;i<15;i++){
            points.add(i+5);
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
                }).setTitle("速度详情").createAndBind(activitySpeed);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }


    public static  void openSpeedDetailActivity(Activity activity){
        activity.startActivity(new Intent(activity,SpeedDetailActivity.class));
    }
}
