package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Address;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.AddressPresenter;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.AddressListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectAddressActivity extends BaseActivity {
    private final int REQUSETCODE = 0x001;

    @BindView(R.id.activity_select_address)
    LinearLayout activitySelectAddress;
    @BindView(R.id.rv_adList)
    RecyclerView rvAdList;
    @BindView(R.id.bt_add_adress)
    Button addAdress;

    private AddressPresenter presenter;
    private List<AddressBean> addressDatas;
    private AddressListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activitySelectAddress);
        initData();
        onClick();
        getData();
    }

    private void getData() {
        presenter.getAddressList(new BasePresenter.OnUIThreadListener<List<AddressBean>>() {
            @Override
            public void onResult(List<AddressBean> result) {
                if(null ==result || result.isEmpty()){
                    //TODO 显示为空 要添加
                    return;
                }
                addressDatas.clear();
                addressDatas.addAll(result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(String string) {

            }
        });
    }

    private void onClick() {
        adapter.setOnCheckBoxLinstener(new AddressListAdapter.OnCheckBoxLinstener() {
            @Override
            public void onResult(int postion, boolean b) {

            }
        });

        adapter.setOnDelClickLinstener(new AddressListAdapter.OnDelClickLinstener() {
            @Override
            public void onReslut(final int postion) {
                presenter.delAddress(addressDatas.get(postion).getId(), new BasePresenter.OnUIThreadListener<String>() {
                    @Override
                    public void onResult(String result) {
                        showMsg(result);
                        addressDatas.remove(postion);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                    }
                });
            }
        });

        adapter.setOnEditClickLinstener(new AddressListAdapter.OnEditClickLinstener() {
            @Override
            public void onReslut(int postion) {
                AddAndEditAddressActivity.openEditAddressActivityForReslut(SelectAddressActivity.this,REQUSETCODE,addressDatas.get(postion));
            }
        });

        adapter.setOnDefaultClickLinstenr(new AddressListAdapter.OnDefaultClickLinstenr() {
            @Override
            public void onResult(final int postion) {
                presenter.setDefultAddress(addressDatas.get(postion).getId(), new BasePresenter.OnUIThreadListener<String>() {
                    @Override
                    public void onResult(String result) {
                        showMsg(result);
                        getData();
                    }

                    @Override
                    public void onErro(String string) {
                        showMsg(string);
                    }
                });
            }
        });

        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAndEditAddressActivity.openAddAddressActivityForReslut(SelectAddressActivity.this,REQUSETCODE);
            }
        });

    }

    private void initData() {
        addressDatas = new ArrayList<>();

        rvAdList.setLayoutManager( new LinearLayoutManager(this));
        rvAdList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());
        adapter = new AddressListAdapter(this,addressDatas);
        rvAdList.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new AddressPresenter(this);
        return presenter;
    }
    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("收货地址").createAndBind(activitySelectAddress);
    }

    public static void openSelectAddressActivityForResult(Activity activity,int requestCode){
        activity.startActivityForResult(new Intent(activity,SelectAddressActivity.class),requestCode);
    }
}
