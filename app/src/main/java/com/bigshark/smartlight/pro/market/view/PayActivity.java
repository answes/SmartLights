package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.ALi;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.WxPay;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.OrderDetailActivity;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.zhifubao.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends BaseActivity {
    private static final int SDK_PAY_FLAG = 1;
    @BindView(R.id.ali)
    SuperTextView ali;
    @BindView(R.id.wechat)
    SuperTextView wechat;
    private IWXAPI iwxapi;

    @BindView(R.id.activity_pay)
    LinearLayout activityPay;

    private MarketListPresenter marketListPresenter;
    private boolean isPayOver = true;

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
        initToolbar();
        iwxapi = WXAPIFactory.createWXAPI(this, "wx35bd3eeb5d531eaf");
        SupportMultipleScreensUtil.scale(activityPay);
        viewOnClick();

    }


    private void viewOnClick() {
        ali.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onSuperTextViewClick() {
                if (isPayOver) {
                    isPayOver = false;
                    marketListPresenter.payAliMoney(order.getData().get(0).getId(), Double.parseDouble(order.getData().get(0).getOmoney()), new BasePresenter.OnUIThreadListener<ALi>() {
                        @Override
                        public void onResult(ALi result) {
                            pay(result.getData());
                        }
                        @Override
                        public void onErro(String string) {
                            showMsg(string);
                        }
                    });

                }
            }
        });
        wechat.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onSuperTextViewClick() {
                if (isPayOver) {
                    isPayOver = false;
                    marketListPresenter.payMoney(order.getData().get(0).getId(), Double.parseDouble(order.getData().get(0).getOmoney()), 2, new BasePresenter.OnUIThreadListener<WxPay.DataBean>() {
                        @Override
                        public void onResult(WxPay.DataBean result) {
                            PayReq payReq = new PayReq();
                            payReq.appId = "wx35bd3eeb5d531eaf";
                            payReq.partnerId = result.getPartnerid();
                            payReq.prepayId = result.getPrepayid();
                            payReq.packageValue = "Sign=WXPay";
                            payReq.nonceStr = result.getNoncestr();
                            payReq.timeStamp = String.valueOf(result.getTimestamp());
                            payReq.sign = result.getSign();
                            iwxapi.sendReq(payReq);
                            isPayOver = true;
                        }

                        @Override
                        public void onErro(String string) {
                            showMsg(string);
                        }
                    });
                }
            }
        });
    }

    private void pay(final String info) {
//       final String s =  "alipay_sdk=alipay-sdk-php-20161101&app_id=2017033106505939&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22subject%22%3A+%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95%22%2C%22out_trade_no%22%3A+%2220170125test01%22%2C%22timeout_express%22%3A+%2230m%22%2C%22total_amount%22%3A+%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpybike.idc.zhonxing.com%2FPay%2Fali_payback&sign_type=RSA&timestamp=2017-06-03+19%3A19%3A10&version=1.0&sign=zI30ZFsrO99u8sUeyaskiQAq%2F%2F6VjgcqPR%2FGXThDpEAY8NBDaCYH2p3nI7vScOQnYkFOva4m0gzpsjIF%2Bl9k%2BFS9IpbttW53N%2FknLTHNuicwm0680%2BGBSu4%2BDVQmS7JexFRObxpqcEACvV2LGuwI9EPRFZF2sufASojTkTEbyLYnDd6VYEdqEmRtM68RHRxWL08LXA7vnuHdztSbkjRoRSdbyq0B1T5Iw4CmcFkO2LXxpQAaMgh7jxsqMq2HkfJOabvo7nbutzLlgVN8kdtn4MyVDRkpg9E86GgVNvE4xl4t0fXHXtSTVsajmAv6RVP%2Bgi4mDI7q%2FEWTb48vD%2B3dGw%3D%3D";
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(info, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("支付").createAndBind(activityPay);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    isPayOver = true;
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        isPayOver = true;
                        PayActivity.this.setResult(RESULT_OK);
                        OrderDetailActivity.openOrderDetailActivityForResult(PayActivity.this, Integer.parseInt(PayActivity.order.getData().get(0).getId()));
                        PayActivity.this.finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

                default:
                    break;
            }
        }
    };

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
