package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.TestBean;
import com.bigshark.smartlight.bean.WxPay;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity {
    private final int ZHIFUBAO = 1;
    private final int WEIXIN = 2;
    private IWXAPI iwxapi;

    @BindView(R.id.activity_pay)
    LinearLayout activityPay;

    private MarketListPresenter marketListPresenter;

    @Override
    public MVPBasePresenter bindPresneter() {
        marketListPresenter = new MarketListPresenter(this);
        return marketListPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        iwxapi =  WXAPIFactory.createWXAPI(this, "wx35bd3eeb5d531eaf");
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

    @OnClick({R.id.ali,R.id.wechat})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ali:
                showMsg("支付宝支付，订单信息为:"+ JSON.toJSONString(order));
                break;
            case R.id.wechat:
                marketListPresenter.payMoney(order.getData().get(0).getId(), Double.parseDouble(order.getData().get(0).getOmoney()), 2, new BasePresenter.OnUIThreadListener<WxPay.DataBean>() {
                    @Override
                    public void onResult(WxPay.DataBean result) {
                        PayReq payReq = new PayReq();
                        payReq.appId = "wx35bd3eeb5d531eaf";
                        payReq.partnerId = result.getPartnerid();
                        payReq.prepayId = result.getPrepayid();
                        payReq.packageValue = "Sign=WXPay";
                        payReq.nonceStr= result.getNoncestr();
                        payReq.timeStamp=  String.valueOf(result.getTimestamp());
                        payReq.sign= result.getSign();
                        iwxapi.sendReq(payReq);
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                    }
                });
                break;
        }
    }

    public static OrderResult order;

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
