package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderDetailResult;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.DateFomat;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.OrderGoodListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bigshark.smartlight.R.id.tv_details;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity {
    private final int QRSH = 0;
    private final int QRQX = 1;

    @BindView(R.id.activity_order)
    RelativeLayout activityOrder;
    @BindView(R.id.stv_name)
    SuperTextView stvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.stv_no)
    SuperTextView stvNo;
    @BindView(R.id.og_list)
    OrderGoodListView ogList;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(tv_details)
    TextView tvDetails;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.bt_cancel)
    Button btCanCel;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private MinePresenter presenter;
    private int id,type;
    private OrderDetailResult.OrderDetail order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityOrder);
        getIntentData();
    }


    //订单状态0:待付款,1:待发货，2:已发货,3:已完成,-1,已取消

    private void getIntentData() {
        id = getIntent().getIntExtra("id", 0);
        getOrder();
    }

    private void getOrder() {
        presenter.getOrderDetail(id, new BasePresenter.OnUIThreadListener<OrderDetailResult.OrderDetail>() {
            @Override
            public void onResult(OrderDetailResult.OrderDetail result) {
                if (null != result) {
                    order = result;
                    setData();
                } else {
                    showMsg("加载出错!");
                }
            }

            @Override
            public void onErro(String string) {

            }
        });
    }

    private void setData() {
        type = Integer.parseInt(order.getStatus());
        btCanCel.setVisibility(View.GONE);
        if (type == 0) {
            btConfirm.setText("待付款");
            setState("#ee3c57", "待付款");
            btCanCel.setVisibility(View.VISIBLE);
            tvDetails.setText("订单编号：".concat(order.getOrder_num()).concat("\n创建时间：").concat(DateFomat.convertSecond2DateSS(order.getCre_tm())));
        }
        if (type == 1) {
            btConfirm.setText("待发货");
            setState("#ee3c57", "待发货");
            tvDetails.setText("订单编号：".concat(order.getOrder_num()).concat("\n创建时间：")
                    .concat(DateFomat.convertSecond2DateSS(order.getCre_tm()))
                    .concat("\n付款时间：").concat(DateFomat.convertSecond2DateSS(order.getPay_tm())));
        }
        if (type == 2) {
            btConfirm.setText("已发货");
            setState("#ee3c57", "已发货");
            tvDetails.setText("订单编号：".concat(order.getOrder_num()).concat("\n创建时间：")
                    .concat(DateFomat.convertSecond2DateSS(order.getCre_tm()))
                    .concat("\n付款时间：").concat(DateFomat.convertSecond2DateSS(order.getPay_tm()))
                    .concat("\n发货时间：").concat(DateFomat.convertSecond2DateSS(order.getSend_tm()))
                    .concat("\n快递公司：").concat(order.getExp_com())
                    .concat("\n快递单号：").concat(order.getExp_num()));
        }
        if (type == 3) {
            setState("#3d444a", "已完成");

            tvDetails.setText("订单编号：".concat(order.getOrder_num()).concat("\n创建时间：")
                    .concat(DateFomat.convertSecond2DateSS(order.getCre_tm()))
                    .concat("\n付款时间：").concat(DateFomat.convertSecond2DateSS(order.getPay_tm()))
                    .concat("\n发货时间：").concat(DateFomat.convertSecond2DateSS(order.getSend_tm()))
                    .concat("\n快递公司：").concat(order.getExp_com())
                    .concat("\n快递单号：").concat(order.getExp_num())
                    .concat("\n完成时间：").concat(DateFomat.convertSecond2DateSS(order.getFinish_tm())));
        }
        if (type == -1) {
            setState("#3d444a", "已取消");
            llBottom.setVisibility(View.GONE);
//            tvDetails.setText("订单编号：".concat(order.getOrder_num()).concat("\\n创建时间：")
//                    .concat(DateFomat.convertSecond2DateSS(order.getCre_tm()))
//                    .concat("\\n付款时间：").concat(DateFomat.convertSecond2DateSS(order.getPay_tm()))
//                    .concat("\\n发货时间：").concat(DateFomat.convertSecond2DateSS(order.getSend_tm()))
//                    .concat("\\n快递公司：").concat(order.getExp_com())
//                    . concat("\\n快递单号：").concat(order.getExp_num())
//                    .concat("\\n取消时间：").concat(DateFomat.convertSecond2DateSS(order.getFinish_tm())));
        }
        List<OrderResult.Gitem> goods = new ArrayList<>();
        goods.clear();
        if(!"null".equals(order.getGitems())){
            goods.addAll(JSONUtil.getObjects(order.getGitems(), OrderResult.Gitem.class));
            if (null != goods && goods.size() != 0) {
                ogList.setData(goods);
            }
        }
        stvName.setLeftString(order.getUsername());
        stvName.setRightString(order.getTel());
        tvAddress.setText("收货地址:".concat(order.getAddress()));
        stvNo.setLeftString("订单编号：".concat(order.getOrder_num()));
        tvTotalPrice.setText("商品总价：".concat(order.getGmoney()));
        tvPrice.setText("共".concat(String.valueOf(goods.size())).concat("件商品， 合计：¥ ").concat(order.getOmoney()));
    }

    private void setState(String color, String state) {
        stvNo.setRightString(state);
        stvNo.setRightTVColor(Color.parseColor(color));
    }

    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setTitle("订单详情")
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                })
                .createAndBind(activityOrder);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MinePresenter(this);
        return presenter;
    }


    /**
     * @param statu   1：款之后，-1：取消订单，3：确认收货
     * @param payType 1：支付宝，2：微信
     */
    private void upOrderStatu(int statu, int payType) {
        presenter.upOrderStatu(Integer.parseInt(order.getId()), statu, payType, new BasePresenter.OnUIThreadListener<Integer>() {
            @Override
            public void onResult(Integer result) {
                if (result == 1) {
                    showMsg("成功");
                    getOrder();
                } else {
                    showMsg("失败");
                }

            }

            @Override
            public void onErro(String string) {

            }
        });
    }

    public static void openOrderDetailActivityForResult(Activity activity, int id) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivityForResult(intent, 0x001);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OrderDetailActivity.this.setResult(RESULT_OK);
    }

    @OnClick({R.id.bt_cancel, R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                showDialog("是否取消订单",QRQX);
                break;
            case R.id.bt_confirm:
                switch (type) {
                    case 0:
                        showMsg("缺少接口");
//                        OrderResult orderResult = new OrderResult();
//                        OrderResult.Order order = ;
//                        List<OrderResult.Order> orders = new ArrayList<OrderResult.Order>();
//                        orders.add(order);
//                        orderResult.setData(orders);
//                        PayActivity.openPayActivity(getActivity(), orderResult);
                        break;
                    case 2:
                        showDialog("是否确认收到货",QRSH);
                        break;
                }
                break;
        }
    }

    private void showDialog(String message, final int t) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (t == QRQX) {
                            upOrderStatu(-1,1);
                        } else if (t == QRSH) {
                            upOrderStatu(3,1);
                        }
                    }
                });
        alertDialog.show();
    }
}
