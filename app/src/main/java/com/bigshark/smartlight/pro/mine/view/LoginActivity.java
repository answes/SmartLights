package com.bigshark.smartlight.pro.mine.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigshark.smartlight.IndexActivity;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.RegiteredNavigationBuilder;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.yalantis.ucrop.dialog.SweetAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.cbDisplayPassword)
    CheckBox checkBox;
    @BindView(R.id.tv_findPsw)
    TextView tvFindPsw;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    private MinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityLogin);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    etPsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    etPsw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 101
                );
            }
        }
    }

    private void initToolbar() {
        RegiteredNavigationBuilder bar = new RegiteredNavigationBuilder(this);
        bar.setTitle("登陆").createAndBind(activityLogin);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MinePresenter(this);
        return presenter;
    }

    private long lastOnclick = 0;
    @OnClick({R.id.bt_login, R.id.tv_registered, R.id.tv_findPsw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                if(!check()){
                    return;
                }

                final SweetAlertDialog dialog = new SweetAlertDialog(this);
                dialog.setTitleText("正在登陆...");
                dialog.show();
                if(System.currentTimeMillis() -lastOnclick >=500){
                    lastOnclick = System.currentTimeMillis();
                    presenter.login(etPhone.getText().toString(), etPsw.getText().toString(), new BasePresenter.OnUIThreadListener<LoginResult>() {
                        @Override
                        public void onResult(LoginResult result) {
                            dialog.cancel();
                            getUserInfo(result.getData().getId());
                        }
                        @Override
                        public void onErro(String string) {
                            dialog.dismiss();
                            showMsg(string);
                        }
                    });
                }
                break;
            case R.id.tv_registered:
                RegisteredActivity.openRegisteredActivity(this);
                break;
            case R.id.tv_findPsw:
                FindPswActivity.openFindPswActivity(this);
                break;
        }
    }

    private void getUserInfo(String id) {
        presenter.getUserInfo(id, new BasePresenter.OnUIThreadListener<LoginResult>() {
            @Override
            public void onResult(LoginResult result) {
                SmartLightsApplication.USER = result.getData();
                SQLUtils.saveUser(LoginActivity.this,SmartLightsApplication.USER.getId());
                IndexActivity.openIndexActivity(LoginActivity.this);
                finish();
            }
            @Override
            public void onErro(String string) {

            }
        });
    }

    private boolean check(){
        if(TextUtils.isEmpty(etPhone.getText().toString())){
            showMsg("请输入手机号码");
            return  false;
        }
        if(TextUtils.isEmpty(etPsw.getText().toString())){
            showMsg("请输入密码");
            return  false;
        }
        return  true;
    }

    public static void openLoginActivity(Activity activity){
        activity.startActivity(new Intent(activity,LoginActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showMsg("定位权限获得成功");
            } else {
                showMsg("权限被拒绝，请在设置中设置定位权限");
            }
        }
    }
}
