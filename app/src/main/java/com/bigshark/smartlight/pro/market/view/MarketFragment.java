package com.bigshark.smartlight.pro.market.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarKetListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.MarketNavigationBuilder;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.bigshark.smartlight.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bigShark on 2016/12/28.
 */

public class MarketFragment extends BaseFragment {
    @BindView(R.id.xrefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    private MarKetListAdapter goodsAdapter;
    private List<Market.Goods> goodsList;
    private MarketListPresenter presenter;

    @Override
    public int getContentView() {
        return R.layout.fragment_market;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
        initToolbar(viewContent);
        bindPresenter();
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

        rvContent.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvContent.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        rvContent.setHasFixedSize(true);

        initData(true);

        goodsAdapter = new MarKetListAdapter(getContext(), goodsList);
        rvContent.setAdapter(goodsAdapter);





        goodsAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
                GoodDetailsActivity.openGoodDetailsActivity(getActivity(),goodsList.get(postion).getId());
            }
        });

    }

    public void initData(final boolean isDownRefresh) {
        goodsList = new ArrayList<>();
        presenter.getGoodsList(isDownRefresh, new BasePresenter.OnUIThreadListener<List<Market.Goods>>() {
            @Override
            public void onResult(List<Market.Goods> result) {
                if (isDownRefresh) {
                    xRefreshView.stopRefresh();
                    if (result == null) {
                        ToastUtil.showToast(getContext(), "加载失败");
                        return;
                    }
                    goodsList.clear();
                } else {
                    xRefreshView.stopLoadMore();
                    if (result == null) {
                        ToastUtil.showToast(getContext(), "没有更多数据了");
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

    private void showMsg(String string){

    }
    @Override
    public MVPBasePresenter bindPresenter() {
        presenter = new MarketListPresenter(getContext());
        return presenter;
    }

    private void initToolbar(View viewContent) {
        MarketNavigationBuilder toolbar = new MarketNavigationBuilder(getContext());
        toolbar.setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }).setRightIcon(R.drawable.main_bottom_market_press)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarActivity.openCarActivity(getActivity());
                    }
                }).createAndBind((ViewGroup) viewContent);
    }
}
