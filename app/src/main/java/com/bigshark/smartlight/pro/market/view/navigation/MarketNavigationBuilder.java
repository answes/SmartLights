package com.bigshark.smartlight.pro.market.view.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilderAdapter;

/**
 * Created by bigShark on 2016/12/28.
 */

public class MarketNavigationBuilder extends NavigationBuilderAdapter {
    private View.OnClickListener centerOnClickListener;

    public MarketNavigationBuilder(Context context) {
        super(context);
    }

    public MarketNavigationBuilder setCenterOnClickListener(View.OnClickListener centerOnClickListener){
        this.centerOnClickListener = centerOnClickListener;
        return this;

    }
    @Override
    public int getLayoutId() {
        return R.layout.toolbar_market_search_layout;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setTitleTextView(R.id.tv_center,"搜索您想要的商品",getCenterOnClickListener());
        setImageViewStyle(R.id.iv_right,getRightIconRes(),getRightIconOnClickListener());
    }
    private View.OnClickListener getCenterOnClickListener(){
        return centerOnClickListener;
    }
}
