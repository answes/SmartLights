package com.bigshark.smartlight.mvp.view.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.mvp.view.IMVPView;

/**
 * Created by bigShark on 2016/12/19.
 */

public abstract class MVPBaseFragment<P extends MVPBasePresenter> extends Fragment implements IMVPView {

    protected  P presenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = bindPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    public abstract P bindPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
