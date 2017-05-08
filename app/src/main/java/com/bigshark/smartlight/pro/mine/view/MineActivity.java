package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends BaseActivity {
    @BindView(R.id.ll_context)
    LinearLayout llContext;
    @BindView(R.id.iv_hander)
    ImageView ivHander;
    @BindView(R.id.iv_userName)
    TextView ivUserName;
    @BindView(R.id.stv_myStroke)
    SuperTextView stvMyStroke;
    @BindView(R.id.stv_myorder)
    SuperTextView stvMyorder;
    @BindView(R.id.stv_myEquipment)
    SuperTextView stvMyEquipment;
    @BindView(R.id.stv_market)
    SuperTextView stvMarket;
    @BindView(R.id.stv_set)
    SuperTextView stvSet;

    private MinePresenter minePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContext);
        ivUserName.setText(SmartLightsApplication.USER.getName());
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的个人中心").createAndBind(llContext);
    }
    @OnClick({R.id.stv_myStroke, R.id.stv_myorder, R.id.stv_myEquipment,R.id.stv_market, R.id.stv_set,R.id.iv_hander,R.id.iv_userName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_myStroke:
                RideActivity.openRideActivity(this);
                break;
            case R.id.stv_myorder:
                OrderActivity.openOrderActivity(this);
                break;
            case R.id.stv_myEquipment:
                EquipmentActivity.openEquipmentActivity(this);
                break;
            case R.id.stv_market:
                MarketActivity.openMarketActivity(this);
                break;
            case R.id.stv_set:
                SetActivity.openSetActivity(this);
                break;
            case R.id.iv_hander:
                setPhotoConfig();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("更换头像")
                        .setMessage("请选择相册或拍照")
                        .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //选择相册
                                PictureConfig.getPictureConfig().openPhoto(MineActivity.this, resultCallback);
                            }
                        })
                        .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //拍照
                                PictureConfig.getPictureConfig().startOpenCamera(MineActivity.this, resultCallback);
                            }
                        });
                alertDialog.show();
                break;
            case R.id.iv_userName:
                MineDetailsActivity.openMineDetailsActivity(this);
                break;
        }
    }
    public static void openMineActivity(Activity activity){
        activity.startActivity(new Intent(activity,MineActivity.class));
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            if(null != resultList && resultList.size() != 0){
                LocalMedia photo =  resultList.get(0);
            }
        }
    };


    /**
     * 设置基本参数
     */
    private  void setPhotoConfig(){
        FunctionConfig config = new FunctionConfig();
        config.setType(LocalMediaLoader.TYPE_IMAGE);
        config.setSelectMode(2);
        config.setCompress(false);
        config.setEnablePixelCompress(true);
        config.setEnableQualityCompress(true);
        config.setMaxSelectNum(1);
        config.setShowCamera(true);
        config.setEnablePreview(true);
        config.setEnableCrop(true);
        config.setImageSpanCount(4);
        config.setCropH(400);
        config.setCropW(400);
        // 先初始化参数配置，在启动相册
        PictureConfig.init(config);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivUserName.setText(SmartLightsApplication.USER.getName());
    }
}
