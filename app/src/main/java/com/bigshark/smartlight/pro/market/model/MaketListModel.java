package com.bigshark.smartlight.pro.market.model;

import android.content.Context;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.http.VolleyHttpUtils;
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

    private String getPrepayURL(){
        return  getServerUrl().concat("/Order/prepay");
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
        requestParam.put("address",orderResult.getData().get(0).getAddress());
        requestParam.put("tel",orderResult.getData().get(0).getTel());
        requestParam.put("gitems", gson.toJson(orderResult.getData().get(0).getGitems1()));
        requestParam.put("gmoney",orderResult.getData().get(0).getGmoney()+"");
        requestParam.put("omoney",orderResult.getData().get(0).getOmoney()+"");
        requestParam.put("buyer_id",String.valueOf(SmartLightsApplication.USER.getId()));
        requestParam.put("username",SmartLightsApplication.USER.getName());
        requestParam.put("address",orderResult.getData().get(0).getAddress());
        requestParam.put("tel",orderResult.getData().get(0).getTel());
        requestParam.put("gmoney",String.valueOf(orderResult.getData().get(0).getGmoney()));
        requestParam.put("omoney",String.valueOf(orderResult.getData().get(0).getOmoney()));
        //发送请求
        httpUtils.postData(subOrderURL(),requestParam,onHttpResultListener);
    }

    public void prepay(String orderId,double money,int type, VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map<String,String> requser = new HashMap<>();
        requser.put("name",SmartLightsApplication.USER.getName()+"");
        requser.put("id",orderId);
        requser.put("money",String.valueOf(money));
        requser.put("type",String.valueOf(type));
        httpUtils.postData(getPrepayURL(),requser,onHttpResultListener);
    }

}
