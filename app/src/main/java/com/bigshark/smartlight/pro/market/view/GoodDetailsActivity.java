package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

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
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("http://img3.imgtn.bdimg.com/it/u=393856977,241164954&fm=23&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=1931443362,597038359&fm=21&gp=0.jpg");
        list.add("http://img2.imgtn.bdimg.com/it/u=2168118864,1781870162&fm=23&gp=0.jpg");
        bnGoodImgs.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        bnGoodImgs.setIndicatorGravity(BannerConfig.CENTER);
        bnGoodImgs.setImageLoader(new GlideImageLoader());
        bnGoodImgs.setImages(list);
        bnGoodImgs.start();
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("商品详情").setRightIcon(R.drawable.main_bottom_market_press)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarActivity.openCarActivity(GoodDetailsActivity.this);
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
                Toast.makeText(this,"加入购物车成功",Toast.LENGTH_SHORT);
                break;
            case R.id.bt_buy:
                CarActivity.openCarActivity(GoodDetailsActivity.this);
                break;
        }
    }

    public static void openGoodDetailsActivity(Activity activity){
        activity.startActivity(new Intent(activity,GoodDetailsActivity.class));
    }
}
