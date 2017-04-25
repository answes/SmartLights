package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Messge;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.view.adapter.MessgeListAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessgeActivity extends BaseActivity {

    @BindView(R.id.rc_content)
    RecyclerView rcContent;
    @BindView(R.id.activity_messge)
    LinearLayout activityMessge;

    private MessgeListAdapter adapter;
    private List<Messge> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messge);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityMessge);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 2; i++) {
            Messge m = new Messge();
            m.setDate("2016-09-11");
            m.setMessge("双12豪礼送亲友，现特价商品5折销售，更多优惠净瓶请进入商场进行抢购....");
            m.setTitle("平台消息");
            datas.add(m);
        }
        rcContent.setLayoutManager(new LinearLayoutManager(this));
        rcContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.main_bottom_text_normal).build());
        adapter = new MessgeListAdapter(this,datas);
        rcContent.setAdapter(adapter);

    }


    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setTitle("消息中心")
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                })
                .createAndBind(activityMessge);
    }


    public static void openMessgeActivity(Activity activity){
        activity.startActivity(new Intent(activity,MessgeActivity.class));
    }
    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }
}
