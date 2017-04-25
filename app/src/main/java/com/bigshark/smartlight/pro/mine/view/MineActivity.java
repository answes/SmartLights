package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends AppCompatActivity {
    @BindView(R.id.ll_context)
    LinearLayout llContext;
    @BindView(R.id.iv_hander)
    ImageView ivHander;
    @BindView(R.id.iv_userName)
    TextView ivUserName;
    @BindView(R.id.stv_myStroke)
    SuperTextView stvMyStroke;
    @BindView(R.id.stv_myorder)
    SuperTextView stvMyorder;
    @BindView(R.id.stv_myEquipment)
    SuperTextView stvMyEquipment;
    @BindView(R.id.stv_market)
    SuperTextView stvMarket;
    @BindView(R.id.stv_set)
    SuperTextView stvSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContext);
        ivUserName.setText(SmartLightsApplication.USER.getName());
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的个人中心").createAndBind(llContext);
    }
    @OnClick({R.id.stv_myStroke, R.id.stv_myorder, R.id.stv_myEquipment,R.id.stv_market, R.id.stv_set,R.id.iv_hander})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_myStroke:
                RideActivity.openRideActivity(this);
                break;
            case R.id.stv_myorder:
                OrderActivity.openOrderActivity(this);
                break;
            case R.id.stv_myEquipment:
                EquipmentActivity.openEquipmentActivity(this);
                break;
            case R.id.stv_market:
                MarketActivity.openMarketActivity(this);
                break;
            case R.id.stv_set:
                SetActivity.openSetActivity(this);
                break;
            case R.id.iv_hander:
                MineDetailsActivity.openMineDetailsActivity(this);
                break;
        }
    }
    public static void openMineActivity(Activity activity){
        activity.startActivity(new Intent(activity,MineActivity.class));
    }
}
