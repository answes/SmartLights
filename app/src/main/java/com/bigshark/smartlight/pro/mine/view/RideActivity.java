package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.RideListAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的骑行
 */
public class RideActivity extends BaseActivity {

    @BindView(R.id.rv_content)
    RecyclerView ervContent;
    @BindView(R.id.activity_ride)
    LinearLayout activityRide;

    private MinePresenter minePresenter;
    private RideListAdapter adapter;
    private List<Ride.Bike> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityRide);
        initRecyclerView();
    }



    private void initRecyclerView() {
        ervContent.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RideListAdapter(this,datas);
        ervContent.setAdapter(adapter);
        onRefresh();
    }
    public void onRefresh() {
                minePresenter.getBikeList(true, new BasePresenter.OnUIThreadListener<List<Ride.Bike>>() {
                    @Override
                    public void onResult(List<Ride.Bike> result) {
                        Log.e("TAG", "onResult:result.size() =  "+ result.size() );
                        if(null!= result && result.size() != 0){
                            datas.clear();
                            datas.addAll(result);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onErro(String string) {
                            showMsg(string);
                    }
                });
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的骑行") .createAndBind(activityRide);
    }


    public static void openRideActivity(Activity activity){
        activity.startActivity(new Intent(activity,RideActivity.class));
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }
}
