package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.ConfiremGoodsListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrederActivity extends BaseActivity {

    @BindView(R.id.stv_name)
    SuperTextView stvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.iv_in)
    ImageView ivIn;
    @BindView(R.id.stv_title)
    SuperTextView stvTitle;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.activity_confirm_oreder)
    RelativeLayout activityConfirmOreder;

    private ConfiremGoodsListAdapter adapter;
    private List<CarGoods> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_oreder);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    private void initData() {
        CarGoods g = new CarGoods();
        g.setImgUrl("http://img2.imgtn.bdimg.com/it/u=3537229348,2840165468&fm=23&gp=0.jpg");
        g.setName("cocoa真皮包");
        g.setPrice(5000);
        g.setNumber(1);
        datas.add(g);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.tongming).build());
        adapter = new ConfiremGoodsListAdapter(this,datas);
        rvGoods.setAdapter(adapter);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    public static void openConfirmOrederActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ConfirmOrederActivity.class));
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("确认订单").createAndBind(activityConfirmOreder);
    }

    @OnClick({R.id.ll_left, R.id.iv_in, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                break;
            case R.id.iv_in:
                break;
            case R.id.bt_confirm:
                break;
        }
    }
}
