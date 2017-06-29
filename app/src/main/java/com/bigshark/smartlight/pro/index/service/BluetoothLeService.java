/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bigshark.smartlight.pro.index.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.BLuetoothData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.bluetooth.le.EXTRA_DATA";

    public final static UUID UUID_HEART_RATE_MEASUREMENT =
            UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
                stopRssi();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                boolean isTrue = register(gatt,service,write);
                if(isTrue){
                    startRssi();
                    broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                }else{
                    connect(mBluetoothDeviceAddress);
                }
                Log.i("Load",isTrue+"");
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            Log.i("Load", Arrays.toString(characteristic.getValue()));
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            if(Math.abs(rssi)>=100 && SmartLightsApplication.isAutoClose){
                sendValue(BLuetoothData.getCloseAlert());
            }
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));

                Log.e(TAG, "broadcastUpdate: " + stringBuilder.toString());
                Bundle bundle = new Bundle();
                bundle.putByteArray(EXTRA_DATA,data);
                intent.putExtras(bundle);
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, true, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    public void erConnect(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mBluetoothDeviceAddress);
                mBluetoothGatt = device.connectGatt(BluetoothLeService.this, true, mGattCallback);
            }
        },50);
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
        stopRssi();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        stopRssi();
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothDeviceAddress = null;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void localBluetoothClose(){
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }


    private static ArrayList<BluetoothGattCharacteristic> arrayNtfCharList = new ArrayList();//可通知列比饿哦
    private static BluetoothGattCharacteristic writeCharacteristic;
    private static String service = "0000e0ff-3c17-d293-8e48-14fe2e4da212";//骑格服务
    //00001800-0000-1000-8000-00805f9b34fb
    //0000e0ff-3c17-d293-8e48-14fe2e4da212
    //0000a0ff-3c17-d293-8e48-14fe2e4da212
    //0000180a-0000-1000-8000-00805f9b34fb
    private static String write = "0000e0ff-3c17-d293-8e48-14fe2e4da212";//0000FFA2-3C17-D293-8E48-14FE2EA2A212
    private static BluetoothGattCharacteristic notifyCharacteristic;//通知的特征码
    private static boolean NotifyEnabled = false;

    public static boolean register(BluetoothGatt bluetoothGatt, String serviceID, String writeCharUUID) {
        rest();
        if(serviceID != null) {
            service = serviceID;
        }

        if(writeCharUUID != null) {
            write = writeCharUUID;
        }

        if(bluetoothGatt != null && !serviceID.isEmpty() && !writeCharUUID.isEmpty()) {
            BluetoothGattService service = bluetoothGatt.getServices().get(1);
            if(service == null) {
                Log.e(TAG, "Sevice未找到");
                return false;
            } else {
                //设置接受的特征
                BluetoothGattCharacteristic chara = (BluetoothGattCharacteristic)service.getCharacteristics().get(0);
                arrayNtfCharList.add(chara);

                //设置写的特征
                BluetoothGattCharacteristic write = bluetoothGatt.getServices().get(2).getCharacteristics().get(1);
                writeCharacteristic = write;

//                List gattCharacteristics = qppService.getCharacteristics();
//
//                for(int j = 0; j < gattCharacteristics.size(); ++j) {
//                    BluetoothGattCharacteristic chara = (BluetoothGattCharacteristic)gattCharacteristics.get(j);
//                    if(chara.getUuid().toString().equals(writeCharUUID)) {
//                        writeCharacteristic = chara;
//                    } else if(chara.getProperties() == 18) {
//                        notifyCharacteristic = chara;
//                        arrayNtfCharList.add(chara);
//                    }
//                }

                if(!setCharacteristicNotification(bluetoothGatt, (BluetoothGattCharacteristic)arrayNtfCharList.get(0), true)) {
                    return false;
                } else {
                    ++notifyCharaIndex;
                    return true;
                }
            }
        } else {
            Log.e(TAG, "缺少参数");
            return false;
        }
    }

    private static int notifyCharaIndex = 0;
    public static boolean setQppNextNotify(BluetoothGatt bluetoothGatt, boolean EnableNotifyChara) {
        if(notifyCharaIndex == arrayNtfCharList.size()) {
            NotifyEnabled = true;
            return true;
        } else {
            return setCharacteristicNotification(bluetoothGatt, (BluetoothGattCharacteristic)arrayNtfCharList.get(notifyCharaIndex++), EnableNotifyChara);
        }
    }


    /**
     * 重置
     */
    private static void rest() {
        writeCharacteristic = null;
        notifyCharacteristic = null;
        arrayNtfCharList.clear();
        notifyCharaIndex = 0;
    }

    private static boolean setCharacteristicNotification(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic characteristic, boolean enabled) {
        if(bluetoothGatt == null) {
            Log.w(TAG, "未初始化Gatt");
            return false;
        } else {
            bluetoothGatt.setCharacteristicNotification(characteristic, enabled);

            try {
                BluetoothGattDescriptor e = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                if(e != null) {
                    e.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    return bluetoothGatt.writeDescriptor(e);
                }

                Log.e(TAG, "descriptor is null");
                return false;
            } catch (NullPointerException var4) {
                var4.printStackTrace();
            } catch (IllegalArgumentException var5) {
                var5.printStackTrace();
            }

            return true;
        }
    }

    public  boolean sendValue(byte[] datas) {
        boolean ret = false;
        if(mBluetoothGatt == null) {
            Log.e(TAG, "BluetoothAdapter not initialized !");
            return false;
        } else if(datas == null) {
            Log.e(TAG, "datas = null !");
            return false;
        } else {
            return writeValue(mBluetoothGatt, writeCharacteristic, datas);
        }
    }

    public String isConnect(){
        return mBluetoothDeviceAddress;
    }

    private static boolean writeValue(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for(byte byteChar : bytes)
            stringBuffer.append(String.format("%02X ", byteChar));
        Log.i("Load",stringBuffer.toString());
        if(gatt == null) {
            Log.e(TAG, "BluetoothAdapter not initialized");
            return false;
        } else {
            characteristic.setValue(bytes);
            return gatt.writeCharacteristic(characteristic);
        }
    }

    ScheduledExecutorService mSchduledExecutorService;
    /**
     * 开始
     */
    public void startRssi(){
       Runnable runnable = new Runnable() {
           public void run() {
              if(mBluetoothGatt!=null){
                  mBluetoothGatt.readRemoteRssi();
              }
           }
       };
        mSchduledExecutorService = Executors
               .newSingleThreadScheduledExecutor();
       // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        mSchduledExecutorService.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);
   }

   public void stopRssi(){
       if(mSchduledExecutorService!=null)
       mSchduledExecutorService.shutdown();
   }

}
