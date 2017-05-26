package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.view.adapter.viewhold.BluetoothScanAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanActivity extends BaseActivity {

    @BindView(R.id.bluetooth)
    RecyclerView bluetooth;
    @BindView(R.id.activity_scan)
    LinearLayout activityScan;

    private BluetoothAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        SupportMultipleScreensUtil.scale(activityScan);
        initData();
        initToolBar();
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            showMsg("当前设备不支持蓝牙设备");
        }
        if (adapter.isEnabled()) {
            adapter.startLeScan(callback);
        } else {
            adapter.enable();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.startLeScan(callback);
                }
            }, 1000);
        }
    }

    BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    check(device);
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        if (adapter.isDiscovering()) {
            adapter.stopLeScan(callback);
        }
        super.onDestroy();
    }

    /**
     * 检测是否拥有蓝牙
     */
    private void check(BluetoothDevice device) {
        for (BluetoothDevice d : deivices) {
            if (d.getAddress().equals(device.getAddress())) {
                return;
            }
        }
        deivices.add(device);
        bluetoothScanAdapter.notifyDataSetChanged();
    }

    private void initToolBar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("巡找蓝牙车灯").createAndBind(activityScan);
    }

    private List<BluetoothDevice> deivices;
    private BluetoothScanAdapter bluetoothScanAdapter;

    private void initData() {
        deivices = new ArrayList<>();
        bluetooth.setLayoutManager(new LinearLayoutManager(this));
        bluetooth.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());
        bluetoothScanAdapter = new BluetoothScanAdapter(this, deivices);
        bluetooth.setAdapter(bluetoothScanAdapter);
        bluetoothScanAdapter.setOnItemOnclickListener(new BluetoothScanAdapter.onItemOnclickListner() {
            @Override
            public void onItemClick(int potsion) {
                adapter.stopLeScan(callback);
                DeviceControlActivity.oppenDeviceControlActivity(deivices.get(potsion).getAddress(), deivices.get(potsion).getName(), ScanActivity.this);
            }
        });
    }

    public static void openScanActivity(Activity activity) {
        activity.startActivity(new Intent(activity, ScanActivity.class));
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }
}
