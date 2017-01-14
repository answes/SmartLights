package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.CarActivity;
import com.bigshark.smartlight.pro.market.view.GoodDetailsActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarketViewHolder;
import com.bigshark.smartlight.pro.market.view.navigation.MarketNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.erv_content)
    EasyRecyclerView ervContent;
    @BindView(R.id.activity_stroke)
    LinearLayout activityStroke;

    private RecyclerArrayAdapter adapter;
    private List<Market> datas;
    private Handler handler = new Handler();
    private boolean hasNetWork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);
        initToolbar();
        initData();
        initRecyclerView();
    }

    public void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            Market market = new Market();
            datas.add(market);
        }
    }

    private void initRecyclerView() {
        ervContent.setLayoutManager(new GridLayoutManager(this,2));
        ervContent.addItemDecoration(new DividerGridItemDecoration(this));

        ervContent.setAdapterWithProgress( adapter = new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MarketViewHolder(parent);
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
                GoodDetailsActivity.openGoodDetailsActivity(MarketActivity.this);
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
        MarketNavigationBuilder toolbar = new MarketNavigationBuilder(this);
        toolbar.setRightIcon(R.drawable.main_bottom_market_press)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarActivity.openCarActivity(MarketActivity.this);
                    }
                }).createAndBind(activityStroke);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    public static void openMarketActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MarketActivity.class));
    }
}
