package com.bigshark.smartlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }
}
