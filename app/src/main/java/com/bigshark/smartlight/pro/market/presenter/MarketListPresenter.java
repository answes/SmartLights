package com.bigshark.smartlight.pro.market.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.bean.GoodDetail;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.Result;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.utils.HttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.market.model.MaketListModel;
import com.bigshark.smartlight.utils.JSONUtil;

import java.util.List;

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
    public void addGoodToCar(int gid, String price, final OnUIThreadListener<String> onUIThreadListener) {

        getModel().addGoodToCart(gid, price, new VolleyHttpUtils.HttpResult() {
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
                    Result result1 = JSON.parseObject(result, Result.class);
                    if (result1.getCode() == 1) {
                        onUIThreadListener.onResult(result1.getExtra());
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


}
