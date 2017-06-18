package com.bigshark.smartlight.pro.index;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps2d.MapView;
import com.bigshark.smartlight.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.navi_view)
    MapView naviView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        naviView.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        naviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        naviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        naviView.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onStop();
    }

    public static  void openActivity(Context context){
            context.startActivity(new Intent(context, TestActivity.class));
    }
}
