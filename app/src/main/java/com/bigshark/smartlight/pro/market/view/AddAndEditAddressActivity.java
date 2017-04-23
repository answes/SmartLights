package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.Address;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.AddressPresenter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAndEditAddressActivity extends BaseActivity {
    private static  final int ADD = 0;
    private static final int EDIT = 1;

    @BindView(R.id.activity_add_address)
    LinearLayout llContent;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.stv_province)
    SuperTextView stvProvince;
    @BindView(R.id.stv_city)
    SuperTextView stvCity;
    @BindView(R.id.stv_district)
    SuperTextView stvDistrict;
    @BindView(R.id.bt_save)
    Button btSave;

    private AddressPresenter presenter;
    private String title;
    private int type;
    private AddressBean address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(llContent);
        initData();
    }

    private String province;
    private String city;
    private String area;


    @OnClick({R.id.stv_province,R.id.stv_city,R.id.stv_district})
    public void onClick(View view){
        OptionsPickerView  pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                area = (SmartLightsApplication.provinces.get(options1).getCity().get(options2).getString().get(options3));
                province = SmartLightsApplication.provinces.get(options1).getPickerViewText();
                city = SmartLightsApplication.provinces.get(options1).getCity().get(options2).getName();
                stvProvince.setRightString(province);
                stvCity.setRightString(city);
                stvDistrict.setRightString(area);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(SmartLightsApplication.provinces, SmartLightsApplication.cities,SmartLightsApplication.areas);//三级选择器
        pvOptions.show();
    }


    private void initData() {
        if(type == EDIT){
            address = (AddressBean) getIntent().getSerializableExtra("data");
            if(null !=address){
                etName.setText(address.getName());
                etTel.setText(address.getTel());
                etDetail.setText(address.getDetail());
                stvCity.setRightString(address.getCity());
                stvProvince.setRightString(address.getProvince());
                stvDistrict.setRightString(address.getDistrict());
            }
        }

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != getAddress()) {
                    if (type == ADD) {
                        presenter.addAddress(getAddress(), new BasePresenter.OnUIThreadListener<String>() {
                            @Override
                            public void onResult(String result) {
                                showMsg(result);
                                finish();
                            }

                            @Override
                            public void onErro(String string) {
                                showMsg(string);
                            }
                        });
                    } else {
                        presenter.editAddress(getAddress(), new BasePresenter.OnUIThreadListener<String>() {
                            @Override
                            public void onResult(String result) {
                                showMsg(result);
                                finish();
                            }
                            @Override
                            public void onErro(String string) {
                                showMsg(string);
                            }
                        });
                    }
                }
            }
        });

    }

    private AddressBean getAddress(){
        if(null == address){
            address = new AddressBean();
        }
        if(etName.getText().toString().trim().isEmpty()){
            ToastUtil.showToast(this,"收货人姓名不能为空");
            return  null;
        }
        if(etTel.getText().toString().trim().isEmpty()){
            ToastUtil.showToast(this,"收货人手机号码不能为空");
            return  null;
        }
        //TODO 这里得更改xml布局文件，superTextview并没有获取文字的方法。
        if(etDetail.getText().toString().trim().isEmpty()){
            ToastUtil.showToast(this,"详细地址不能为空");
            return  null;
        }
        address.setName(etName.getText().toString().trim());
        address.setTel(etTel.getText().toString().trim());
        address.setProvince(province);
        address.setCity(city);
        address.setDistrict(area);
        address.setDetail(etDetail.getText().toString());
        return  address;
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new AddressPresenter(this);
        return presenter;
    }

    private void initToolbar() {
        type = getIntent().getIntExtra("type",0);
        if( type== ADD){
            title = "新增收货地址";
        }else{
            title = "编辑收货地址";
        }
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle(title).createAndBind(llContent);
    }

    public static void openAddAddressActivityForReslut(Activity activity,int requestCode){
        Intent intent = new Intent(activity,AddAndEditAddressActivity.class);
        intent.putExtra("type",ADD);
        activity.startActivityForResult(intent,requestCode);
    }
    public static void openEditAddressActivityForReslut(Activity activity,int requestCode,AddressBean data){
        Intent intent = new Intent(activity,AddAndEditAddressActivity.class);
        intent.putExtra("type",EDIT);
        intent.putExtra("data",data);
        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.setResult(RESULT_OK);
    }
}
