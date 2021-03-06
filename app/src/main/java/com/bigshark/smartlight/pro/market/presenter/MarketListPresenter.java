package com.bigshark.smartlight.pro.market.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.bean.ALi;
import com.bigshark.smartlight.bean.GoodDetail;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.SubOrderResult;
import com.bigshark.smartlight.bean.WxPay;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.market.model.MaketListModel;
import com.bigshark.smartlight.utils.JSONUtil;

import java.util.List;

import static android.R.attr.type;

/**
 * Created by bigShark on 2017/1/20.
 */

public class MarketListPresenter extends BasePresenter<MaketListModel> {
    private int page = 1;

    public MarketListPresenter(Context context) {
        super(context);
    }

    @Override
    public MaketListModel bindModel() {
        return new MaketListModel(getContext());
    }

    public void getGoodsList(final boolean isDownRefresh, final OnUIThreadListener<List<Market.Goods>> onUIThreadListener) {

        getModel().getMarketList(page, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                if (1 == code) {
                    Market market = JSONUtil.getObject(result, Market.class);
                    if (isDownRefresh) {
                        page = 1;
                    } else {
                        page++;
                    }
                    onUIThreadListener.onResult(market.getData());
                } else {
                    onUIThreadListener.onErro("");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    public void getGoodDetail(int gid, final OnUIThreadListener<GoodDetail.Goods> onUIThreadListener) {

        getModel().getGoodDetail(gid, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e("TAG", "onResult:detail " + result);
                if (TextUtils.isEmpty(result)) {
                    onUIThreadListener.onResult(null);
                } else {
                    JSONObject json = JSONObject.parseObject(result);
                    int code = (int) json.get("code");
                    if (1 == code) {
                        GoodDetail goodDetail = JSONUtil.getObject(result, GoodDetail.class);
                        onUIThreadListener.onResult(goodDetail.getData());
                    } else {
                        onUIThreadListener.onErro("");
                    }
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     * 商品加入购物车操作。
     *
     * @param gid
     * @param onUIThreadListener
     */
    public void addGoodToCar(int gid, String price,String img_url, final OnUIThreadListener<String> onUIThreadListener) {

        getModel().addGoodToCart(gid, price,img_url, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                if (TextUtils.isEmpty(result)) {
                    onUIThreadListener.onResult(null);
                } else {
                    JSONObject json = JSONObject.parseObject(result);
                    int code = (int) json.get("code");
                    if (1 == code) {
                        onUIThreadListener.onResult(result);
                    } else {
                        onUIThreadListener.onErro("");
                    }
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     * 购物车删除商品操作
     *
     * @param gid                删除全部时候传0
     * @param onUIThreadListener
     */
    public void delGoodToCar(boolean isAllDel, int gid, final OnUIThreadListener<String> onUIThreadListener) {

        getModel().delGoodToCart(isAllDel, gid, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e("TAG", "delGoodToCar " + result);
                if (TextUtils.isEmpty(result)) {
                    onUIThreadListener.onResult(null);
                } else {
                    JSONObject json = JSONObject.parseObject(result);
                    int code = (int) json.get("code");
                    if (1 == code) {
                        onUIThreadListener.onResult(result);
                    } else {
                        onUIThreadListener.onErro("");
                    }
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(null);
            }
        });
    }

    /**
     * 购物车商品数量操作
     *
     * @param gid
     * @param onUIThreadListener
     */
    public void updateGoodNum(int gid, int num, final OnUIThreadListener<String> onUIThreadListener) {

        getModel().updateGoodNum(gid, num, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e("TAG", "updateGoodNum " + result);
                if (TextUtils.isEmpty(result)) {
                    onUIThreadListener.onResult(null);
                } else {
                    JSONObject json = JSONObject.parseObject(result);
                    int code = (int) json.get("code");
                    if (1 == code) {
                        onUIThreadListener.onResult(result);
                    } else {
                        onUIThreadListener.onErro("操作失败");
                    }
                }
            }

            @Override
            public void erro(String msg) {

            }
        });
    }

    /**
     * 获取购物车商品列表
     *
     * @param onUIThreadListener
     */
    public void getCarGoodList(final OnUIThreadListener<String> onUIThreadListener) {

        getModel().getCarGoodList(new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e("TAG", "getCarGoodList " + result);
                if (TextUtils.isEmpty(result)) {
                    onUIThreadListener.onResult(null);
                } else {
                    JSONObject json = JSONObject.parseObject(result);
                    int code = (int) json.get("code");
                    if (1 == code) {
                        onUIThreadListener.onResult(result);
                    } else {
                        onUIThreadListener.onResult(null);
                    }
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro("获取数据失败，请稍后再试");
            }
        });
    }

    /**
     * 下订单
     *
     * @param orderResult
     * @param onUIThreadListener
     */
    public void subOrder(OrderResult orderResult, final OnUIThreadListener<String> onUIThreadListener) {
        getModel().subOrder(orderResult, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                try {
                    SubOrderResult result1 = JSON.parseObject(result, SubOrderResult.class);
                    if (result1.getCode() == 1) {
                        onUIThreadListener.onResult(String.valueOf(result1.getData()));
                    } else {
                        onUIThreadListener.onErro(result1.getExtra());
                    }
                } catch (Exception e) {
                    onUIThreadListener.onErro("提交订单");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     *
     * @param orderId 订单ID
     * @param money   订单总金额
     * @param type   类型  1支付宝 2微信
     */
    public void payAliMoney(String orderId, double money,  final OnUIThreadListener<ALi> uiThreadListener){
        getModel().prepay(orderId, money, 1, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                Log.i("Load",t);
                try {
                    ALi ali = JSON.parseObject(t, ALi.class);
                    uiThreadListener.onResult(ali);
                }catch (Exception e){
                    uiThreadListener.onErro("支付失败");
                }
            }

            @Override
            public void erro(String msg) {
                uiThreadListener.onErro(msg);
            }
        });
    }


    /**
     *
     * @param orderId 订单ID
     * @param money   订单总金额
     * @param type   类型  1支付宝 2微信
     */
    public void payMoney(String orderId, double money, int type, final OnUIThreadListener<WxPay.DataBean> uiThreadListener){
        getModel().prepay(orderId, money, type, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                Log.i("Load",t);
                try {
                    WxPay wxPay = JSON.parseObject(t, WxPay.class);
                    uiThreadListener.onResult(wxPay.getData());
                }catch (Exception e){
                    uiThreadListener.onErro("支付失败");
                }
            }

            @Override
            public void erro(String msg) {
                uiThreadListener.onErro(msg);
            }
        });
    }
}
