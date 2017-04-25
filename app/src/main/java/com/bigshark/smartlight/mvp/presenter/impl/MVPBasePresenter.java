package com.bigshark.smartlight.mvp.presenter.impl;
import com.bigshark.smartlight.mvp.presenter.IMVPPresenter;
import com.bigshark.smartlight.mvp.view.IMVPView;

/**
 * Created by bigShark on 2016/12/19.
 */

public class MVPBasePresenter<V extends IMVPView> implements IMVPPresenter<V> {

    private V  view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view !=null){
            view = null;
        }
    }
}
