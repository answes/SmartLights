package com.bigshark.smartlight.pro.market.view;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarketViewHolder;
import com.bigshark.smartlight.pro.market.view.navigation.MarketNavigationBuilder;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bigShark on 2016/12/28.
 */

public class MarketFragment extends BaseFragment implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.erv_content)
    EasyRecyclerView ervContent;
    private RecyclerArrayAdapter adapter;
    private List<Market> datas;
    private Handler handler = new Handler();

    private boolean hasNetWork = true;

    @Override
    public int getContentView() {
        return R.layout.fragment_market;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
        initToolbar(viewContent);
        initData();
        initRecyclerView();
    }

    @Override
    public void initData() {
        super.initData();
        datas = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            Market market = new Market();
            datas.add(market);
        }
    }

    private void initRecyclerView() {
        ervContent.setLayoutManager(new GridLayoutManager(getActivity(),2));
       ervContent.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        ervContent.setAdapterWithProgress( adapter = new RecyclerArrayAdapter(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MarketViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                //GoodDetailsActivity.openGoodDetailsActivity(getActivity());
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

    private void initToolbar(View viewContent) {
        MarketNavigationBuilder toolbar = new MarketNavigationBuilder(getContext());
        toolbar.setRightIcon(R.drawable.fragment_market_toolbar_in)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).createAndBind((ViewGroup) viewContent);
    }
}
