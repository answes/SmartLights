package com.bigshark.smartlight.pro.market.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;

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
    LinearLayout activityCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        initToolbar();
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
    }
}
