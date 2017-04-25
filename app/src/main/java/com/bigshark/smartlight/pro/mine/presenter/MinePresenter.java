package com.bigshark.smartlight.pro.mine.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.FindPsw;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.bean.Market;
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

}
