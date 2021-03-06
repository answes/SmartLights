package com.bigshark.smartlight.pro.mine.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bigShark on 2016/12/28.
 */

public class MineFragment extends BaseFragment {
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

    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
        initToolbar(viewContent);
    }

    private void initToolbar(View viewContent) {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(getActivity());
        toolbar.setRightIcon(R.drawable.fragment_mine_messge)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MessgeActivity.openMessgeActivity(getActivity());
                    }
                })
                .createAndBind((ViewGroup) viewContent);
    }

    @OnClick({R.id.stv_myStroke, R.id.stv_myorder, R.id.stv_myEquipment,R.id.stv_market, R.id.stv_set,R.id.iv_hander})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_myStroke:
                RideActivity.openRideActivity(getActivity());
                break;
            case R.id.stv_myorder:
                break;
            case R.id.stv_myEquipment:
                EquipmentActivity.openEquipmentActivity(getActivity());
                break;
            case R.id.stv_market:
                MarketActivity.openMarketActivity(getActivity());
                break;
            case R.id.stv_set:
                SetActivity.openSetActivity(getActivity());
                break;
            case R.id.iv_hander:
                MineDetailsActivity.openMineDetailsActivity(getActivity());
                break;
        }
    }

    @Override
    public MVPBasePresenter bindPresenter() {
        return null;
    }
}
