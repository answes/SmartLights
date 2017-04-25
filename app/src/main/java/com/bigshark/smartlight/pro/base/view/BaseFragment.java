package com.bigshark.smartlight.pro.base.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.mvp.view.impl.MVPBaseFragment;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

/**
 * baseFragment
 * Created by bigShark on 2016/12/19.
 */

public abstract class BaseFragment<P extends MVPBasePresenter> extends MVPBaseFragment<P> {
    private View viewContent;   //缓存视图
    private boolean isInit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewContent == null) {
            viewContent = inflater.inflate(getContentView(), container, false);
            SupportMultipleScreensUtil.scale(viewContent);
            initContentView(viewContent);
        }

        //判断Fragment 对应的activity是否纯在这个视图
        ViewGroup parent = (ViewGroup) viewContent.getParent();

        if (parent != null) {
            //如果存在 就移除，重新添加，这样的方式就可以缓存视图
            parent.removeView(viewContent);
        }

        return viewContent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isInit){
            this.isInit = true;
            initData();
        }
    }

    @Override
    public P bindPresenter() {
        return null;
    }



    public abstract int getContentView();

    public abstract void initContentView(View viewContent);

    public void initData() {

    }
}
