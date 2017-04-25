package com.bigshark.smartlight.pro.market.model;

import android.content.Context;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.Address;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.impl.RequestParam;
import com.bigshark.smartlight.http.impl.SystemHttpCommand;
import com.bigshark.smartlight.http.utils.HttpTask;
import com.bigshark.smartlight.http.utils.HttpUtils;
import com.bigshark.smartlight.pro.base.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 */

public class AddressModel extends BaseModel {

    public AddressModel(Context context) {
        super(context);
    }
    private String getAddressListURL( ){
        return getServerUrl().concat("/Address/lists");
    }

    private String setDefaultAddressURL(){
        return  getServerUrl().concat("/Address/setdefault");
    }

    private String delAddressURL(){
        return  getServerUrl().concat("/Address/del");
    }

    private String editAddressURL (){
        return  getServerUrl().concat("/Address/edit");
    }

    private String addAddressURL (){
        return  getServerUrl().concat("/Address/add");
    }

    /**
     * 获取地址列表
     * @param onHttpResultListener
     */
    public void getAddressList(VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap();
        requestParam.put("uid", SmartLightsApplication.USER.getId()+"");
        //发送请求
        httpUtils.postData(getAddressListURL(),requestParam,onHttpResultListener);
    }

    /**
     * 设置默认地址
     * @param id  地址id
     * @param onHttpResultListener
     */
    public void setDefultAddress(int id, VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap();
        requestParam.put("uid", SmartLightsApplication.USER.getId()+"");
        requestParam.put("id", id+"");
        //发送请求
        httpUtils.postData(setDefaultAddressURL(),requestParam,onHttpResultListener);
    }

    /**
     * 删除地址
     * @param id 地址id
     * @param onHttpResultListener
     */
    public void delAddress(int id, VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils volleyHttpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap();
       // requestParam.put("uid", SmartLightsApplication.USER.getId());
        requestParam.put("id", id+"");
        //发送请求
        volleyHttpUtils.postData(delAddressURL(),requestParam,onHttpResultListener);

    }

    /**
     * 编辑收货地址
     * @param address
     * @param httpResult
     */
    public void editAddress(AddressBean address, VolleyHttpUtils.HttpResult httpResult){
        VolleyHttpUtils volleyHttpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap();
        requestParam.put("uid", SmartLightsApplication.USER.getId()+"");
        requestParam.put("id", address.getId()+"");
        requestParam.put("province", address.getProvince());
        requestParam.put("city", address.getCity());
        requestParam.put("district", address.getDistrict());
        requestParam.put("detail", address.getDetail());
        requestParam.put("name", address.getName());
        requestParam.put("tel", address.getTel());
        volleyHttpUtils.postData(editAddressURL(),requestParam,httpResult);
    }

    /**
     * 增加收货地址
     * @param address
     * @param onHttpResultListener
     */
    public void addAddress(AddressBean address, VolleyHttpUtils.HttpResult onHttpResultListener){
        VolleyHttpUtils httpUtils = new VolleyHttpUtils();
        Map requestParam = new HashMap();
        requestParam.put("uid", SmartLightsApplication.USER.getId()+"");
        requestParam.put("province", address.getProvince());
        requestParam.put("city", address.getCity());
        requestParam.put("district", address.getDistrict());
        requestParam.put("detail", address.getDetail());
        requestParam.put("name", address.getName());
        requestParam.put("tel", address.getTel());
        httpUtils.postData(addAddressURL(),requestParam,onHttpResultListener);
    }
}
