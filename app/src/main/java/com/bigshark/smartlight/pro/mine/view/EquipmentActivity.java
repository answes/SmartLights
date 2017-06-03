package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Equipment;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.view.ScanActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.EquipmentListAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的设备为本地数据库提供的数据
 */
public class EquipmentActivity extends BaseActivity {

    @BindView(R.id.rv_equipment)
    RecyclerView rvEquipment;
    @BindView(R.id.activity_equipment)
    LinearLayout activityEquipment;
    @BindView(R.id.bt_addEqu)
    Button btAddEqu;
    @BindView(R.id.bt_toMarket)
    Button btToMarket;

    private List<Equipment> datas = new ArrayList<>();
    private EquipmentListAdapter adapter;
    private MinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityEquipment);
        initData();
    }

    private void initData() {
        rvEquipment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EquipmentListAdapter(this, datas);
        rvEquipment.setAdapter(adapter);

        List<Equipment> sqlDatas = SQLUtils.getEqus(this);
        if(null != sqlDatas && sqlDatas.size() != 0){
            datas.addAll(sqlDatas);
            adapter.notifyDataSetChanged();
        }
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的设备").createAndBind(activityEquipment);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MinePresenter(this);
        return presenter;
    }

    public static void openEquipmentActivity(Activity activity) {
        activity.startActivity(new Intent(activity, EquipmentActivity.class));
    }

    @OnClick({R.id.bt_addEqu, R.id.bt_toMarket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_addEqu:
                ScanActivity.openScanActivity(this);
                break;
            case R.id.bt_toMarket:
                MarketActivity.openMarketActivity(this);
                break;
        }
    }
}
