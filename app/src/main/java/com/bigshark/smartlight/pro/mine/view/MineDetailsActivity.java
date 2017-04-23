package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.view.navigation.RegiteredNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineDetailsActivity extends BaseActivity {

    @BindView(R.id.stv_name)
    SuperTextView stvName;
    @BindView(R.id.stv_phone)
    SuperTextView stvPhone;
    @BindView(R.id.stv_sex)
    SuperTextView stvSex;
    @BindView(R.id.stv_height)
    SuperTextView stvHeight;
    @BindView(R.id.stv_weight)
    SuperTextView stvWeight;
    @BindView(R.id.activity_mine_details)
    LinearLayout activityMineDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_details);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityMineDetails);
        setUserInfo();
        setClick();
    }

    private void setUserInfo() {
        LoginResult.User user = SmartLightsApplication.USER;
        stvName.setRightString(user.getName());
        stvPhone.setRightString(user.getTel());
        stvSex.setRightString(user.getSex());
        stvHeight.setRightString(user.getHeight());
        stvWeight.setRightString(user.getWeight());
    }

    private void setClick() {
        stvName.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "姓名修改", "姓名");
            }
        });
        stvPhone.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "手机号码修改", "手机号码");
            }
        });
        stvSex.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "性别修改", "性别");
            }
        });
        stvHeight.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "身高修改", "身高");
            }
        });
        stvWeight.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onSuperTextViewClick() {
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "体重修改", "体重");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            setUserInfo();
        }
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("详情").createAndBind(activityMineDetails);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    public static void openMineDetailsActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MineDetailsActivity.class));
    }
}
