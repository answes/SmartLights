package com.bigshark.smartlight.pro.index.broadcast;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.bigshark.smartlight.pro.index.service.BluetoothLeService;

/**
 * Created by ch on 2017/5/24.
 *
 * @email 869360026@qq.com
 */

public class BluetoothStateRecive extends BroadcastReceiver {

    public BluetoothStateRecive(@NonNull BlueetoothStateChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
            //Gatt连接
            listener.onReciveData(0,"已连接",null);
        } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
            //gatt失去连接
            listener.onReciveData(1,"断开连接",null);
        } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
            // Show all the supported services and characteristics on the user interface.
            //获得Sevices 及可通信状态 raedy状态
            listener.onReciveData(2,"可通信",null);
        } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
            //获得数据
            listener.onReciveData(3,null,intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
        }
        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
            int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
            switch(blueState) {
                case BluetoothAdapter.STATE_TURNING_ON:

                    break;
                case BluetoothAdapter.STATE_ON:
                    listener.onReciveData(4,"蓝牙已经打开",null);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    listener.onReciveData(6,"正在关闭蓝牙",null);
                    break;
                case BluetoothAdapter.STATE_OFF:
                    listener.onReciveData(5,"蓝牙已经关闭",null);
                    break;
            }
        }
    }


    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return intentFilter;
    }
    private BlueetoothStateChangeListener listener;

    public interface BlueetoothStateChangeListener {
        /**
         * @param state 0已经连接上 1失去连接 2 可通信状态  3 获得数据 4打开蓝牙 6 正在关闭蓝牙
         * @param data  数据
         */
        void onReciveData(int state, String data,byte[] realData);
    }
}
