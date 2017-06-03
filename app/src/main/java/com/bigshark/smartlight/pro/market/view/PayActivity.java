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
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.zhifubao.PayResult;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends BaseActivity {
    private final int ZHIFUBAO = 1;
    private final int WEIXIN = 2;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
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

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private void viewOnClick() {
        ali.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onSuperTextViewClick() {
                if (isPayOver) {
                    isPayOver = false;
                    marketListPresenter.payAliMoney(order.getData().get(0).getId(), Double.parseDouble(order.getData().get(0).getOmoney()), 1, new BasePresenter.OnUIThreadListener<ALi>() {
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
//       final String s =  "app_id=2017033106505939&biz_content=%257B%2522timeout_express%2522%253A%252230m%2522%252C%2522product_code%2522%253A%2522QUICK_MSECURITY_PAY%2522%252C%2522total_amount%2522%253A%25220.01%2522%252C%2522subject%2522%253A%25228%2522%252C%2522body%2522%253A%2522%255Cu9a91%255Cu683c%255Cu8ba2%255Cu5355%2522%252C%2522out_trade_no%2522%253A%2522on5931082e1c213%2522%257D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%253A%252F%252Fpybike.idc.zhonxing.com%252FPay%252Fali_payback&sign_type=RSA&timestamp=2017-06-03%2B10%253A12%253A11&version=1.0&sign=HRoLFcRI3U9qvQOaZ3jroPFmehq8r0dJFc6GPmo6iJcxX%252FpIIuuxerfWOvud1xoCZ47zqs8c6rBfW5bI1GmHJj6a06e2%252Fana%252BSTs1ylLgmIsp6wvN8UZsbl0jutaSGAsJ6iHodlBeFPhwBSFlFlokjN0gbZsFUiZhGTdQ3yH14Gq8TsgCyeYIc38ikv8nsKq8E%252Fu%252FuozI3p4LenEL0kvpSDJx%252F9iBlp7sN1Uex99gt7%252BRZyxy2E1NCwIyeXbPvxU1HOTGxUz9tfG25PxqQhOrjgeeqQGiKzApDMtg2c%252BNPf9BkchmKHoXO4kKpo3OeZB4YQB8oKIGRglT38D9rVaIg%253D%253D";
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



//    @OnClick({R.id.ali, R.id.wechat})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ali:
//                if (isPayOver) {
//                    showMsg("进来支付宝了");
//                    isPayOver = false;
//                    Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Double.parseDouble(order.getData().get(0).getOmoney()), order.getData().get(0).getId());
//                    String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//                    String sign = OrderInfoUtil2_0.getSign(params, OrderInfoUtil2_0.PRIVATEKEY, false);
//                    final String orderInfo = orderParam + "&" + sign;
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            PayTask alipay = new PayTask(PayActivity.this);
//                            Map<String, String> result = alipay.payV2(orderInfo, true);
//                            Log.i("msp", result.toString());
//
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
//                        }
//                    }).start();
//                }
//
//                break;
//            case R.id.wechat:
//                if (isPayOver) {
//                    isPayOver = false;
//                    marketListPresenter.payMoney(order.getData().get(0).getId(), Double.parseDouble(order.getData().get(0).getOmoney()), 2, new BasePresenter.OnUIThreadListener<WxPay.DataBean>() {
//                        @Override
//                        public void onResult(WxPay.DataBean result) {
//                            PayReq payReq = new PayReq();
//                            payReq.appId = "wx35bd3eeb5d531eaf";
//                            payReq.partnerId = result.getPartnerid();
//                            payReq.prepayId = result.getPrepayid();
//                            payReq.packageValue = "Sign=WXPay";
//                            payReq.nonceStr = result.getNoncestr();
//                            payReq.timeStamp = String.valueOf(result.getTimestamp());
//                            payReq.sign = result.getSign();
//                            iwxapi.sendReq(payReq);
//                            isPayOver = true;
//                        }
//
//                        @Override
//                        public void onErro(String string) {
//                            showMsg(string);
//                        }
//                    });
//                }
//                break;
//        }
//    }

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
