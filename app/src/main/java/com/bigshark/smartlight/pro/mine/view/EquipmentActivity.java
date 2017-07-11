package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigshark.smartlight.IndexActivity;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Equipment;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.BluetoothStateRecive;
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
        if (recive == null) {
            recive = new BluetoothStateRecive(new BluetoothStateRecive.BlueetoothStateChangeListener() {
                @Override
                public void onReciveData(int state, String data,byte[] datas) {
                    if (state == 0) {
//                        tvState.setText("已连接");
                    } else if (state == 1) {
//                        tvState.setText("断开连接");
                    } else if (state == 2) {
//                        tvState.setText("通信状态");
                        finish();
                    } else if (state == 3) {

                    }
                }
            });
        }
        registerReceiver(recive, BluetoothStateRecive.makeGattUpdateIntentFilter());

    }

    private void initData() {
        rvEquipment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EquipmentListAdapter(this, datas);
        rvEquipment.setAdapter(adapter);
        adapter.setOnItemClickListener(new EquipmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int postion) {
                IndexActivity.conect(datas.get(postion).getNumbering());
            }
        });
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
                if(isOPen(this)){
                    ScanActivity.openScanActivity(this);
                }else{
                    final AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("尊敬的客户，android4.0以上蓝牙扫描时需要GPS定位。请您在设置中打开GPS，并允许“骑格”使用。")
                            .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent GPSIntent = new Intent();
                                    GPSIntent.setClassName("com.android.settings",
                                            "com.android.settings.widget.SettingsAppWidgetProvider");
                                    GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
                                    GPSIntent.setData(Uri.parse("custom:3"));
                                    try {
                                        PendingIntent.getBroadcast(EquipmentActivity.this, 0, GPSIntent, 0).send();
                                    } catch (PendingIntent.CanceledException e) {
                                        e.printStackTrace();
                                    }
                                    dialogInterface.dismiss();
                                    dialogInterface.cancel();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    dialogInterface.cancel();
                                }
                            })
                            .create();
                    alertDialog.show();
                }

                break;
            case R.id.bt_toMarket:
                MarketActivity.openMarketActivity(this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != recive)
            unregisterReceiver(recive);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BluetoothStateRecive recive;

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps ) {
            return true;
        }
        return false;
    }
}
