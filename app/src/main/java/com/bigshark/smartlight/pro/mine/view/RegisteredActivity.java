package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.RegisterResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.RegiteredNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.Code;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yan)
    EditText etYan;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.bt_registered)
    Button btRegistered;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.activity_registered)
    LinearLayout activityRegistered;
    @BindView(R.id.cbDisplayPassword)
    CheckBox checkBox;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        ButterKnife.bind(this);
        initToolbar();
        bindPresneter();
        SupportMultipleScreensUtil.scale(activityRegistered);
        ivCode.setImageBitmap(code.createBitmap());
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
    }

    private void initToolbar() {
        RegiteredNavigationBuilder bar = new RegiteredNavigationBuilder(this);
        bar.setTitle("注册").setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).createAndBind(activityRegistered);
    }

    private MinePresenter minePresenter;

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }
    private Code code = Code.getInstance();
    @OnClick({R.id.bt_registered, R.id.tv_protocol,R.id.iv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_registered:
                if (!check()) {
                    return;
                }
                if (!etYan.getText().toString().equals(code.getCode())) {
                    showMsg("验证码不正确");
                    return;
                }
                minePresenter.registered(etPhone.getText().toString(), etPsw.getText().toString(), new BasePresenter.OnUIThreadListener<RegisterResult>() {
                    @Override
                    public void onResult(RegisterResult result) {
                        showMsg("注册成功");
                        if (result.getCode() == 1) {
                            LoginActivity.openLoginActivity(RegisteredActivity.this);
                            finish();
                        }
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                    }
                });
                break;
            case R.id.tv_protocol:
                break;
            case R.id.iv_code:
                ivCode.setImageBitmap(code.createBitmap());
                break;
        }
    }


    public static void openRegisteredActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RegisteredActivity.class));
    }

    /**
     * 检查数据完整
     */
    private boolean check() {
        if (TextUtils.isEmpty(etPhone.getText())) {
            showMsg("请填写手机号码");
            return false;
        }
        if (TextUtils.isEmpty(etPsw.getText())) {
            showMsg("请填写登录密码");
            return false;
        }
        if (TextUtils.isEmpty(etYan.getText())) {
            showMsg("请填写验证码");
            return false;
        }
        if (etPsw.getText().toString().length() < 6 && etPsw.getText().toString().length() > 8) {
            showMsg("密码长度在6到18位");
            return false;
        }
        return true;
    }
}

