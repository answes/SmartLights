package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.IndexActivity;
import com.bigshark.smartlight.MainActivity;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.BLuetoothData;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.Contact;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.stv_about)
    SuperTextView stvAbout;
    @BindView(R.id.stv_changePsw)
    SuperTextView stvChangePsw;
    @BindView(R.id.stv_emptyCache)
    SuperTextView stvEmptyCache;
    @BindView(R.id.stv_update)
    SuperTextView stvUpate;

    @BindView(R.id.bt_logout)
    Button btLogout;
    @BindView(R.id.activity_set)
    LinearLayout rootView;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.switch_auto)
    Switch switchAuto;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(rootView);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SQLUtils.appConfig(SetActivity.this,true);
                }else{
                    SQLUtils.appConfig(SetActivity.this,false);
                }
                SmartLightsApplication.isOpenVioce = b ;
            }
        });
        switchAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    SQLUtils.appAutoConfig(SetActivity.this,true);
                }else{
                    SQLUtils.appAutoConfig(SetActivity.this,false);
                }
                SmartLightsApplication.isAutoClose = b;
            }
        });

        switch1.setChecked(SQLUtils.getConfig(this));
        switchAuto.setChecked(SQLUtils.getAutoConfig(this));

        IndexActivity.onDisdialogMissListener = new IndexActivity.OnDisdialogMissListener() {
            @Override
            public void dissmiss() {
                    if(dialog != null){
                        dialog.dismiss();
                        dialog.cancel();
                    }
            }
        };
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("设置").createAndBind(rootView);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @OnClick({R.id.stv_about, R.id.stv_changePsw, R.id.stv_emptyCache, R.id.bt_logout,R.id.stv_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_about:
                AboutActivity.openAboutActivity(this);
                break;
            case R.id.stv_changePsw:
                ChangePswActivity.openChangePswActivity(this);
                break;
            case R.id.stv_emptyCache:
                ToastUtil.showToast(this, "已清除30k缓存");
                break;
            case R.id.bt_logout:
                SQLUtils.clearUser(this);
                LoginActivity.openLoginActivity(this);
                break;
            case R.id.stv_update:
                try {
                    IndexActivity.sendData(BLuetoothData.getFirmwareVersoin());
                    if(dialog == null){
                        dialog = ProgressDialog.show(this,"提示","正在升级....");
                    }
                    dialog.show();
                }catch (Exception e){
                    showMsg("请链接蓝牙之后再进行升级");
                    if(dialog != null){
                        dialog.cancel();
                    }
                }
                break;
        }
    }

    public static void openSetActivity(Activity activity) {
        activity.startActivity(new Intent(activity, SetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IndexActivity.onDisdialogMissListener = null;
    }
}
