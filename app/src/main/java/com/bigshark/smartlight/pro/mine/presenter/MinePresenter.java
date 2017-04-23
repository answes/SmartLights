package com.bigshark.smartlight.pro.mine.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.FindPsw;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.bean.MessageResult;
import com.bigshark.smartlight.bean.Messge;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.bean.RegisterResult;
import com.bigshark.smartlight.http.VolleyHttpUtils;
import com.bigshark.smartlight.http.utils.HttpUtils;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.market.model.MaketListModel;
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

    public void getOrders(final boolean isDownRefresh,String status ,final OnUIThreadListener<OrderResult> onUIThreadListener){
        getModel().getOrders( page, status, new VolleyHttpUtils.HttpResult() {
            @Override
            public void succss(String result) {
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                Log.e(TAG, "succss: "+result);
                if (1 == code) {
                    OrderResult orderResult = JSONUtil.getObject(result, OrderResult.class);
                    if (isDownRefresh) {
                        page = 1;
                    } else {
                        page++;
                    }
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
                    onUIThreadListener.onResult(result);
            }

            @Override
            public void erro(String msg) {
            }
        });
    }

}
