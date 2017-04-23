package com.bigshark.smartlight.pro.mine.model;

import android.content.Context;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.impl.RequestParam;
import com.bigshark.smartlight.http.impl.SystemHttpCommand;
import com.bigshark.smartlight.http.utils.HttpTask;
import com.bigshark.smartlight.http.utils.HttpUtils;
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
    private String upLoadImgUrl(){return getServerUrl().concat("/File/uploadPicture");}
    private String upUserInfoUrl() {return  getServerUrl().concat("/User/upuinfo");}



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


    public void upLoadImg(String url,VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        httpUtils.postData(getMessageUrl(), requestParam, httpResult);
    }

    public void upUserInfo(VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap<>();
        requestParam.put("sex", SmartLightsApplication.USER.getSex().equals("男") ? 1 : 0);
        requestParam.put("height", Integer.parseInt(SmartLightsApplication.USER.getHeight()));
        requestParam.put("weight", Integer.parseInt(SmartLightsApplication.USER.getWeight()));
        requestParam.put("age", Integer.parseInt(SmartLightsApplication.USER.getAge()));
        requestParam.put("tel", SmartLightsApplication.USER.getTel());
        requestParam.put("btel", SmartLightsApplication.USER.getBtel());
        requestParam.put("fig", SmartLightsApplication.USER.getFig());
        httpUtils.postData(getMessageUrl(), requestParam, httpResult);
    }
}
