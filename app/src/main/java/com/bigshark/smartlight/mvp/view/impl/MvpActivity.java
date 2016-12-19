package com.bigshark.smartlight.mvp.view.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.mvp.view.IMVPView;

/**
 * Created by bigShark on 2016/12/19.
 */

public abstract class MvpActivity<P extends MVPBasePresenter> extends AppCompatActivity implements IMVPView {

    protected  P presenter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = bindPresneter();
        if(presenter != null){
            presenter.attachView(this);
        }
    }

    public abstract P bindPresneter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
        }
    }
}
