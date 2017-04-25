package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.view.navigation.RegiteredNavigationBuilder;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        info = getIntent().getStringExtra("info");
        initToolbar();
        SupportMultipleScreensUtil.scale(activityModifyInfo);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    private void initToolbar() {
        RegiteredNavigationBuilder bar = new RegiteredNavigationBuilder(this);
        bar.setRightText("完成").setTitle(title).setLeftIcon(R.drawable.left_back).setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setRightIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).createAndBind(activityModifyInfo);
    }

    public static void openModifyInfoActivity(Activity activity, String title, String info) {
        Intent intent = new Intent(activity, ModifyInfoActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("info", info);
        activity.startActivity(intent);
    }
}
