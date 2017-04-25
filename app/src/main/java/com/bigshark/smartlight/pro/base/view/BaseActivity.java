package com.bigshark.smartlight.pro.base.view;

import android.content.Context;
import android.widget.Toast;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.mvp.view.impl.MvpActivity;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

/**
 * 项目的baseactivity
 * Created by bigShark on 2016/12/19.
 */

public abstract class BaseActivity <P extends MVPBasePresenter> extends MvpActivity<P>{
    private long lastShowTime = 0;

    public void showMsg(String msg){
        if(System.currentTimeMillis() - lastShowTime >=2000){
            Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        }
    }
}
