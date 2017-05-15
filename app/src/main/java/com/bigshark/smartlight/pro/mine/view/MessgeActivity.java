package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.MessageResult;
import com.bigshark.smartlight.bean.Messge;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
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
    private MinePresenter minePresenter;

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
        logingData();
        rcContent.setLayoutManager(new LinearLayoutManager(this));
        rcContent.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.lines).build());
        adapter = new MessgeListAdapter(this,datas);
        rcContent.setAdapter(adapter);

    }

    private void logingData() {
        minePresenter.getMessages(new BasePresenter.OnUIThreadListener<MessageResult>() {
            @Override
            public void onResult(MessageResult result) {
                if( null == result.getData() || result.getData().size() == 0){
                    showMsg("获取数据失败，请稍后再试");
                }else{
                    datas.clear();
                    datas.addAll(result.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErro(String string) {

            }
        });
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
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }
}
