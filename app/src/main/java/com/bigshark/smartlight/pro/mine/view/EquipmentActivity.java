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
import com.bigshark.smartlight.pro.mine.view.adapter.EquipmentListAdapter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EquipmentActivity extends BaseActivity {

    @BindView(R.id.rv_equipment)
    RecyclerView rvEquipment;
    @BindView(R.id.activity_equipment)
    LinearLayout activityEquipment;
    @BindView(R.id.bt_addEqu)
    Button btAddEqu;

    private List<Equipment> datas = new ArrayList<>();
    private EquipmentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }

    private void initData() {
        Equipment e= new Equipment();
        e.setName("设备名称：Amy的设备1");
        e.setNumbering("设备编号：20160708201");
        datas.add(e);
        datas.add(e);
        rvEquipment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EquipmentListAdapter(this,datas);
        rvEquipment.setAdapter(adapter);
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的设备") .createAndBind(activityEquipment);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    public static void openEquipmentActivity(Activity activity) {
        activity.startActivity(new Intent(activity, EquipmentActivity.class));
    }

    @OnClick(R.id.bt_addEqu)
    public void onClick() {
    }
}
