package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;

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
    @BindView(R.id.bt_logout)
    Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);
        initToolbar();
    }

    private void initToolbar() {
        LinearLayout rootView = (LinearLayout) findViewById(R.id.activity_set);
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitle("设置") .createAndBind( rootView);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @OnClick({R.id.stv_about, R.id.stv_changePsw, R.id.stv_emptyCache, R.id.bt_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_about:
                break;
            case R.id.stv_changePsw:
                ChangePswActivity.openChangePswActivity(this);
                break;
            case R.id.stv_emptyCache:
                break;
            case R.id.bt_logout:
                break;
        }
    }

    public static void openSetActivity(Activity activity){
        activity.startActivity(new Intent(activity,SetActivity.class));
    }
}
