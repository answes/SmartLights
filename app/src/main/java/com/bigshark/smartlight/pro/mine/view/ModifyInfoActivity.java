package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ModifyInfoActivity extends BaseActivity {
    private String title;
    private String info;

    @BindView(R.id.et_info)
    EditText etInfo;
    @BindView(R.id.activity_modify_info)
    LinearLayout activityModifyInfo;
    private MinePresenter minePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        info = getIntent().getStringExtra("info");
        initToolbar();
        setEtInfo();
        SupportMultipleScreensUtil.scale(activityModifyInfo);
    }

    private void setEtInfo() {
        if("姓名修改".equals(title)){
            etInfo.setHint("请填写 姓名");
            return;
        }
        if("性别修改".equals(title)){
            etInfo.setHint("请填写 男 或 女 ");
            return;
        }
        etInfo.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        etInfo.setHint(title);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    private void initToolbar() {
        MineNavigationBuilder bar = new MineNavigationBuilder(this);
        bar.setTitle(title).setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etInfo.getText().toString().isEmpty()){
                    showMsg("修改内容不能为空");
                    return;
                }
                upUser();
                upUserInfo();
            }
        }).createAndBind(activityModifyInfo);
    }

    private void upUser() {
        LoginResult.User user = SmartLightsApplication.USER;
        if("姓名修改".equals(title)){
            user.setName(etInfo.getText().toString().trim());
        }else if("手机号码修改".equals(title)){
            user.setTel(etInfo.getText().toString().trim());
        }
        else if("性别修改".equals(title)){
            user.setSex(etInfo.getText().toString().trim());
        }
        else if("身高修改".equals(title)){
            user.setHeight(etInfo.getText().toString().trim());
        }
        else if("体重修改".equals(title)){
            user.setWeight(etInfo.getText().toString().trim());
        }

    }

    private void upUserInfo() {
        minePresenter.upUserInfo(new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                JSONObject json = JSONObject.parseObject(result);
                int code = (int) json.get("code");
                if (1 == code) {
                    showMsg("修改成功");
                    ModifyInfoActivity.this.setResult(RESULT_OK);
                }else{
                    showMsg(json.getString("extra"));
                }
                finish();
            }

            @Override
            public void onErro(String string) {
                finish();
            }
        });
    }

    public static void openModifyInfoActivityForResult(Activity activity, String title, String info) {
        Intent intent = new Intent(activity, ModifyInfoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("info", info);
        activity.startActivityForResult(intent,0x001);
    }
}
