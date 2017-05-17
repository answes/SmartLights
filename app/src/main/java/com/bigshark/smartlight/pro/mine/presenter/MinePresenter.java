package com.bigshark.smartlight.pro.mine.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.CodeBean;
import com.bigshark.smartlight.bean.FindPsw;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.bean.MessageResult;
import com.bigshark.smartlight.bean.OrderDetailResult;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.RegisterResult;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.mine.model.MineModel;
import com.bigshark.smartlight.utils.JSONUtil;

import java.util.List;

/**
 * Created by bigShark on 2017/1/20.
 */

public class MinePresenter extends BasePresenter<MineModel> {
    private static final String TAG = "MinePresenter";
    private int page = 1;

    public MinePresenter(Context context) {
        super(context);
    }

    LoginResult.User loginUser;

    public LoginResult.User getUser() {
        return loginUser;
    }

    @Override
    public MineModel bindModel() {
        return new MineModel(getContext());
    }

    public void login(String phone, String password, final OnUIThreadListener<LoginResult> onUIThreadListener) {

        getModel().login(phone, password, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e(TAG, "login USER = " + result);
                try {
                    LoginResult loginResult = JSONUtil.getObject(result, LoginResult.class);
                    if (loginResult.getCode() == 0) {
                        onUIThreadListener.onErro("用户名不存在或密码不正确");
                    } else {
                        onUIThreadListener.onResult(loginResult);
                    }
                }catch (Exception e){
                    onUIThreadListener.onErro("用户名不存在或密码不正确");
                }


            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    public void registered(String phone, String password, final OnUIThreadListener<RegisterResult> onUIThreadListener) {

        getModel().registered(phone, password, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                RegisterResult market = JSONUtil.getObject(result, RegisterResult.class);
                onUIThreadListener.onResult(market);
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    public void getUserInfo( String userId,final OnUIThreadListener<LoginResult> onUIThreadListener) {
        getModel().getUserInfo(userId,new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e(TAG, "login USER = " + result);
                try {
                    LoginResult loginResult = JSONUtil.getObject(result, LoginResult.class);
                        onUIThreadListener.onResult(loginResult);
                }catch (Exception e){
                    onUIThreadListener.onErro("用户名不存在或密码不正确");
                }
            }
            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }


    public void findPsw(String tel, String passWord, final String rePassWord, final OnUIThreadListener<FindPsw> onUIThreadListener) {
        getModel().findPsw(tel, passWord, rePassWord, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                FindPsw findPsw = JSONUtil.getObject(result, FindPsw.class);
                onUIThreadListener.onResult(findPsw);
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    public void resetPassword(String oldpassword,String newpassword,String repassword ,final OnUIThreadListener<FindPsw> onUIThreadListener){
        getModel().resetPassword(oldpassword, newpassword, repassword, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                FindPsw findPsw = JSONUtil.getObject(result, FindPsw.class);
                onUIThreadListener.onResult(findPsw);
            }

            @Override
            public void erro(String msg) {
            }
        });
    }

    public void getOrders(final boolean isDownRefresh, String status , final OnUIThreadListener<OrderResult> onUIThreadListener){
        if("-1".equals(status)){
            status = null;
        }
        getModel().getOrders( page, status, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                OrderResult orderResult = JSONUtil.getObject(result, OrderResult.class);
                    if (isDownRefresh) {
                        page = 1;
                    } else {
                        page++;
                    }
                    onUIThreadListener.onResult(orderResult);
            }
            @Override
            public void erro(String msg) {
                onUIThreadListener.onResult(null);
            }
        });
    }


    /**
     * 获取系统信息
     * @param onUIThreadListener
     */
    public void getMessages(final OnUIThreadListener<MessageResult> onUIThreadListener){
        getModel().getMessages(  new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                if (1 == code) {
                    MessageResult orderResult = JSONUtil.getObject(result, MessageResult.class);
                    onUIThreadListener.onResult(orderResult);
                } else {
                    onUIThreadListener.onErro("");
                }
            }
            @Override
            public void erro(String msg) {
            }
        });
    }

    public void upUserInfo(final OnUIThreadListener<String> onUIThreadListener){
        getModel().upUserInfo( new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e(TAG, "upUserInfo " + result);
                onUIThreadListener.onResult(result);
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

    public void getBikeList(final boolean isDownRefresh, final OnUIThreadListener<List<Ride.Bike>> onUIThreadListener){
        getModel().getBikeList( page,  new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e(TAG, "succss: "+result );
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                if (1 == code) {
                    Ride bike = JSONUtil.getObject(result, Ride.class);
                    if (isDownRefresh) {
                        page = 1;
                    } else {
                        page++;
                    }
                    onUIThreadListener.onResult(bike.getData());
                } else {
                    onUIThreadListener.onErro("");
                }
            }
            @Override
            public void erro(String msg) {
            }
        });
    }


    public void getOrderDetail(int type , final OnUIThreadListener<OrderDetailResult.OrderDetail> onUIThreadListener){
        getModel().getOrderDetail(type, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                if(result.isEmpty()){
                    onUIThreadListener.onErro("");
                    return;
                }
                OrderDetailResult detail = JSONUtil.getObject(result, OrderDetailResult.class);
                onUIThreadListener.onResult(detail.getData());
            }

            @Override
            public void erro(String msg) {
            }
        });
    }


    public void upOrderStatu(int id, int status,int paytype , final OnUIThreadListener<Integer> onUIThreadListener){
        getModel().upOrderStatu(id,status,paytype,new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                Log.e(TAG, "upOrderStatu: "+result);
                if(result.isEmpty()){
                    onUIThreadListener.onErro("");
                    return;
                }
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                onUIThreadListener.onResult(code);
            }

            @Override
            public void erro(String msg) {
            }
        });
    }

    public void getCode(final OnUIThreadListener<String> onUIThreadListener){
        getModel().getCode(SmartLightsApplication.USER.getTel(), new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String t) {
                Log.i("Load",t);
                CodeBean codeBean = JSON.parseObject(t,CodeBean.class);
                if(codeBean.getCode() == 1){
                    onUIThreadListener.onResult(String.valueOf(codeBean.getData()));
                }else{
                    onUIThreadListener.onErro(codeBean.getExtra());
                }
            }

            @Override
            public void erro(String msg) {
                onUIThreadListener.onErro(msg);
            }
        });
    }

}
