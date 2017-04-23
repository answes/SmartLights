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

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.CarActivity;
import com.bigshark.smartlight.pro.market.view.GoodDetailsActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarKetListAdapter;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarketViewHolder;
import com.bigshark.smartlight.pro.market.view.navigation.MarketNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketActivity extends BaseActivity {

    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.activity_stroke)
    LinearLayout activityStroke;

    private MarKetListAdapter goodsAdapter;
    private List<Market.Goods> goodsList;
    private MarketListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityStroke);
        initRecycclerView();
    }
    private void initRecycclerView() {
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        // 静默加载模式不能设置footerview
        // 设置支持自动刷新
        xRefreshView.setAutoLoadMore(true);
        //设置静默加载时提前加载的item个数
        //		xRefreshView.setPreLoadCount(2);

        rvContent.setLayoutManager(new GridLayoutManager(this,2));
        rvContent.addItemDecoration(new DividerGridItemDecoration(this));
        rvContent.setHasFixedSize(true);

        initData(true);

        goodsAdapter = new MarKetListAdapter(this,goodsList);
        rvContent.setAdapter(goodsAdapter);

        goodsAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener(){
            @Override
            public void onRefresh() {
                initData(true);
            }
            @Override
            public void onLoadMore(boolean isSlience) {
                initData(false);
            }
        });

        goodsAdapter.setItemOnClickListener(new MarKetListAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                GoodDetailsActivity.openGoodDetailsActivity(MarketActivity.this,goodsList.get(postion).getId());
            }
        });

    }
    public void initData(final boolean isDownRefresh) {
        goodsList = new ArrayList<>();
        presenter.getGoodsList(isDownRefresh,new BasePresenter.OnUIThreadListener<List<Market.Goods>>(){
            @Override
            public void onResult(List<Market.Goods> result) {
                if (isDownRefresh) {
                    xRefreshView.stopRefresh();
                    if(result == null){
                        ToastUtil.showToast(MarketActivity.this,"加载失败");
                        return;
                    }
                    goodsList.clear();
                } else {
                    xRefreshView.stopLoadMore();
                    if(result == null){
                        ToastUtil.showToast(MarketActivity.this,"没有更多数据了");
                        return;
                    }
                }
                goodsList.addAll(result);
                goodsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }
    private void initToolbar() {
        MarketNavigationBuilder toolbar = new MarketNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setRightIcon(R.drawable.main_bottom_market_press)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarActivity.openCarActivity(MarketActivity.this);
                    }
                }).createAndBind(activityStroke);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MarketListPresenter(this);
        return presenter;
    }

    public static void openMarketActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MarketActivity.class));
    }
}
