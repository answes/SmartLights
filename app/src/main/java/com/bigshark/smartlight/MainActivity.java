package com.bigshark.smartlight;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bigshark.smartlight.pro.index.view.IndexFragment;
import com.bigshark.smartlight.pro.market.view.MarketFragment;
import com.bigshark.smartlight.pro.mine.view.MineFragment;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {

    private List<TabItem> tabItemList;

    public static void oppenMainActivity(Activity activity){
        activity.startActivity(new Intent(activity,MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost rootView = (FragmentTabHost) findViewById(android.R.id.tabhost);
        SupportMultipleScreensUtil.init(getApplication());
        initTabData();
        initTabHost();
        SupportMultipleScreensUtil.scale(rootView);
    }

    //初始化Tab数据
    private void initTabData() {

        tabItemList = new ArrayList<>();

        //添加首页Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_index_normal
                , R.drawable.main_bottom_index_press, R.string.main_index_text, IndexFragment.class));

        //添加商场Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_market_normal
                , R.drawable.main_bottom_market_press, R.string.main_shopping_text, MarketFragment.class));

        //添加我的Tab
        tabItemList.add(new TabItem(R.drawable.main_bottom_mine_normal
                , R.drawable.main_bottom_mine_press, R.string.main_mine_text, MineFragment.class));

    }


    private void initTabHost() {
        //获取FragmentTabHost
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //绑定TabHost(绑定我们的body)
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabItemList.size(); i++) {
            TabItem tabItem = tabItemList.get(i);
            //绑定Fragment(将Fragment添加到FragmentTabHost组件上面)
            TabHost.TabSpec tabSpec = fragmentTabHost.
                    newTabSpec(tabItem.getTitleString())
                    .setIndicator(tabItem.getView());
            //添加Fragment
            //tabSpec:选项卡
            //tabItem.getFragmentClass():具体的Fragment
            //tabItem.getBundle():给我们的具体的Fragment传参数
            fragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), tabItem.getBundle());
            //给我们的Tab按钮设置背景
            fragmentTabHost.getTabWidget()
                    .getChildAt(i)
                    .setBackgroundColor(getResources().getColor(R.color.mian_bottom_bar_bg));
            //监听点击Tab
            fragmentTabHost.setOnTabChangedListener(this);
            //默认选中第一个Tab
            if (i == 0) {
                tabItem.setChecked(true);
            }
        }
    }


    @Override
    public void onTabChanged(String tabId) {

        for (int i = 0; i < tabItemList.size(); i++) {
            TabItem tabItem = tabItemList.get(i);
            if (tabId.equals(tabItem.getTitleString())) {
                //选中
                tabItem.setChecked(true);
            } else {
                //没有选中Tab样式为正常
                tabItem.setChecked(false);
            }
        }


    }


    class TabItem {
        //正常情况下显示的图片
        private int imageNormal;
        //选中情况下显示的图片
        private int imagePress;
        private int title;
        private String titleString;
        private Class<? extends Fragment> fragmentClass;

        private View view;
        private ImageView imageView;
        private TextView textView;
        private Bundle bundle;

        public TabItem(int imageNormal, int imagePress, int title, Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public int getTitle() {
            return title;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if (TextUtils.isEmpty(titleString)) {
                titleString = getResources().getString(title);
            }
            return titleString;
        }

        public Bundle getBundle() {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("title", getTitleString());
            return bundle;
        }

        //还需要提供一个切换Tab方法---改变Tab样式
        public void setChecked(boolean isChecked) {
            if (imageView != null) {
                if (isChecked) {
                    imageView.setImageResource(imagePress);
                } else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if (textView != null && title != 0) {
                if (isChecked) {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_press));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.main_bottom_text_normal));
                }
            }
        }

        public View getView() {
            if (this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                this.imageView = (ImageView) this.view.findViewById(R.id.iv_tab);
                this.textView = (TextView) this.view.findViewById(R.id.tv_tab);
                //判断资源是否存在,不再我就因此
                if (this.title == 0) {
                    this.textView.setVisibility(View.GONE);
                } else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                //绑定图片默认资源
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }
    }
}
