package com.bigshark.smartlight.pro.mine.view;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.allen.library.SuperTextView;
import com.android.internal.http.multipart.FilePart;
import com.android.internal.http.multipart.Part;
import com.android.internal.http.multipart.StringPart;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.utils.MD5Utils;
import com.bigshark.smartlight.utils.MultipartRequest;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.VolleyUtils;
import com.bigshark.smartlight.weight.CircleNetworkImageImage;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends BaseActivity {
    @BindView(R.id.ll_context)
    LinearLayout llContext;
    @BindView(R.id.iv_hander)
    CircleNetworkImageImage ivHander;
    @BindView(R.id.iv_userName)
    TextView ivUserName;
    @BindView(R.id.stv_myStroke)
    SuperTextView stvMyStroke;
    @BindView(R.id.stv_myorder)
    SuperTextView stvMyorder;
    @BindView(R.id.stv_myEquipment)
    SuperTextView stvMyEquipment;
    @BindView(R.id.stv_market)
    SuperTextView stvMarket;
    @BindView(R.id.stv_set)
    SuperTextView stvSet;

    private MinePresenter minePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContext);
        ivUserName.setText(SmartLightsApplication.USER.getName());
        if(null != SmartLightsApplication.USER.getFig() && !SmartLightsApplication.USER.getFig().isEmpty()) {
            VolleyUtils.loadImage(MineActivity.this, ivHander, SmartLightsApplication.USER.getFigimg());
        }
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("我的个人中心").createAndBind(llContext);
    }
    @OnClick({R.id.stv_myStroke, R.id.stv_myorder, R.id.stv_myEquipment,R.id.stv_market, R.id.stv_set,R.id.iv_hander,R.id.iv_userName})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stv_myStroke:
                RideActivity.openRideActivity(this);
                break;
            case R.id.stv_myorder:
                MyOrderActivity.openMyOrderActivity(this);
                break;
            case R.id.stv_myEquipment:
                EquipmentActivity.openEquipmentActivity(this);
                break;
            case R.id.stv_market:
                MarketActivity.openMarketActivity(this);
                break;
            case R.id.stv_set:
                SetActivity.openSetActivity(this);
                break;
            case R.id.iv_hander:
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                }else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("更换头像")
                            .setMessage("请选择相册或拍照")
                            .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //选择相册
                                    PictureConfig.getInstance().openPhoto(MineActivity.this, resultCallback);
                                }
                            })
                            .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //拍照
                                    PictureConfig.getInstance().startOpenCamera(MineActivity.this);
                                }
                            });
                    alertDialog.show();
                }
                break;
            case R.id.iv_userName:
                MineDetailsActivity.openMineDetailsActivity(this);
                break;
        }
    }
    public static void openMineActivity(Activity activity){
        activity.startActivity(new Intent(activity,MineActivity.class));
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            if(null != resultList && resultList.size() != 0){
                LocalMedia photo =  resultList.get(0);
                upLoadImage(photo);
            }
        }

        @Override
        public void onSelectSuccess(LocalMedia localMedia) {
            upLoadImage(localMedia);
        }
    };

    private void upLoadImage(LocalMedia photo) {
        //构造参数列表
        List<Part> partList = new ArrayList<>();
        Log.e("TAG", "upLoadImage: "+photo.getCompressPath() );
        try {
            partList.add(new StringPart("fmd", MD5Utils.getMd5ByFile(new File(photo.getCompressPath()))));
            partList.add(new FilePart("File", new File(photo.getCompressPath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String url = "http://pybike.idc.zhonxing.com/File/uploadPicture";
        //生成请求
        MultipartRequest profileUpdateRequest = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = JSONObject.parseObject(response);
                String fig = json.getString("data");
                upUserInfo(fig);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //处理失败错误信息
                Log.e("MultipartRequest", error.getMessage(), error);
                Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //将请求加入队列
        SmartLightsApplication.queue.add(profileUpdateRequest);
    }

    private void upUserInfo(String fig) {
        LoginResult.User user = SmartLightsApplication.USER;
        user.setFig(fig);
        minePresenter.upUserInfo(new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                getUserInfo();
            }

            @Override
            public void onErro(String string) {
            }
        });
    }

    private void getUserInfo() {
        minePresenter.getUserInfo(SmartLightsApplication.USER.getId(),new BasePresenter.OnUIThreadListener<LoginResult>() {
            @Override
            public void onResult(LoginResult result) {
                VolleyUtils.loadImage(MineActivity.this,ivHander,result.getData().getFigimg());
            }

            @Override
            public void onErro(String string) {

            }
        });

    }

    /**
     * 単独拍照图片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FunctionConfig.CAMERA_RESULT) {
                if (data != null) {
                    List<LocalMedia> selectMedia = (List<LocalMedia>) data.getSerializableExtra(FunctionConfig.EXTRA_RESULT);
                    if (selectMedia != null && selectMedia.size() != 0) {
                        LocalMedia photo =  selectMedia.get(0);
                        upLoadImage(photo);
                    }
                }
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        ivUserName.setText(SmartLightsApplication.USER.getName());
    }
}
