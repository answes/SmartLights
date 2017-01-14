package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.CarListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarActivity extends BaseActivity {

    @BindView(R.id.rv_carlist)
    RecyclerView rvCarlist;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.bt_settlement)
    Button btSettlement;
    @BindView(R.id.activity_car)
    RelativeLayout activityCar;

    private CarListAdapter adapter;
    private List<CarGoods> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 2 ; i++) {
            CarGoods g= new CarGoods();
            g.setImgUrl("http://img2.imgtn.bdimg.com/it/u=3537229348,2840165468&fm=23&gp=0.jpg");
            g.setName("cocoa真皮包");
            g.setPrice(5000);
            g.setNumber(1);
            datas.add(g);
        }
        rvCarlist.setLayoutManager(new LinearLayoutManager(this));
        rvCarlist.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());
        adapter = new CarListAdapter(this,datas);
        rvCarlist.setAdapter(adapter);
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("购物车").createAndBind(activityCar);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @OnClick(R.id.bt_settlement)
    public void onClick() {
        ConfirmOrederActivity.openConfirmOrederActivity(this);
    }

    public static void openCarActivity(Activity activity){
        activity.startActivity(new Intent(activity,CarActivity.class));
    }
}
