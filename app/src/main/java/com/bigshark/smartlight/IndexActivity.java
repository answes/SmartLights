package com.bigshark.smartlight;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.index.presenter.MapPreseter;
import com.bigshark.smartlight.pro.index.view.EndConfirmActivity;
import com.bigshark.smartlight.pro.index.view.MapActivity;
import com.bigshark.smartlight.pro.index.view.navigation.IndexNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.MessgeActivity;
import com.bigshark.smartlight.pro.mine.view.MineActivity;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 最新的首页
 */
public class IndexActivity extends BaseActivity {
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.iv_lock)
    ImageView ivLock;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_speed2)
    TextView tvSpeed2;
    @BindView(R.id.tv_high_speed)
    TextView tvHighSpeed;
    @BindView(R.id.btn_find)
    TextView btnFind;
    @BindView(R.id.ll_context)
    LinearLayout llContext;
    @BindView(R.id.bt_stop)
    Button btStop;
    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.index_bottom)
    LinearLayout IndexBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.init(getApplication());
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

    /**
     * 查找用户新车
     *
     * @param view
     */
    @OnClick({R.id.btn_find, R.id.tv_location, R.id.bt_stop, R.id.bt_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_find:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
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
                    // btnFind.setText("结束骑行");
                    btnFind.setVisibility(View.GONE);
                    IndexBottom.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_location:
                if (isStart) {
                    MapActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), true, mapPreseter.getSavesLatlng());
                } else {
                    showMsg("在骑行状态下打才能查看路径");
                }
                break;
            case R.id.bt_finish:
                mapPreseter.stop();
                EndConfirmActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), mapPreseter.getSavesLatlng(),mapPreseter);
                break;
            case R.id.bt_stop:
                if(btStop.getText().toString().equals("暂停骑行")) {
                    isStart = false;
                    btStop.setText("恢复骑行");
                    mapPreseter.stop();
                }else{
                    btStop.setText("暂停骑行");
                    isStart = true;
                    mapPreseter.restart();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadCasst();
    }

    private MapLocationRecive mapLocationRecive;

    private void registerBroadCasst() {
        if (mapLocationRecive == null) {
            mapLocationRecive = new MapLocationRecive(new MapLocationRecive.OnLocationReciveListener() {
                @Override
                public void onRevice(final UpLoadRecord record) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHeight.setText(String.valueOf(record.getHeight()) + "m");
                            tvHighSpeed.setText(String.format("%.2f", record.getMaxSpeed()) + "km/h");
                            tvHot.setText(String.format("%.2f", record.getK()) + "Cal");
                            tvSpeed.setText(String.format("%.2f", record.getSpeed()) + "km/h");
                            tvSpeed2.setText(String.format("%.2f", record.getSpeed()) + "km/h");
                            tvTotal.setText(String.format("%.2f", (record.getDistance()) / 1000) + "km");
                        }
                    });
                }
            });
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MapLocationRecive.ACTION);
        registerReceiver(mapLocationRecive, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapPreseter.finish(null);
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

        if(requestCode == EndConfirmActivity.REQUEST_END_CONFIRM && RESULT_OK ==resultCode ){
            btnFind.setVisibility(View.VISIBLE);
            IndexBottom.setVisibility(View.GONE);
            tvSpeed.setText("0.00km/h");
            tvHot.setText("0Cal");
            tvTotal.setText("0km");
            tvHeight.setText("0.0m");
            tvSpeed2.setText("0.00km/h");
            tvHighSpeed.setText("0.00km/h");
        }else if(requestCode == EndConfirmActivity.REQUEST_END_CONFIRM && RESULT_OK == resultCode){
            mapPreseter.restart();
        }
    }
}
