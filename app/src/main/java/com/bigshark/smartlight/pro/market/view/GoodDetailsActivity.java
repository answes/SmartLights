package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodDetailsActivity extends BaseActivity {

    @BindView(R.id.bn_goodImgs)
    Banner bnGoodImgs;
    @BindView(R.id.stv_goodTitle)
    SuperTextView stvGoodTitle;
    @BindView(R.id.tv_moeny)
    TextView tvMoeny;
    @BindView(R.id.stv_googDeatil)
    SuperTextView stvGoogDeatil;
    @BindView(R.id.bt_addCar)
    Button btAddCar;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.activity_good_details)
    RelativeLayout activityGoodDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
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
                }).setTitle("商品详情").setRightIcon(R.drawable.fragment_market_toolbar_in)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).createAndBind(activityGoodDetails);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @OnClick({R.id.bt_addCar, R.id.bt_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_addCar:
                break;
            case R.id.bt_buy:
                break;
        }
    }

    public static void openGoodDetailsActivity(Activity activity){
        activity.startActivity(new Intent(activity,GoodDetailsActivity.class));
    }
}
