package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {

    @BindView(R.id.alipay)
    TextView alipay;
    @BindView(R.id.wepay)
    TextView wepay;
    @BindView(R.id.activity_pay)
    LinearLayout activityPay;

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        SupportMultipleScreensUtil.scale(activityPay);
        initToolbar();
    }
    private void initToolbar(){
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("支付").createAndBind(activityPay);
    }

    @OnClick({R.id.alipay,R.id.wepay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.alipay:
                showMsg("支付宝支付，订单信息为:"+ JSON.toJSONString(order));
                break;
            case R.id.wepay:
                showMsg("微信支付，订单信息为:"+ JSON.toJSONString(order));
                break;
        }
    }

    private static OrderResult order;

    /**
     * 打开支付activity
     *
     * @param activity
     * @param orderResult
     */
    public static void openPayActivity(Activity activity, OrderResult orderResult) {
        order = orderResult;
        activity.startActivity(new Intent(activity, PayActivity.class));
    }

}
