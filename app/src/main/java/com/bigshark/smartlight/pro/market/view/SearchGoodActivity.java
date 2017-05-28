package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.MarKetListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.SearchNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.MarketActivity;
import com.bigshark.smartlight.utils.DividerGridItemDecoration;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchGoodActivity extends BaseActivity {

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.rootview)
    LinearLayout rootview;

    private MarKetListAdapter goodsAdapter;
    private static List<Market.Goods> allGoods = new ArrayList<>();
    private List<Market.Goods> searchGoods = new ArrayList<>();
   // private MarketListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_good);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(rootview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        rvContent.setLayoutManager(new GridLayoutManager(this,2));
        rvContent.addItemDecoration(new DividerGridItemDecoration(this));
        rvContent.setHasFixedSize(true);
        goodsAdapter = new MarKetListAdapter(this,searchGoods);
        rvContent.setAdapter(goodsAdapter);
        goodsAdapter.setItemOnClickListener(new MarKetListAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                GoodDetailsActivity.openGoodDetailsActivity(SearchGoodActivity.this,searchGoods.get(postion).getId());
            }
        });
    }

    private void initToolbar() {
        SearchNavigationBuilder toolbar = new SearchNavigationBuilder(this);
        toolbar.setSearch("搜索").setSearchListener(new SearchNavigationBuilder.SearchLinstener() {
            @Override
            public void search(String searchText) {
                    searchRun(searchText);
            }
        }).setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).createAndBind(rootview);
    }

    private void searchRun(String searchText) {
        searchGoods.clear();
        if(null != allGoods && allGoods.size() != 0){
            for (Market.Goods g: allGoods){
                if(g.getName().indexOf(searchText) != -1){
                    searchGoods.add(g);
                }
            }
        }
        goodsAdapter.notifyDataSetChanged();
    }

    public static void openSearchGoodActivity(Activity a,List<Market.Goods> goods){
        Intent in = new Intent(a,SearchGoodActivity.class);
        allGoods.addAll(goods);
        a.startActivity(in);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
       // presenter =new  MarketListPresenter(this);
        return null;
    }


}
