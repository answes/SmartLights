package com.bigshark.smartlight.pro.mine.view.navigation;

import android.content.Context;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilderAdapter;

/**
 * Created by bigShark on 2017/1/21.
 */

public class RegiteredNavigationBuilder extends NavigationBuilderAdapter {
    private String rightString;

    public RegiteredNavigationBuilder(Context context) {
        super(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.toolbar_mine_regitered_layout;
    }

    public RegiteredNavigationBuilder setRightText(String rightString){
        this.rightString = rightString;
        return this;
    }

    public String getRightText(){
        return  rightString;
    }


    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setTitleTextView(R.id.tv_titlte,getTitle());
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setTitleTextView(R.id.tv_titlte,getRightText(),getRightIconOnClickListener());
    }
}
