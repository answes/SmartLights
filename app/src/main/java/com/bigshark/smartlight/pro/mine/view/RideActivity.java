package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.RideListAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的骑行
 */
public class RideActivity extends BaseActivity {

    @BindView(R.id.rv_content)
    RecyclerView ervContent;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;
    @BindView(R.id.activity_ride)
    LinearLayout activityRide;

    private MinePresenter minePresenter;
    private RideListAdapter adapter;
    private List<Ride.Bike> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityRide);
        initRecyclerView();
    }



    private void initRecyclerView() {

        xrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xrefreshview.setPullLoadEnable(true);
        // 设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        // 静默加载模式不能设置footerview
        // 设置支持自动刷新
        xrefreshview.setAutoLoadMore(true);
        //设置静默加载时提前加载的item个数
//		xRefreshView.setPreLoadCount(2);

        ervContent.setHasFixedSize(true);
        ervContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RideListAdapter(this,datas);
        ervContent.setAdapter(adapter);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(this));


        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                loadDatas(true);
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                loadDatas(false);
            }
        });


        loadDatas(true);

    }
    public void loadDatas(final boolean isDownRefresh) {
                minePresenter.getBikeList(isDownRefresh, new BasePresenter.OnUIThreadListener<List<Ride.Bike>>() {
                    @Override
                    public void onResult(List<Ride.Bike> result) {
                        if (isDownRefresh) {
                            xrefreshview.stopRefresh();
                        } else {
                            xrefreshview.stopLoadMore();
                        }
                        if(null!= result && result.size() != 0){
                            if(isDownRefresh){
                                datas.clear();
                            }
                            datas.addAll(result);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onErro(String string) {
                            showMsg(string);
                    }
                });
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的骑行") .createAndBind(activityRide);
    }


    public static void openRideActivity(Activity activity){
        activity.startActivity(new Intent(activity,RideActivity.class));
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }
}
