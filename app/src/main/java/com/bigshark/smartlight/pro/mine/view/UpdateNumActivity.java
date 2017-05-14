package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.Result;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.utils.Contact;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateNumActivity extends BaseActivity {
    public  static  final  int REQUEST_MINE_DETALI =0x89;
    @BindView(R.id.et_old_phone)
    EditText etOldPhone;
    @BindView(R.id.et_yan)
    EditText etYan;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.et_new_phone)
    EditText etNewPhone;
    @BindView(R.id.ll_context)
    LinearLayout llContext;

    public static void openUpdateNumActivity(Activity activity){
        activity.startActivityForResult(new Intent(activity,UpdateNumActivity.class),REQUEST_MINE_DETALI);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_num);
        ButterKnife.bind(this);
        SupportMultipleScreensUtil.scale(llContext);
        etOldPhone.setText(SmartLightsApplication.USER.getTel());
        etOldPhone.setEnabled(false);
    }
    private MinePresenter minePresenter;
    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    private CountDownTimer countDownTimer = new CountDownTimer(61000L,1000L) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText(millisUntilFinished/1000+"S");
        }

        @Override
        public void onFinish() {
            btnCode.setEnabled(true);
            btnCode.setText("获取验证码");
        }
    };

    private String code = "";
    @OnClick({R.id.btn_code,R.id.btn_update})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_code:
                    btnCode.setEnabled(false);
                countDownTimer.start();
                minePresenter.getCode(new BasePresenter.OnUIThreadListener<String>() {
                    @Override
                    public void onResult(String result) {
                        showMsg("验证码已经发送到您的手机，请注意查收");
                        code = result;
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                    }
                });
                break;
            case R.id.btn_update:
                if(etNewPhone.getText().toString().equals("")){
                    showMsg("您还没有输入手机号码");
                    return;
                }

                if(code.equals("")){
                    showMsg("您还没有获取验证码");
                    return;
                }

                if(!code.equals(etYan.getText().toString())){
                    showMsg("您输入的验证码有误");
                    return;
                }
                SmartLightsApplication.USER.setTel(etNewPhone.getText().toString());
                minePresenter.upUserInfo(new BasePresenter.OnUIThreadListener<String>() {
                    @Override
                    public void onResult(String result) {
                        try {
                            Result result1 = JSON.parseObject(result, Result.class);
                            if(result1.getCode() == 1){
                                showMsg("修改手机号码成功");
                                setResult(RESULT_OK);
                                finish();
                            }else{
                                showMsg("修改手机号码失败");
                            }
                        }catch (Exception e){
                            showMsg("修改手机号码失败");
                            setResult(RESULT_CANCELED);
                        }
                    }

                    @Override
                    public void onErro(String string) {

                    }
                });
                break;
        }
    }
}
