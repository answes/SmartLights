package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePswActivity extends BaseActivity {

    @BindView(R.id.et_oldPsw)
    EditText etOldPsw;
    @BindView(R.id.et_newPsw)
    EditText etNewPsw;
    @BindView(R.id.et_newPswS)
    EditText etNewPswS;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.activity_change_psw)
    LinearLayout activityChangePsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_psw);
        ButterKnife.bind(this);
        initToolbar();
        bindPresneter();
        SupportMultipleScreensUtil.scale(activityChangePsw);
    }
    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("修改密码") .createAndBind(activityChangePsw);
    }

    private MinePresenter minePresenter;
    @Override
    public MVPBasePresenter bindPresneter(){
            minePresenter = new MinePresenter(this);
            return minePresenter;
    }

    @OnClick(R.id.bt_confirm)
    public void onClick() {

    }


    public static void openChangePswActivity(Activity activity){
        activity.startActivity(new Intent(activity,ChangePswActivity.class));
    }
}
