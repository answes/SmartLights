package com.bigshark.smartlight.pro.index.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigshark.smartlight.IndexActivity;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.BluetoothStateRecive;
import com.bigshark.smartlight.pro.index.service.BluetoothLeService;
import com.bigshark.smartlight.pro.index.view.adapter.viewhold.BluetoothScanAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.yalantis.ucrop.dialog.SweetAlertDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends BaseActivity {
    public static final int SACN_RESULT_CODE = 0x110;

    @BindView(R.id.bluetooth)
    RecyclerView bluetooth;
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.activity_scan)
    LinearLayout activityScan;

    private BluetoothAdapter adapter;
    private boolean stop;
    private SweetAlertDialog dialog;

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
        dialog = new SweetAlertDialog(this);
        dialog.setTitleText("正在搜索...");
        dialog.show();
    }

    BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
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
                }).setTitle("寻找蓝牙车灯").createAndBind(activityScan);
    }

    private List<BluetoothDevice> deivices;
    private BluetoothScanAdapter bluetoothScanAdapter;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.putExtra("link", true);
            ScanActivity.this.setResult(RESULT_OK, intent);
            ScanActivity.this.finish();
        }
    };

    private void initData() {
        deivices = new ArrayList<>();
        bluetooth.setLayoutManager(new LinearLayoutManager(this));
        bluetooth.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());
        bluetoothScanAdapter = new BluetoothScanAdapter(this, deivices);
        bluetooth.setAdapter(bluetoothScanAdapter);
        bluetoothScanAdapter.setOnItemOnclickListener(new BluetoothScanAdapter.onItemOnclickListner() {
            @Override
            public void onItemClick(final int potsion) {
                adapter.stopLeScan(callback);
                BluetoothLeService.isCanOpenDevice = false;
                showMsg("正在连接蓝牙车灯，请等待。");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        IndexActivity.conect(deivices.get(potsion).getAddress());
                    }
                });
                SQLUtils.saveEqu(ScanActivity.this, deivices.get(potsion).getName(), deivices.get(potsion).getAddress());
//                DeviceControlActivity.oppenDeviceControlActivity(deivices.get(potsion).getAddress(), deivices.get(potsion).getName(), ScanActivity.this);
            }
        });


    }

    public static void openScanActivity(Activity activity) {
        Intent intent = new Intent(activity, ScanActivity.class);
        activity.startActivityForResult(intent, SACN_RESULT_CODE);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    private BluetoothStateRecive recive;

    @Override
    protected void onResume() {
        super.onResume();
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mHandler.sendEmptyMessage(1);
                            }
                        }).start();
                        unregisterReceiver(recive);
                        recive = null;
                    } else if (state == 3) {

                    }
                }
            });
        }
        registerReceiver(recive, BluetoothStateRecive.makeGattUpdateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != recive)
            unregisterReceiver(recive);
    }

    @OnClick(R.id.bt_scan)
    public void onViewClicked() {
        if(stop){
//            if (adapter == null) {
//                showMsg("当前设备不支持蓝牙设备");
//            }
//            if (adapter.isEnabled()) {
//                adapter.startLeScan(callback);
//            } else {
//                adapter.enable();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.startLeScan(callback);
//                    }
//                }, 1000);
//            }
            btScan.setText("暂停");
            stop = true;
        }

    }
}
