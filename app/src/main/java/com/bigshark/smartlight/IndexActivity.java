package com.bigshark.smartlight;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.bigshark.smartlight.bean.UpLoadRecord;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.index.broadcast.MapLocationRecive;
import com.bigshark.smartlight.pro.index.presenter.MapPreseter;
import com.bigshark.smartlight.pro.index.view.MapActivity;
import com.bigshark.smartlight.pro.index.view.ScanActivity;
import com.bigshark.smartlight.pro.index.view.navigation.IndexNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.MessgeActivity;
import com.bigshark.smartlight.pro.mine.view.MineActivity;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.List;

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
     * @param view
     */
    @OnClick({R.id.btn_find,R.id.tv_location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_find:
                isStart = !isStart;
                if(isStart) {
                    mapPreseter.start();
                    btnFind.setText("结束骑行");
                }else{
                    btnFind.setText("开始骑行");
                    mapPreseter.stop(new BasePresenter.OnUIThreadListener<String>() {
                        @Override
                        public void onResult(String result) {
                            showMsg(result);
                        }

                        @Override
                        public void onErro(String string) {
                            showMsg(string);
                        }
                    });
                }
                break;
            case R.id.tv_location:
                if(isStart) {
                    MapActivity.openMapActivity(this, mapPreseter.getUplodeRecord(), true,mapPreseter.getSavesLatlng());
                }else {
                    showMsg("在骑行状态下打才能查看路径");
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
    private void registerBroadCasst(){
        if(mapLocationRecive==null){
            mapLocationRecive = new MapLocationRecive(new MapLocationRecive.OnLocationReciveListener() {
                @Override
                public void onRevice(final UpLoadRecord record) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvHeight.setText(String.valueOf(record.getHeight())+"m");
                            tvHighSpeed.setText(String.format("%.2f",record.getMaxSpeed())+"km/h");
                            tvHot.setText(String.format("%.2f",record.getK())+"Cal");
                            tvSpeed.setText(String.format("%.2f",record.getSpeed())+"km/h");
                            tvSpeed2.setText(String.format("%.2f",record.getSpeed())+"km/h");
                            tvTotal.setText(String.format("%.2f",(record.getDistance())/1000)+"km");
                        }
                    });
                }
            });
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MapLocationRecive.ACTION);
        registerReceiver(mapLocationRecive,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapPreseter.stop(null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterBroadCast();
    }

    private void unRegisterBroadCast(){
        if(mapLocationRecive!=null){
            unregisterReceiver(mapLocationRecive);
        }
    }
}
