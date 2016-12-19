package com.bigshark.smartlight.mvp.view;

/**
 * Created by bigShark on 2016/12/19.
 */

public interface IMVPLceView<M>  extends IMVPView {
    /**
     * 显示加载中的视图(加载类型)
     *
     * @param pullToRefresh
     */
    public void showLoading(boolean pullToRefresh);

    /**
     * 显示ContentView
     */
    public void showContent();

    /**
     * 显示错误信息
     */

    public void showError(Exception e, boolean pullToRefresh);

    /**
     * 绑定数据
     *
     * @param data
     */
    public void showData(M data);
}
