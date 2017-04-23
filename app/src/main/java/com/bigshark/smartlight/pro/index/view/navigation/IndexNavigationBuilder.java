package com.bigshark.smartlight.pro.index.view.navigation;

import android.content.Context;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilderAdapter;

/**
 * Created by bigShark on 2016/12/29.
 */

public class IndexNavigationBuilder extends NavigationBuilderAdapter {

    public IndexNavigationBuilder(Context context) {
        super(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.toolbar_mine_layout;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setTitleTextView(R.id.tv_titlte,getTitle());
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setImageViewStyle(R.id.iv_right,getRightIconRes(),getRightIconOnClickListener());
    }
}
