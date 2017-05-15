package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.AddressPresenter;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.ConfiremGoodsListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmOrederActivity extends BaseActivity {
    private final int  REQUESTCODE = 0x001;


    @BindView(R.id.stv_name)
    SuperTextView stvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.iv_in)
    ImageView ivIn;
    @BindView(R.id.stv_title)
    SuperTextView stvTitle;
    @BindView(R.id.rv_goods)
    RecyclerView rvGoods;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.activity_confirm_oreder)
    RelativeLayout activityConfirmOreder;

    private ConfiremGoodsListAdapter adapter;
    private List<CarGoods.Good> datas = new ArrayList<>();
    private MarketListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_oreder);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityConfirmOreder);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    private void initData() {
        Gson gson = new Gson();
        List<CarGoods.Good> g= gson.fromJson(getIntent().getStringExtra("data"),new TypeToken<List<CarGoods.Good>>(){}.getType());
        datas.addAll(g);
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        rvGoods.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).colorResId(R.color.lines).build());
        adapter = new ConfiremGoodsListAdapter(this,datas);
        rvGoods.setAdapter(adapter);
        tvTotal.setText("合计:"+caunltMax());
    }
    private AddressPresenter addressPresenter;
    private AddressBean address;
    /**
     * 获取默认的地址并且显示
     */
    private void getAddress(){
        if(addressPresenter == null){
            addressPresenter = new AddressPresenter(this);
        }
        addressPresenter.getAddressList(new BasePresenter.OnUIThreadListener<List<AddressBean>>() {
            @Override
            public void onResult(List<AddressBean> result) {
                for (AddressBean addressBean:result){
                    if(addressBean.getIs_default().equals("1")){
                        address = addressBean;
                        stvName.setLeftString("收件人:"+addressBean.getName());
                        tvAddress.setText("收货地址:"+addressBean.getProvince()+addressBean.getCity()+addressBean.getDistrict()+addressBean.getDetail());
                        break;
                    }
                }
            }

            @Override
            public void onErro(String string) {

            }
        });
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MarketListPresenter(this);
        return presenter;
    }

    public static void openConfirmOrederActivity(Activity activity ,String data) {
        Intent intent = new Intent(activity, ConfirmOrederActivity.class);
        intent.putExtra("data",data);
        activity.startActivity(intent);
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("确认订单").createAndBind(activityConfirmOreder);
    }
    private long lastShowTime = 0;
    @OnClick({R.id.ll_left, R.id.iv_in, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_left:
                SelectAddressActivity.openSelectAddressActivityForResult(this,REQUESTCODE);
                break;
            case R.id.iv_in:
                SelectAddressActivity.openSelectAddressActivityForResult(this,REQUESTCODE);
                break;
            case R.id.bt_confirm:
                if(tvAddress.getText().length()==5){
                    ToastUtil.showToast(this,"请选填写地址");
                    return;
                }
                if(System.currentTimeMillis() - lastShowTime >=2000){
                    presenter.subOrder(setOrderData(""), new BasePresenter.OnUIThreadListener<String>() {
                        @Override
                        public void onResult(String result) {
                            PayActivity.openPayActivity(ConfirmOrederActivity.this,setOrderData(result));
                        }

                        @Override
                        public void onErro(String string) {
                            showMsg(string);
                        }
                    });
                }
                break;
        }
    }

    /**
     * 计算商品价格
     */
    private float caunltMax(){
        float total = 0;
        for (CarGoods.Good g:datas){
            total = total+Integer.parseInt(g.getNum()) * Float.parseFloat(g.getPrice());
        }
        return  total;
    }



    private OrderResult setOrderData(String orderId) {
        float max = caunltMax();
        OrderResult orderResult = new OrderResult();
        OrderResult.Order order = new OrderResult.Order();
        order.setId(orderId);
        order.setAddress(address.getProvince()+address.getCity()+address.getDistrict()+address.getDetail());
        order.setTel(address.getTel());
        order.setGmoney(max+"");
        order.setOmoney(max+"");
        List<OrderResult.Gitem> gitemList = new ArrayList<>();
        for (CarGoods.Good good:datas){
            OrderResult.Gitem gitem = new OrderResult.Gitem();
            gitem.setFig(good.getImgUrl());
            gitem.setImg(good.getImgUrl());
            gitem.setGid(good.getGid());
            gitem.setName(good.getName());
            gitem.setPrice(good.getPrice());
            gitem.setTprice(String.valueOf(Integer.parseInt(good.getNum()) * Double.parseDouble(good.getPrice())));
            gitem.setNum(String.valueOf(Integer.parseInt(good.getNum())));
            gitemList.add(gitem);
        }
        order.setGitems1(gitemList);
        List<OrderResult.Order> orders = new ArrayList<>();
        orders.add(order);
        orderResult.setData(orders);
        return  orderResult;
    }
}
