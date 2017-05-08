package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.FindPsw;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.RegiteredNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.Code;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPswActivity extends BaseActivity {

    @BindView(R.id.tv_logo)
    ImageView tvLogo;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_yan)
    EditText etYan;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.et_twopsw)
    EditText etTwopsw;
    @BindView(R.id.bt_confim)
    Button btConfim;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    private Code code = Code.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityLogin);
        ivCode.setImageBitmap(code.createBitmap());
    }

    private void initToolbar() {
        RegiteredNavigationBuilder bar = new RegiteredNavigationBuilder(this);
        bar.setTitle("找回密码").setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).createAndBind(activityLogin);
    }
    private MinePresenter minePresenter;
    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    @OnClick({R.id.bt_confim,R.id.iv_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_confim:
            if (!check()) {
                return;
            }
            if (!etYan.getText().toString().equals("5374")) {
                showMsg("验证码不正确");
                return;
            }
            minePresenter.findPsw(etPhone.getText().toString(), etPsw.getText().toString(), etTwopsw.getText().toString(), new BasePresenter.OnUIThreadListener<FindPsw>() {
                @Override
                public void onResult(FindPsw result) {
                    showMsg(result.getExtra());
                    if (result.getCode() == 1) {
                        LoginActivity.openLoginActivity(FindPswActivity.this);
                        finish();
                    }
                }

                @Override
                public void onErro(String string) {
                    showMsg(string);
                }
            });
                break;
            case R.id.iv_code:
                ivCode.setImageBitmap(code.createBitmap());
                break;
        }
    }

    public static void openFindPswActivity(Activity activity){
        activity.startActivity(new Intent(activity,FindPswActivity.class));
    }

    private boolean check(){
        if (TextUtils.isEmpty(etPhone.getText().toString())){
            showMsg("请填写手机号码");
            return  false;
        }
        if(TextUtils.isEmpty(etPsw.getText().toString())){
            showMsg("请填密码");
            return  false;
        }
        if(TextUtils.isEmpty(etTwopsw.getText().toString())){
            showMsg("请确认密码");
            return  false;
        }
        if(!etTwopsw.getText().toString().equals(etPsw.getText().toString())){
            showMsg("两次密码不一致");
            return false;
        }
        if(TextUtils.isEmpty(etYan.getText())){
            showMsg("请输入验证码");
            return  false;
        }
        return  true;
    }
}
