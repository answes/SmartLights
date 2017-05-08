package com.bigshark.smartlight.pro.mine.view.navigation;

import android.content.Context;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilder;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilderAdapter;

/**
 * Created by bigShark on 2016/12/29.
 */

public class MineNavigationBuilder extends NavigationBuilderAdapter {
    private String tvRight;


    public NavigationBuilder setRightText(String tvRight) {
        this.tvRight = tvRight;
        return this;
    }
    private String getTvRight(){
        return tvRight;
    }


    public MineNavigationBuilder(Context context) {
        super(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.toolbar_mine_layout;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setTitleTextView(R.id.tv_right,getTvRight(),getRightIconOnClickListener());
        setTitleTextView(R.id.tv_titlte,getTitle());
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setImageViewStyle(R.id.iv_right,getRightIconRes(),getRightIconOnClickListener());
    }
}
