package com.bigshark.smartlight;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigshark.smartlight.bean.BLuetoothData;
import com.bigshark.smartlight.bean.Equipment;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.BluetoothStateRecive;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.index.presenter.MapPreseter;
import com.bigshark.smartlight.pro.index.service.BluetoothLeService;
import com.bigshark.smartlight.pro.index.view.EndConfirmActivity;
import com.bigshark.smartlight.pro.index.view.MapActivity;
import com.bigshark.smartlight.pro.index.view.NagivaActivity;
import com.bigshark.smartlight.pro.index.view.ScanActivity;
import com.bigshark.smartlight.pro.index.view.navigation.IndexNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.EquipmentActivity;
import com.bigshark.smartlight.pro.mine.view.MessgeActivity;
import com.bigshark.smartlight.pro.mine.view.MineActivity;
import com.bigshark.smartlight.utils.Contact;
import com.bigshark.smartlight.utils.GPSUtil;
import com.bigshark.smartlight.utils.MediaPlayerUtils;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.TimeUtil;
import com.bigshark.smartlight.utils.ToastUtil;
import com.bigshark.smartlight.weight.CustomArcView;
import com.bigshark.smartlight.weight.TakePhotoPopWin;

import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 最新的首页
 */
public class IndexActivity extends BaseActivity {
    @BindView(R.id.arc_view)
    CustomArcView arcView;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.tv_ele)
    ImageView tvEle;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.frame_biz)
    FrameLayout frameBiz;
    @BindView(R.id.frame_location)
    FrameLayout frameLocation;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_maxspeed)
    TextView tvMaxspeed;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tv_avg_speed)
    TextView tvAvgSpeed;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.bt_stop)
    Button btStop;
    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.index_bottom)
    LinearLayout indexBottom;
    @BindView(R.id.btn_find)
    TextView btnFind;
    @BindView(R.id.ll_context)
    LinearLayout llContext;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    private boolean isLinkBlue;
    private MediaPlayerUtils mediaPlayerUtils;

    private static Context mContext;

    private boolean isStopCount = false;
    private boolean isPause = true;
    private Handler mHandler = new Handler();
    private long timer = 0;
    private String timeStr = "";
    private List<Equipment> blueDatas = new ArrayList<>();


    private Runnable TimerRunnable = new Runnable() {

        @Override
        public void run() {
            if (!isStopCount) {
                timer += 1000;
                timeStr = TimeUtil.getFormatTime(timer);
                tvHour.setText(timeStr);
            }
            countTimer();
        }
    };

    private void countTimer() {
        mHandler.postDelayed(TimerRunnable, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContext);
        SmartLightsApplication.isAutoClose = SQLUtils.getAutoConfig(this);
        //蓝牙连接服务
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        mediaPlayerUtils = new MediaPlayerUtils(this, arcView);
        if (null != SQLUtils.getEqus(this)) {
            blueDatas.addAll(SQLUtils.getEqus(this));
        }
        mContext = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101
                    );
        }
    }

    private MapPreseter mapPreseter;

    @Override
    public MVPBasePresenter bindPresneter() {
        mapPreseter = new MapPreseter(this);
        return mapPreseter;
    }

    private void initToolbar() {
        IndexNavigationBuilder toolbar = new IndexNavigationBuilder(this);
        toolbar.setTitle(R.string.main_index_text)
                .setLeftIcon(R.drawable.index_mine).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineActivity.openMineActivity(IndexActivity.this);
            }
        }).setRightIcon(R.drawable.fragment_mine_messge).setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessgeActivity.openMessgeActivity(IndexActivity.this);
            }
        }).createAndBind(llContext);
    }

    public static void openIndexActivity(Activity a) {
        a.startActivity(new Intent(a, IndexActivity.class));
    }

    private String mac;
    private boolean isStart = false;
    private WindowManager.LayoutParams params;
    private TakePhotoPopWin takePhotoPopWin;

    int similart = 1;
    /**
     * 查找用户新车
     *
     * @param view
     */
    @OnClick({R.id.btn_find, R.id.frame_location, R.id.bt_stop, R.id.bt_finish, R.id.frame_biz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_find:
                if (!GPSUtil.isOPen(this)) {
                    GPSUtil.openGPS(this);
                }
                isStart = true;
                countTimer();
                startRide();
//                sendPackge(similart);
//                similart = similart+1;
                break;
            case R.id.frame_location:
                if (isStart) {
                    MapActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), true, mapPreseter.getSavesLatlng());
                } else {
                    NagivaActivity.openNagivaActivity(this);
                }
                break;
            case R.id.bt_finish:
                isStart = false;
                mapPreseter.stop();
                isPause = false;
                isStopCount = true;
                EndConfirmActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), mapPreseter.getSavesLatlng(), mapPreseter);
                break;
            case R.id.bt_stop:
                if (btStop.getText().toString().equals("暂停骑行")) {
                    isStart = false;
                    btStop.setText("恢复骑行");
                    mapPreseter.stop();
                    isPause = false;
                    isStopCount = true;
                } else {
                    btStop.setText("暂停骑行");
                    isStart = true;
                    mapPreseter.restart();
                    isPause = true;
                    isStopCount = false;
                }
                break;
            case R.id.frame_biz:
                if (isLinkBlue) {
                    takePhotoPopWin = new TakePhotoPopWin(this, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            switch (view.getId()) {
                                case R.id.ll_open:
                                    IndexActivity.sendData(BLuetoothData.getOpenAlert());
                                    takePhotoPopWin.dismiss();
                                    break;
                                case R.id.ll_close:
                                    IndexActivity.sendData(BLuetoothData.getCloseAlert());
                                    takePhotoPopWin.dismiss();
                                    break;
                                case R.id.ll_find:
                                    IndexActivity.sendData(BLuetoothData.getFindCar());
                                    takePhotoPopWin.dismiss();
                                    break;

                            }
                        }
                    });
                    takePhotoPopWin.showAtLocation(llContext, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    params = getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getWindow().setAttributes(params);
                    //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
                    takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            params = getWindow().getAttributes();
                            params.alpha = 1f;
                            getWindow().setAttributes(params);
                        }
                    });
                } else {
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    if (adapter == null) {
                        showMsg("当前设备不支持蓝牙");
                        return;
                    }
                    if (adapter.isEnabled()) {
                        //是否打开
                        openEquipmentOrConnect();
                    } else {
                        showMsg("正在打开蓝牙，请稍后");
                        adapter.enable();
                    }

                }
                break;
        }
    }


    private void initBluetoothAdapter(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            showMsg("当前设备不支持蓝牙");
            return;
        }
        if (adapter.isEnabled()) {
            //是否打开
            openEquipmentOrConnect();
        } else {
           showMsg("蓝牙未打开");
        }
    }

    private void startRide() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x01);
        } else {
            isStart = true;
            mapPreseter.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ((SmartLightsApplication) IndexActivity.this.getApplication()).initJson();
                }
            }).start();
            btnFind.setVisibility(View.GONE);
            indexBottom.setVisibility(View.VISIBLE);
        }
    }

    protected void onResume() {
        super.onResume();
        registerBroadCasst();
    }

    private MapLocationRecive mapLocationRecive;
    private BluetoothStateRecive bluetoothStateRecive;
    private AlertDialog alertDialog = null;


    /**
     * 蓝牙开启时
     * 确定是否连接或者开启界面
     */
    private void openEquipmentOrConnect() {
        if (mBluetoothLeService != null && mBluetoothLeService.isConnect() != null) {
            mBluetoothLeService.erConnect();
            return;
        }
        List<Equipment> data = SQLUtils.getEqus(IndexActivity.this);
        if (data == null || data.size() == 0) {
            showMsg("您没有链接过的设备");
            EquipmentActivity.openEquipmentActivity(this);
            return;
        }
        Equipment equipment = data.get(data.size() - 1);
        //正在为您连
        showMsg(String.format("正在为您链接%s,%s", equipment.getName(), equipment.getNumbering()));
        IndexActivity.conect(equipment.getNumbering());
    }

    private void registerBroadCasst() {
        if (mapLocationRecive == null) {
            mapLocationRecive = new MapLocationRecive(new MapLocationRecive.OnLocationReciveListener() {
                @Override
                public void onRevice(final UpLoadRecord record) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHigh.setText(String.valueOf(record.getHeight()) + "m");
                            tvMaxspeed.setText(String.format("%.2f", record.getMaxSpeed()) + "km/h");
                            tvHot.setText(String.format("%.2f", record.getK()) + "Cal");
                            tvSpeed.setText(String.format("%.2f", record.getSpeed()));
                            tvDistance.setText(String.format("%.2f", (record.getDistance()) / 1000) + "km");
                            tvAvgSpeed.setText(String.format("%.2fkm/h", (record.getAvSpeed())));
//                            tvHour.setText(new StringBuffer()
//                                            .append(String.format("%02d", record.getTime() / (60 * 60 * 1000)))
//                                            .append(":")
//                                            .append(String.format("%02d", record.getTime() % (60 * 60 * 1000) / (60 * 1000)))
//                                            .append(":")
//                                            .append((String.format("%02d", record.getTime() % (60 * 60 * 1000) % (60 * 1000) / (1000))))
//                                            .toString());

                        }
                    });
                }
            });
        }

        if (null == bluetoothStateRecive) {
            bluetoothStateRecive = new BluetoothStateRecive(new BluetoothStateRecive.BlueetoothStateChangeListener() {
                @Override
                public void onReciveData(final int state, final String data, final byte[] realData) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (state == 0) {
                                isLinkBlue = true;
                                showMsg("蓝牙链接成功");
                            }
                            if (state == 4) {
                                //蓝牙打开
//                                if(mBluetoothLeService!=null && mBluetoothLeService.isConnect() !=null) {
//                                   mBluetoothLeService.erConnect();
//                                }else{
//                                    ScanActivity.openScanActivity(IndexActivity.this);
//                                }
                                openEquipmentOrConnect();
                            }

                            if (state == 6) {
                                showMsg("已经关闭蓝牙");
                                //已经关闭蓝牙
                                tvEle.setVisibility(View.GONE);
                                isLinkBlue = false;
                                arcView.setDataType(CustomArcView.DataType.NONE);
                                mBluetoothLeService.localBluetoothClose();
                            }

                            if (3 == state) {
                                if (3 == realData[4]) {
                                    //0x16禁止
//                                    int count = (realData[7] * 256 + realData[8])/700;
                                    try {
                                        tvEle.setVisibility(View.VISIBLE);
                                        String elcNumber = new StringBuffer().append(String.format("%02X", realData[7])).append(String.format("%02X", realData[8])).toString();
                                        int count = (Integer.valueOf(elcNumber, 16) - 3500) * 100 / 700;
                                        if (count < 10) {
                                            tvEle.setImageResource(R.drawable.empty_battery);
                                        } else if (count < 20) {
                                            tvEle.setImageResource(R.drawable.ele_low);
                                        } else if (count < 40) {
                                            tvEle.setImageResource(R.drawable.ele_low2);
                                        } else if (count < 60) {
                                            tvEle.setImageResource(R.drawable.ele_medum);
                                        } else if (count < 80) {
                                            tvEle.setImageResource(R.drawable.ele_high1);
                                        } else {
                                            tvEle.setImageResource(R.drawable.ele_high2);
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                                //转向
                                if (5 == realData[4]) {
                                    int tuen = realData[7];
                                    if (1 == tuen) {
                                        mediaPlayerUtils.palyLeftMedia();
                                        arcView.setDataType(CustomArcView.DataType.LEFT);
                                    } else if (2 == tuen) {
                                        mediaPlayerUtils.palyRightMedia();
                                        arcView.setDataType(CustomArcView.DataType.RIGHT);
                                    } else if (3 == tuen) {
                                        mediaPlayerUtils.playEnd();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                arcView.setDataType(CustomArcView.DataType.NONE);
                                            }
                                        }, 200);
                                    } else if (4 == tuen) {
                                        mediaPlayerUtils.setPlayTime();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                arcView.setDataType(CustomArcView.DataType.NONE);
                                            }
                                        });
                                    }

                                }
                                //55 55 55 55 01 01 00 01 55 55 55 55
                                if (1 == realData[4]) {
                                    if (realData[7] == 1) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                arcView.setDataType(CustomArcView.DataType.SHACHE);
                                            }
                                        });
                                        mediaPlayerUtils.palyShacheMedia();
                                    } else {
                                        arcView.setDataType(CustomArcView.DataType.NONE);
                                    }
                                }
                                if (13 == realData[4]) {
                                    //警报
                                    if (alertDialog == null) {
                                        alertDialog = new AlertDialog.Builder(IndexActivity.this)
                                                .setTitle("警报")
                                                .setMessage("收到警报信号")
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        alertDialog = null;
                                                        dialog.cancel();
                                                    }
                                                }).setPositiveButton("忽略", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                        sendData(BLuetoothData.getOpenAlert());
                                                    }
                                                }).create();
                                        alertDialog.show();
                                    }
                                }


                                //固件版本
                                //55 55 55 55 0x16 0x01 00 01 55 55 55 55
                                if(realData[4] == 0x16){
                                    try {
                                        if (Contact.fireWave.getVersionCode() <= realData[7]) {
                                            sendData(BLuetoothData.getFirmwareUp(Contact.fireWave));
                                        } else {
                                            showMsg("已经是最新版本了");
                                        }
                                    }catch (Exception e){
                                        showMsg("已经是最新版本了");
                                    }
                                }
                                //55 55 55 55 11 01 00 01
                                if(realData[4] == 0x11) {
                                    if(0x01 == realData[7]){
                                        //sendPackge(0);
                                        showMsg("准备升级");
                                    }else{
                                        if(onDisdialogMissListener!=null){
                                            onDisdialogMissListener.dissmiss();
                                        }
                                        showMsg("取消升级");
                                    }
                                }
                                if(realData[4]==0x14){
                                    showMsg("升级失败，重新升级");
                                    sendData(BLuetoothData.getFirmwareUp(Contact.fireWave));
                                }

                                if(realData[4] == 0x17){
                                    if(onDisdialogMissListener!=null){
                                        onDisdialogMissListener.dissmiss();
                                    }
                                    showMsg("固件升级成功");
                                }
                                //升级过程中的
                                //55 55 55 55 0x12 0x02 0x00 0x01 0x00
                                if(realData[4] == 0x12){
                                    if(realData[8] == 0x00){
                                        //发送升级包
                                        sendPackge(realData[7]);
                                    }else{
                                        sendData(BLuetoothData.getReplyState());
//                                        if(0x01 != realData[8]){
//                                            sendPackge(realData[7]);
//                                        }

                                    }
                                }

                            } else if (1 == state) {    //失去通信，断开连接
                                if(onDisdialogMissListener!=null){
                                    onDisdialogMissListener.dissmiss();
                                }
                                tvEle.setVisibility(View.GONE);
                                arcView.setDataType(CustomArcView.DataType.NONE);
                                showMsg("蓝牙已经失去连接");
                                isLinkBlue = false;
                            }
                        }
                    });
                }
            });
        }

        registerReceiver(bluetoothStateRecive, BluetoothStateRecive.makeGattUpdateIntentFilter());

        IntentFilter mapFilter = new IntentFilter();
        mapFilter.addAction(MapLocationRecive.ACTION);
        registerReceiver(mapLocationRecive, mapFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapPreseter.finish(null);
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        mediaPlayerUtils.release();
        mHandler.removeCallbacks(TimerRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterBroadCast();
    }

    private void unRegisterBroadCast() {
        if (mapLocationRecive != null) {
            unregisterReceiver(mapLocationRecive);
        }
        if (bluetoothStateRecive != null) {
            unregisterReceiver(bluetoothStateRecive);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            //请求的定位权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapPreseter.start();
                isStart = false;
                btnFind.setText("结束骑行");
            } else {
                showMsg("权限被拒绝，请在软件设置中打开权限");
            }
        }
        if(requestCode == 101){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showMsg("权限被拒绝，允许写入日志");
            } else {
                showMsg("权限被拒绝，请在文件写入权限");
            }
        }
    }

    public static void write(String string,String fileName){
        File file = new File(Environment.getExternalStorageDirectory(),fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(string);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EndConfirmActivity.REQUEST_END_CONFIRM && RESULT_OK == resultCode) {
            btnFind.setVisibility(View.VISIBLE);
            indexBottom.setVisibility(View.GONE);
            tvSpeed.setText("0.00km/h");
            tvHot.setText("0Cal");
            tvDistance.setText("0km");
            tvHigh.setText("0.0m");
            tvMaxspeed.setText("0.00km/h");
            tvHour.setText("00:00:00");
        } else if (requestCode == EndConfirmActivity.REQUEST_END_CONFIRM && RESULT_CANCELED== resultCode) {
            mapPreseter.restart();
        } else if (requestCode == ScanActivity.SACN_RESULT_CODE && RESULT_OK == resultCode) {
            isLinkBlue = true;
            //startRide();
        }
    }

    private static BluetoothLeService mBluetoothLeService;//蓝牙连接服务
    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e("Load", "Unable to initialize Bluetooth");
                ToastUtil.showToast(IndexActivity.this, "当前设备不支持蓝牙", 1000);
            }else{
                initBluetoothAdapter();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService.close();
            mBluetoothLeService = null;
        }
    };

    //连接
    public static void conect(String addrss) {
        try {
            if (!mBluetoothLeService.connect(addrss)) {
                ToastUtil.showToast(mContext, "链接失败，请确保蓝牙车灯打开");
                EquipmentActivity.openEquipmentActivity((Activity) mContext);
            }
        }catch (Exception e){
            ToastUtil.showToast(mContext, "链接失败，请确保蓝牙车灯打开");
            EquipmentActivity.openEquipmentActivity((Activity) mContext);
        }
    }

    public static void sendData(byte[] data) {
        if(!mBluetoothLeService.sendValue(data)){
            Toast.makeText(mContext,"您没有连接蓝牙",Toast.LENGTH_SHORT).show();
            if(onDisdialogMissListener!=null){
                onDisdialogMissListener.dissmiss();
            }
        }
    }
    private void sendPackge(int pacge){
        byte[] packgeBytes = Contact.fireWave.getBytes().get(pacge-1);
        byte[] bizBytes = new byte[packgeBytes.length+4];//指令1 内容2 包名1 2048+2+1+1 = 2052+8 = 2060
        System.arraycopy(packgeBytes,0,bizBytes,4,packgeBytes.length);
        //生成数组信息
        bizBytes[0] = 0x13;
        String hexString = String.format("%04x", packgeBytes.length+1);
        bizBytes[1] = (byte) Integer.parseInt(hexString.substring(2,4),16);
        bizBytes[2] = (byte) Integer.parseInt(hexString.substring(0,2),16);
        bizBytes[3] = (byte) pacge;
        byte[] returnBytes =  BLuetoothData.getData(bizBytes);
        try {
            mBluetoothLeService.startSendPackge(returnBytes);
        }catch (Exception e){
            showMsg("请链接上蓝牙后重试");
        }
    }


    public static  OnDisdialogMissListener onDisdialogMissListener;
    public  interface OnDisdialogMissListener{
        void dissmiss();
    }
}
