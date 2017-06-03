package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.adapter.viewholder.CarListAdapter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarActivity extends BaseActivity {

    @BindView(R.id.rv_carlist)
    RecyclerView rvCarlist;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.bt_settlement)
    Button btSettlement;
    @BindView(R.id.activity_car)
    RelativeLayout activityCar;
    @BindView(R.id.tv_null)
    TextView isNull;
    @BindView(R.id.cb_AllSelet)
    CheckBox allSelet;

    private CarListAdapter adapter;
    private MarketListPresenter presenter;
    private List<CarGoods.Good> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityCar);
        initData();
    }

    private void initData() {
        setTotal();
        rvCarlist.setLayoutManager(new LinearLayoutManager(this));
        rvCarlist.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());
        adapter = new CarListAdapter(this, datas);
        rvCarlist.setAdapter(adapter);

        presenter.getCarGoodList(new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if (null != result) {
                    CarGoods carGoods = JSONUtil.getObject(result, CarGoods.class);
                    datas.addAll(carGoods.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    isNull.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });

        adapter.setOnClickAddBTListener(new CarListAdapter.OnClickAddBTListener() {
            @Override
            public void onResult(int postion) {
                int num = Integer.parseInt(datas.get(postion).getNum().trim()) + 1;
                upData(postion, 1,num);
            }
        });
        adapter.setOnClickSubBTListener(new CarListAdapter.OnClickSubBTListener() {
            @Override
            public void onResult(int postion) {
                if (Integer.parseInt(datas.get(postion).getNum().trim()) == 1) {
                    delData(postion);
                } else {
                    int num = Integer.parseInt(datas.get(postion).getNum().trim()) - 1;
                    upData(postion, -1,num);
                }
            }
        });

        adapter.setOnClickDelListenr(new CarListAdapter.OnClickDelListenr() {
            @Override
            public void onResult(int postion) {
                delData(postion);
            }
        });

        adapter.setOnCheckBoxListener(new CarListAdapter.OnCheckBoxListener() {
            @Override
            public void onChanged(boolean b, int postion) {
                datas.get(postion).setCheck(b);
                if (isAllSelet()) {
                    allSelet.setChecked(true);
                }
                if (isAllNotSelet()) {
                    allSelet.setChecked(false);
                }
                setTotal();
            }
        });

        allSelet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    for (CarGoods.Good good : datas) {
                        good.setCheck(true);
                    }
                } else {
                    for (CarGoods.Good good : datas) {
                        good.setCheck(false);
                    }
                }
                setTotal();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private boolean isAllSelet() {
        for (CarGoods.Good g : datas) {
            if (!g.isCheck()) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllNotSelet() {
        for (CarGoods.Good g : datas) {
            if (!g.isCheck()) {
                return true;
            }
        }
        return false;
    }

    private void setTotal() {
        double total = 0.00;
        int count = 0;
        if (datas.isEmpty()) {
            tvTotal.setText("合计：￥".concat(String.valueOf(0.00)));
            btSettlement.setText("结算(".concat(String.valueOf(0)).concat(")"));
            return;
        }
        for (CarGoods.Good g : datas) {
            if (g.isCheck()) {
                total = total + Double.parseDouble(g.getPrice().trim()) * Double.parseDouble(g.getNum().trim());
                count = count + 1;
            }
        }

        tvTotal.setText("合计：￥".concat(String.valueOf(total)));
        btSettlement.setText("结算(".concat(String.valueOf(count)).concat(")"));
    }

    private void upData(final int postion, final int num,final int realNumber) {
        datas.get(postion).setNum(String.valueOf(realNumber));
        adapter.notifyItemChanged(postion);
        presenter.updateGoodNum(datas.get(postion).getId(), num, new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if(CarActivity.this.isFinishing()){
                    return;
                }
               if(result == null){
                   showMsg("操作失败");
                   datas.get(postion).setNum(String.valueOf(realNumber+num));
                   adapter.notifyItemChanged(postion);
               }
                setTotal();
            }

            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }

    /**
     * 删除数据
     * @param postion
     */
    private void delData(final int postion) {

        presenter.delGoodToCar(false, datas.get(postion).getId(), new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if(CarActivity.this.isFinishing()){
                    return;
                }
               if(null == result){
                showMsg("删除商品失败");
               }else{
                   datas.remove(postion);
                   adapter.notifyDataSetChanged();
                   if(datas.size() == 0){
                       isNull.setVisibility(View.VISIBLE);
                       allSelet.setChecked(false);
                   }
               }
                setTotal();
            }
            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("购物车").createAndBind(activityCar);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MarketListPresenter(this);
        return presenter;
    }

    private long lastOnclick = 0;
    @OnClick(R.id.bt_settlement)
    public void onClick() {
        if(System.currentTimeMillis() -lastOnclick >=2000) {
            lastOnclick = System.currentTimeMillis();
            Gson gson = new Gson();
            List<CarGoods.Good> data = new ArrayList<>();
            for (CarGoods.Good g : datas) {
                if (g.isCheck()) {
                    data.add(g);
                }
            }
            if(data.size() == 0){
                showMsg("你还没有选择需要的商品");
                return;
            }
            ConfirmOrederActivity.openConfirmOrederActivity(this, gson.toJson(data));
        }
    }

    public static void openCarActivity(Activity activity) {
        activity.startActivity(new Intent(activity, CarActivity.class));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            finish();
        }
    }
}
