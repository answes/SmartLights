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
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
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

    @OnClick({R.id.alipay,R.id.wepay})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.alipay:
                showMsg("支付宝支付，订单信息为:"+ JSON.toJSONString(order));
                break;
            case R.id.wepay:
                similaerPay();
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
    private void similaerPay(){
        StringRequest request = new StringRequest(Request.Method.POST, "http://pybike.idc.zhonxing.com/Order/prepay"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TestBean testBean = JSON.parseObject(response,TestBean.class);
                PayReq payReq = new PayReq();
                payReq.appId = "wx35bd3eeb5d531eaf";
                payReq.partnerId = testBean.getData().getPartnerId();
                payReq.prepayId = testBean.getData().getPrepayId();
                payReq.packageValue = "Sign=WXPay";
                payReq.nonceStr=  testBean.getData().getNonceStr();
                payReq.timeStamp=  String.valueOf(testBean.getData().getTimeStamp());
                payReq.sign=  testBean.getData().getSign();
                iwxapi.sendReq(payReq);
                Log.i("Load",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showMsg("");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map requestParam = new HashMap<>();
                requestParam.put("name",order.getData().get(0).getUsername());
                requestParam.put("id",order.getData().get(0).getId());
                requestParam.put("money",order.getData().get(0).getOmoney());
                requestParam.put("type",String.valueOf(WEIXIN));
                return super.getParams();
            }
        };
        SmartLightsApplication.queue.add(request);
    }

}
