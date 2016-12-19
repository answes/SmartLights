package com.bigshark.smartlight.mvp.presenter;

import com.bigshark.smartlight.mvp.view.IMVPView;

/**
 * Created by bigShark on 2016/12/19.
 */

public interface IMVPPresenter<V extends IMVPView> {
    /**
     * 绑定view
     * @param view
     */
    public void  attachView(V view);

    /**
     * 解除对view的绑定
     */
    public void detachView();
}
