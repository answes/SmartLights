package com.bigshark.smartlight.pro.mine.model;

import android.content.Context;

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
}
