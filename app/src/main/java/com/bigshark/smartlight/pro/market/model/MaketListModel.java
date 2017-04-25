package com.bigshark.smartlight.pro.market.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.impl.RequestParam;
import com.bigshark.smartlight.http.impl.SystemHttpCommand;
import com.bigshark.smartlight.http.utils.HttpTask;
import com.bigshark.smartlight.http.utils.HttpUtils;
import com.bigshark.smartlight.pro.base.model.BaseModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigShark on 2017/1/20.
 */

public class MaketListModel extends BaseModel {

    public MaketListModel(Context context) {
        super(context);
    }

    private String getGoodListUrl(){
        return getServerUrl().concat("/Goods/goodslist");
    }
    private String addGoodToCarUrl(){
        return getServerUrl().concat("/Cart/add");
    }
    private String delGoodToCarUrl(){
        return getServerUrl().concat("/Cart/del");
    }
    private String updateGoodNumUrl(){
        return getServerUrl().concat("/Cart/upnum");
    }
    private String getGoodDetailsRul(){
        return  getServerUrl().concat("/Goods/goodsdetail");
    }
    private String getCarListUrl(){
        return getServerUrl().concat("/Cart/lists");
    }
    private String subOrderURL(){
        return getServerUrl().concat("/Order/suborder");
    }
    public void getMarketList( int page,VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("p",String.valueOf(page));
        //发送请求
        httpUtils.postData(getGoodListUrl(),requestParam,onHttpResultListener);
    }

    /**
     * 获取商品详情
     * @param gid 商品id
     * @param onHttpResultListener
     */
    public void getGoodDetail( int gid,VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("id",String.valueOf(gid));
        //发送请求
        httpUtils.postData(getGoodDetailsRul(),requestParam,onHttpResultListener);
    }

    /**
     * 添加购物车
     * @param gid
     * @param onHttpResultListener
     */
    public void addGoodToCart( int gid, String price, VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("uid", SmartLightsApplication.USER.getId());
        requestParam.put("gid",gid+"");
       // requestParam.put("name",name);
        requestParam.put("price",price);
       // requestParam.put("img_url",imgUrl);
        //发送请求
        httpUtils.postData(addGoodToCarUrl(),requestParam,onHttpResultListener);
    }

    /**
     * 全删请传uid,指定删除条目传id
     * @param gid
     * @param onHttpResultListener
     */
    public void delGoodToCart(boolean isAllDel, int gid,VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        if(isAllDel) {
            requestParam.put("uid",String.valueOf(SmartLightsApplication.USER.getId()));
        }else {
            requestParam.put("id", String.valueOf(gid));
        }
        //发送请求
        httpUtils.postData(delGoodToCarUrl(),requestParam,onHttpResultListener);
    }

    /**
     *
     * @param gid  修改的id
     * @param num  修改的数量
     * @param onHttpResultListener
     */
    public void updateGoodNum(int gid,int num,VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("num",num+"");
        requestParam.put("id",gid+"");
        //发送请求
        httpUtils.postData(updateGoodNumUrl(),requestParam,onHttpResultListener);

    }

    /**
     * 获取购物车列表
     * @param onHttpResultListener
     */
    public void getCarGoodList(VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("uid",SmartLightsApplication.USER.getId());
        //发送请求
        httpUtils.postData(getCarListUrl(),requestParam,onHttpResultListener);
    }

    public void subOrder(OrderResult orderResult, VolleyHttpUtils.HttpResult onHttpResultListener){
        Gson gson = new Gson();
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("buyer_id",SmartLightsApplication.USER.getId()+"");
        requestParam.put("username",SmartLightsApplication.USER.getName());
        requestParam.put("address",orderResult.getData().getAddress());
        requestParam.put("tel",orderResult.getData().getTel());
        //TODO 这里转换成数组有待修改
        requestParam.put("gitems", gson.toJson(orderResult.getData().getGitemsl()));
        requestParam.put("gmoney",orderResult.getData().getGmoney()+"");
        requestParam.put("omoney",orderResult.getData().getOmoney()+"");
        //发送请求
        httpUtils.postData(subOrderURL(),requestParam,onHttpResultListener);
    }

}
