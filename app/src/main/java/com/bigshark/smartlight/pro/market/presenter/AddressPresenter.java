package com.bigshark.smartlight.pro.market.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.bean.Address;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.bean.Result;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.utils.HttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.market.model.AddressModel;
import com.bigshark.smartlight.utils.JSONUtil;

import java.util.List;


/**
 * Created by Administrator on 2017/2/27.
 */

public class AddressPresenter extends BasePresenter<AddressModel> {
    private final String  TAG = "AddressPresenter = ";

    public AddressPresenter(Context context) {
        super(context);
    }
    @Override
    public AddressModel bindModel() {
        return new AddressModel(getContext());
    }

    /**
     * 获取全部地址
     * @param onUIThreadListener
     */
    public void getAddressList( final OnUIThreadListener<List<AddressBean>> onUIThreadListener){
        getModel().getAddressList(new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                try {
                    Address address = JSON.parseObject(t,Address.class);
                    if(address.getCode() == 1){
                        onUIThreadListener.onResult(address.getData());
                    }else{
                        onUIThreadListener.onErro(address.getExtra());
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("没有收获地址");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });

    }

    /**
     *设置默认地址
     * @param id 地址id
     * @param onUIThreadListener
     */
    public  void setDefultAddress(int id, final OnUIThreadListener<String> onUIThreadListener){
        getModel().setDefultAddress(id, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                try {
                    Result result = JSON.parseObject(t, Result.class);
                    if(result.getCode() ==1){
                        onUIThreadListener.onResult("设置默认地址成功");
                    }else{
                        onUIThreadListener.onErro(result.getExtra());
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("设置默认地址失败");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     *删除地址
     * @param id 地址id
     * @param onUIThreadListener
     */
    public  void delAddress(int id ,final OnUIThreadListener<String> onUIThreadListener){
        getModel().delAddress(id, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                try{
                    Result result = JSON.parseObject(t,Result.class);
                    if(result.getCode() == 1){
                        onUIThreadListener.onResult(result.getExtra());
                    }else{
                        onUIThreadListener.onErro(result.getExtra());
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("删除地址失败");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     * 编辑地址
     * @param address 编辑好的地址
     * @param onUIThreadListener
     */
    public void editAddress(AddressBean address,final OnUIThreadListener<String> onUIThreadListener){
        getModel().editAddress(address, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                try {
                    Result result = JSON.parseObject(t,Result.class);
                    if(result.getCode() == 1) {
                        onUIThreadListener.onResult(result.getExtra());
                    }else {
                        onUIThreadListener.onErro(result.getExtra());
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("编辑地址失败");
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    /**
     * 增加地址
     * @param address 编辑好的地址
     * @param onUIThreadListener
     */
    public void addAddress(AddressBean address,final OnUIThreadListener<String> onUIThreadListener){
        getModel().addAddress(address,new VolleyHttpUtils.HttpResult(){
            @Override
            public void succss(String t) {
                try {
                    Result result = JSON.parseObject(t, Result.class);
                    if(result.getCode() == 1){
                        onUIThreadListener.onResult(result.getExtra());
                    }else {
                        onUIThreadListener.onErro(result.getExtra());
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("添加地址失败");
                }
                Log.i("Load",t);
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }
}
