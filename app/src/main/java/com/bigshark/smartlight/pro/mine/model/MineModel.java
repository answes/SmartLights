package com.bigshark.smartlight.pro.mine.model;

import android.content.Context;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.pro.base.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigShark on 2017/1/20.
 */

public class MineModel extends BaseModel {

    public MineModel(Context context) {
        super(context);
    }

    private String getLoginUrl(){
        return getServerUrl().concat("/User/login");
    }
    private String getUserInfoUrl(){return  getServerUrl().concat("/User/getuserinfo");}
    private String getRegisteredUrl(){
        return getServerUrl().concat("/User/reg");
    }
    private String getFindPswUrl(){
        return  getServerUrl().concat("/User/findpass");
    }
    private String resetPswUrl(){
        return  getServerUrl().concat("/User/resetpass");
    }
    private String getOrdresUrl(){
        return  getServerUrl().concat("/Order/orderlist");
    }
    private String getMessageUrl(){return  getServerUrl().concat("/Index/message");}
    private String upUserInfoUrl() {return  getServerUrl().concat("/User/upuinfo");}
    private String getBikeUrl(){return  getServerUrl().concat("/Bike/bikelist");}
    private String getOrdresDetailUrl(){
        return  getServerUrl().concat("/Order/orderdetail");
    }
    private String upOrderStatuUrl(){
        return  getServerUrl().concat("/Order/changeorderstatus");
    }




    public void login( String phone, String password ,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("tel",phone);
        requestParam.put("password",password);
        httpUtils.postData(getLoginUrl(), requestParam, httpResult);
    }


    public void registered( String phone, String password ,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("tel",phone);
        requestParam.put("password",password);
        //发送请求
        httpUtils.postData(getRegisteredUrl(), requestParam, httpResult);
    }

    public void getUserInfo(String userId,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id",userId);
        //发送请求
        httpUtils.postData(getUserInfoUrl(), requestParam, httpResult);
    }

    public void findPsw(String tel,String password,String repassword,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("tel",tel);
        requestParam.put("password",password);
        requestParam.put("repassword",repassword);
        httpUtils.postData(getFindPswUrl(), requestParam, httpResult);
    }

    public void resetPassword(String oldpassword,String newpassword,String repassword , VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("oldpassword",oldpassword);
        requestParam.put("newpassword",newpassword);
        requestParam.put("repassword",repassword);
        httpUtils.postData(resetPswUrl(), requestParam, httpResult);
    }

    /**
     * 订单状态0:待付款,1:待发货，2:已发货,3:已完成,-1,已取消
     * @param p 页数
     * @param status  不传默认全部 status = -1
     * @param httpResult
     */
    public void getOrders(int p,String status , VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id", SmartLightsApplication.USER.getId());
        requestParam.put("p",String.valueOf(p));
        if(!"-1".equals(status)) {
            requestParam.put("status", status);
        }
        httpUtils.postData(getOrdresUrl(), requestParam, httpResult);
    }

    /**
     * 获取系统信息
     * @param httpResult
     */
    public void getMessages(VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        httpUtils.postData(getMessageUrl(), requestParam, httpResult);
    }


    public void upUserInfo(VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id", SmartLightsApplication.USER.getId());
        requestParam.put("sex", SmartLightsApplication.USER.getSex());
        requestParam.put("height", SmartLightsApplication.USER.getHeight());
        requestParam.put("weight", SmartLightsApplication.USER.getWeight());
        requestParam.put("age", SmartLightsApplication.USER.getAge());
        requestParam.put("tel", SmartLightsApplication.USER.getTel());
        if(SmartLightsApplication.USER.getBtel().isEmpty()){
            SmartLightsApplication.USER.setBtel(SmartLightsApplication.USER.getTel());
        }
        requestParam.put("btel", SmartLightsApplication.USER.getBtel());
        requestParam.put("fig", String.valueOf(SmartLightsApplication.USER.getFig()));
        httpUtils.postData(upUserInfoUrl(), requestParam, httpResult);
    }

    public void getBikeList(int page,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id", SmartLightsApplication.USER.getId());
        requestParam.put("p", String.valueOf(3));
        httpUtils.postData(getBikeUrl(), requestParam, httpResult);
    }

    public void getOrderDetail(int type,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id", SmartLightsApplication.USER.getId());
        requestParam.put("id", String.valueOf(type));
        httpUtils.postData(getOrdresDetailUrl(), requestParam, httpResult);
    }

    public void upOrderStatu(int id, int status,int paytype,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("user_id", SmartLightsApplication.USER.getId());
        requestParam.put("id", String.valueOf(id));
        requestParam.put("status", String.valueOf(status));
        requestParam.put("paytype", String.valueOf(paytype));
        httpUtils.postData(upOrderStatuUrl(), requestParam, httpResult);
    }
}
