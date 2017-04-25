package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.mine.view.adapter.OrderAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.activity_order)
    LinearLayout activityOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityOrder);
        initData();
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的订单") .createAndBind(activityOrder);
    }

    private void initData() {
        String[] titles = getResources().getStringArray(R.array.mine_order_tab);
        OrderAdapter orderAdapter = new OrderAdapter(getSupportFragmentManager(), Arrays.asList(titles));
        vpOrder.setAdapter(orderAdapter);
        tabOrder.setupWithViewPager(vpOrder);
    }

    public static void openOrderActivity(Activity activity){
        activity.startActivity(new Intent(activity,OrderActivity.class));
    }
}
