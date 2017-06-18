package com.bigshark.smartlight;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.bigshark.smartlight.bean.BLuetoothData;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.BluetoothStateRecive;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.index.presenter.MapPreseter;
import com.bigshark.smartlight.pro.index.service.BluetoothLeService;
import com.bigshark.smartlight.pro.index.view.EndConfirmActivity;
import com.bigshark.smartlight.pro.index.view.MapActivity;
import com.bigshark.smartlight.pro.index.view.ScanActivity;
import com.bigshark.smartlight.pro.index.view.navigation.IndexNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.MessgeActivity;
import com.bigshark.smartlight.pro.mine.view.MineActivity;
import com.bigshark.smartlight.utils.GPSUtil;
import com.bigshark.smartlight.utils.MediaPlayerUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;
import com.bigshark.smartlight.weight.CustomArcView;
import com.bigshark.smartlight.weight.TakePhotoPopWin;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContext);
        //蓝牙连接服务
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        mediaPlayerUtils = new MediaPlayerUtils(this, arcView);
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
        })
                .createAndBind(llContext);
    }

    public static void openIndexActivity(Activity a) {
        a.startActivity(new Intent(a, IndexActivity.class));
    }

    private String mac;
    private boolean isStart = false;
    private WindowManager.LayoutParams params;
    private TakePhotoPopWin takePhotoPopWin;

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
                startRide();
                break;
            case R.id.frame_location:
                if (isStart) {
                    MapActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), true, mapPreseter.getSavesLatlng());
                } else {
                    showMsg("在骑行状态下打才能查看路径");
                }
                break;
            case R.id.bt_finish:
                isStart = false;
                mapPreseter.stop();
                EndConfirmActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), mapPreseter.getSavesLatlng(), mapPreseter);
                break;
            case R.id.bt_stop:
                if (btStop.getText().toString().equals("暂停骑行")) {
                    isStart = false;
                    btStop.setText("恢复骑行");
                    mapPreseter.stop();
                } else {
                    btStop.setText("暂停骑行");
                    isStart = true;
                    mapPreseter.restart();
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
                    ScanActivity.openScanActivity(IndexActivity.this);
                }
                break;
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

    @Override

    protected void onResume() {
        super.onResume();
        registerBroadCasst();
    }

    private MapLocationRecive mapLocationRecive;
    private BluetoothStateRecive bluetoothStateRecive;

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
                            tvHour.setText(new StringBuffer()
                                            .append(String.format("%02d", record.getTime() / (60 * 60 * 1000)))
                                            .append(":")
                                            .append(String.format("%02d", record.getTime() % (60 * 60 * 1000) / (60 * 1000)))
                                            .append(":")
                                            .append((String.format("%02d", record.getTime() % (60 * 60 * 1000) % (60 * 1000) / (1000))))
                                            .toString());

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
                            if (3 == state) {
                                if (3 == realData[4]) {
                                    //0x16禁止
//                                    int count = (realData[7] * 256 + realData[8])/700;
                                    try {
                                        int hight = realData[7];
                                        int low = realData[8];
                                        tvEle.setVisibility(View.VISIBLE);
                                        int count = ((Integer.parseInt( (int)realData[7] + "" + (int)realData[8])) - 3500) / 700;

                                        if(count<20){
                                            tvEle.setImageResource(R.drawable.ele_low);
                                        }else if(count<40){
                                            tvEle.setImageResource(R.drawable.ele_low2);
                                        }else if(count<60){
                                            tvEle.setImageResource(R.drawable.ele_medum);
                                        }else if(count<80){
                                            tvEle.setImageResource(R.drawable.ele_high1);
                                        }else{
                                            tvEle.setImageResource(R.drawable.ele_high2);
                                        }
                                    } catch (Exception e) {

                                    }
                                }
                                //专项
                                if (5 == realData[4]) {
                                    int tuen = realData[6];
                                    if (1 == tuen) {
                                        mediaPlayerUtils.palyLeftMedia();
                                        arcView.setLeftDraw();
                                    } else if (2 == tuen) {
                                        mediaPlayerUtils.palyRightMedia();
                                        arcView.setRihgtDraw();
                                    }else if(3 == tuen){
                                        mediaPlayerUtils.playEnd();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                arcView.setFuyuanDraw();
                                            }
                                        }, 200);
                                    }else{
                                        mediaPlayerUtils.setPlayTime();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                arcView.setFuyuanDraw();
                                            }
                                        }, 200);
                                    }

                                }

                                if (1 == realData[4]) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            arcView.setShacheDraw();
                                        }
                                    });
                                    mediaPlayerUtils.palyShacheMedia();
                                }
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
        } else if (requestCode == EndConfirmActivity.REQUEST_END_CONFIRM && RESULT_OK == resultCode) {
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
                ToastUtil.showToast(IndexActivity.this, "次设", 1000);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    //连接
    public static void conect(String addrss) {
        mBluetoothLeService.connect(addrss);
    }

    public static void sendData(byte[] data) {
        Log.i("Test", mBluetoothLeService.sendValue(data) + "");
    }
}
