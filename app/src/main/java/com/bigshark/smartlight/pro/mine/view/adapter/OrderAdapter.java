package com.bigshark.smartlight.pro.mine.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bigshark.smartlight.pro.mine.view.OrderFragment;

import java.util.List;

/**
 * Created by bigShark on 2017/1/20.
 */

public class OrderAdapter extends FragmentStatePagerAdapter {
    public static final String TAB_TAG = "@bigShark@";
    private List<String> mTitles;
    public OrderAdapter(FragmentManager fm, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        OrderFragment fragment = new OrderFragment();
        String[] title = mTitles.get(position).split(TAB_TAG);
        fragment.setType(Integer.parseInt(title[1]));
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }
}
