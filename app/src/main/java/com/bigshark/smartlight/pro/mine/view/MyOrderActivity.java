package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends AppCompatActivity {
    public static final String TAB_TAG = "@bigShark@";

    @BindView(R.id.tab_order)
    TabLayout tabOrder;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.activity_order)
    LinearLayout activityOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityOrder);
        tabOrder.setupWithViewPager(vpOrder);
        vpOrder.setAdapter(new PagerAdapter(getSupportFragmentManager()));

    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的订单").createAndBind(activityOrder);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        String[] titles = getResources().getStringArray(R.array.mine_order_tab);

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            MyOrderFragment fragment = new MyOrderFragment();
            String[] title = titles[position].split(TAB_TAG);
            fragment.setType(title[1]);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position].split(TAB_TAG)[0];
        }
    }

    public static void openMyOrderActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MyOrderActivity.class));
    }
}
