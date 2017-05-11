package com.bigshark.smartlight;

import android.os.Bundle;

import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.LoginActivity;
import com.bigshark.smartlight.utils.SQLUtils;

public class LaunchingActivity extends BaseActivity {


    private MinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);

        //判断以前是否登陆
        final String userId = SQLUtils.getUser(this);
        if(null == userId){
            //开启一个线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //睡眠2秒，然后跳转到登录界面
                        Thread.sleep(2000);
                        //打开登陆界面
                        LoginActivity.openLoginActivity(LaunchingActivity.this);
                        //结束欢迎界面
                        LaunchingActivity.this.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            presenter.getUserInfo(new BasePresenter.OnUIThreadListener<LoginResult>() {
                @Override
                public void onResult(LoginResult result) {
                    SmartLightsApplication.USER = result.getData();
                    IndexActivity.openIndexActivity(LaunchingActivity.this);
                    LaunchingActivity.this.finish();
                }
                @Override
                public void onErro(String string) {
                    LoginActivity.openLoginActivity(LaunchingActivity.this);
                    LaunchingActivity.this.finish();
                }
            });
        }

    }

    @Override
    public MVPBasePresenter bindPresneter() {
       presenter = new MinePresenter(this);
        return presenter;
    }
}
