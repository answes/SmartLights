package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.GoodDetailsActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarketViewHolder;
import com.bigshark.smartlight.pro.mine.view.adapter.RideViewHolder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RideActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.erv_content)
    EasyRecyclerView ervContent;
    @BindView(R.id.activity_ride)
    LinearLayout activityRide;

    private Handler handler = new Handler();
    private boolean hasNetWork = true;
    private List<Ride> datas = new ArrayList<>();
    private RecyclerArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityRide);
        initData();
        initRecyclerView();
    }

    private void initData() {
        for(int i = 0;i<2;i++){
            Ride r = new Ride();
            r.setDistance(4.99);
            r.setTime("2016-09-11 12:12:22");
            datas.add(r);
        }
    }


    private void initRecyclerView() {
        ervContent.setLayoutManager(new LinearLayoutManager(this));
        ervContent.setAdapterWithProgress( adapter = new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RideViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        });

        adapter.setMore(R.layout.view_more,this);
        adapter.setNoMore(R.layout.view_nomore, new RecyclerArrayAdapter.OnNoMoreListener() {
            @Override
            public void onNoMoreShow() {
                adapter.resumeMore();
            }

            @Override
            public void onNoMoreClick() {
                adapter.resumeMore();
            }
        });
        ervContent.setRefreshListener(this);
        onRefresh();
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });

    }
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                //刷新
                if (!hasNetWork) {
                    adapter.pauseMore();
                    return;
                }
                adapter.addAll(datas);
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新
                adapter.pauseMore();
//                if (!hasNetWork) {
//
//                    return;
//                }
//                adapter.addAll(datas);
            }
        }, 100);
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
        return null;
    }
}
