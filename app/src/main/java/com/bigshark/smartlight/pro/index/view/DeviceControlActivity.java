package com.bigshark.smartlight.pro.index.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigshark.smartlight.IndexActivity;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.BLuetoothData;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.BluetoothStateRecive;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceControlActivity extends BaseActivity {
    private final static String DEVICE_NAME = "DEVICE_NAME";
    private final static String DEVICE_ADDRSS = "device_addreess";
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.tv_device_address)
    TextView tvDeviceAddress;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.btn_batery)
    Button btnBatery;
    @BindView(R.id.btn_brake)
    Button btnBrake;
    @BindView(R.id.btn_turn_on)
    Button btnTurnOn;
    @BindView(R.id.btn_turn_left)
    Button btnTurnLeft;
    @BindView(R.id.btn_turn_right)
    Button btnTurnRight;
    @BindView(R.id.btn_find_car)
    Button btnFindCar;
    @BindView(R.id.btn_open_alert)
    Button btnOpenAlert;
    @BindView(R.id.btn_close_alert)
    Button btnCloseAlert;

    private String device_name, device_addrress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_control);
        ButterKnife.bind(this);
        device_name = getIntent().getStringExtra(DEVICE_NAME);
        device_addrress = getIntent().getStringExtra(DEVICE_ADDRSS);
        tvDeviceName.setText(device_name);
        tvDeviceAddress.setText(device_addrress);
        tvState.setText("未连接");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                IndexActivity.conect(device_addrress);
            }
        });
    }

    @OnClick({R.id.btn_batery,R.id.btn_brake,R.id.btn_turn_on,R.id.btn_turn_left
    ,R.id.btn_turn_right,R.id.btn_find_car,R.id.btn_open_alert,R.id.btn_close_alert})
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_batery:
                IndexActivity.sendData(BLuetoothData.getReplayBatery());
                break;
            case R.id.btn_brake:
                IndexActivity.sendData(BLuetoothData.getReplayBrake());
                break;
            case R.id.btn_turn_on:
                IndexActivity.sendData(BLuetoothData.getReplayTurnTo());
                break;
            case R.id.btn_turn_left:
                IndexActivity.sendData(BLuetoothData.getTurnLeft());
                break;
            case R.id.btn_turn_right:
                IndexActivity.sendData(BLuetoothData.getTurnRight());
                break;
            case R.id.btn_find_car:
                IndexActivity.sendData(BLuetoothData.getFindCar());
                break;
            case R.id.btn_open_alert:
                IndexActivity.sendData(BLuetoothData.getOpenAlert());
                break;
            case R.id.btn_close_alert:
                IndexActivity.sendData(BLuetoothData.getCloseAlert());
                break;
        }
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    /**
     * @param address     mac地址
     * @param device_name device_name
     * @param context     上下文内容
     */
    public static void oppenDeviceControlActivity(String address, String device_name, Context context) {
        Intent intent = new Intent(context, DeviceControlActivity.class);
        intent.putExtra(DEVICE_NAME, device_name);
        intent.putExtra(DEVICE_ADDRSS, address);
        context.startActivity(intent);
    }
    private BluetoothStateRecive recive;
    @Override
    protected void onResume() {
        super.onResume();
        if(recive == null){
            recive = new BluetoothStateRecive(new BluetoothStateRecive.BlueetoothStateChangeListener() {
                @Override
                public void onReciveData(int state, String data) {
                    if(state == 0){
                        tvState.setText("已连接");
                    }else if(state == 1){
                        tvState.setText("断开连接");
                    }else if(state == 2){
                        tvState.setText("通信状态");
                    }else if(state == 3){
                        tvData.setText(data);
                    }
                }
            });
        }
        registerReceiver(recive,BluetoothStateRecive.makeGattUpdateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(recive);
    }
}
