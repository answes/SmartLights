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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.bigshark.smartlight.pro.market.view.SelectAddressActivity;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.utils.MD5Utils;
import com.bigshark.smartlight.utils.MultipartRequest;
import com.bigshark.smartlight.utils.SQLUtils;
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
    @BindView(R.id.iv_hander)
    CircleNetworkImageImage ivHander;
    @BindView(R.id.rl_haed)
    RelativeLayout rlHaed;
    @BindView(R.id.stv_age)
    SuperTextView stvAge;
    @BindView(R.id.stv_address)
    SuperTextView stvAddress;
    @BindView(R.id.bt_logout)
    Button btLogout;
    private MinePresenter minePresenter;

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
        if ("1".equals(user.getSex())) {
            stvSex.setRightString("男");
        } else {
            stvSex.setRightString("女");
        }
        stvAge.setRightString(user.getAge());
        stvHeight.setRightString(user.getHeight());
        stvWeight.setRightString(user.getWeight());
        if (null != SmartLightsApplication.USER.getFig() && !SmartLightsApplication.USER.getFig().isEmpty()) {
            VolleyUtils.loadImage(MineDetailsActivity.this, ivHander, SmartLightsApplication.USER.getFigimg());
        }
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
                UpdateNumActivity.openUpdateNumActivity(MineDetailsActivity.this);
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
        stvAge.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onSuperTextViewClick() {
                super.onSuperTextViewClick();
                ModifyInfoActivity.openModifyInfoActivityForResult(MineDetailsActivity.this, "年龄修改", "年龄");
            }
        });
        stvAddress.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener(){
            @Override
            public void onSuperTextViewClick() {
                super.onSuperTextViewClick();
                SelectAddressActivity.openSelectAddressActivityForResult(MineDetailsActivity.this, 0x020);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ModifyInfoActivity.INFORESULTCODE) {
            setUserInfo();
        }else if (resultCode == RESULT_OK) {
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
        minePresenter = new MinePresenter(this);
        return minePresenter;
    }

    public static void openMineDetailsActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MineDetailsActivity.class));
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
                VolleyUtils.loadImage(MineDetailsActivity.this,ivHander,result.getData().getFigimg());
            }

            @Override
            public void onErro(String string) {

            }
        });

    }

    @OnClick({R.id.rl_haed, R.id.bt_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_haed:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                                    PictureConfig.getInstance().openPhoto(MineDetailsActivity.this, resultCallback);
                                }
                            })
                            .setNegativeButton("拍照", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //拍照
                                    PictureConfig.getInstance().startOpenCamera(MineDetailsActivity.this);
                                }
                            });
                    alertDialog.show();
                }


                break;
            case R.id.bt_logout:
                SQLUtils.clearUser(this);
                LoginActivity.openLoginActivity(this);
                break;
        }
    }
}
