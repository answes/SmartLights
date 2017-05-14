package com.bigshark.smartlight.pro.mine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.market.view.PayActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.OrderListAdapter;
import com.bigshark.smartlight.utils.ToastUtil;
import com.yalantis.ucrop.dialog.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by bigShark on 2017/1/20.
 */

public class OrderFragment extends BaseFragment {
    private final int QRSH = 0;
    private final int QRQX = 1;
    private int type = 0;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
//    @BindView(R.id.xrefreshview)
//    XRefreshView xrefreshview;

    private SweetAlertDialog dialog;
    private MinePresenter presenter;
    private OrderListAdapter adapter;
    private List<OrderResult.Order> datas = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_order;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
    }

    @Override
    public void initData() {
        super.initData();
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderListAdapter(getActivity(), datas);
        rvContent.setAdapter(adapter);

        adapter.setItemOnClickListener(new OrderListAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                OrderResult orderResult = new OrderResult();
                OrderResult.Order order = datas.get(postion);
                List<OrderResult.Order> orders = new ArrayList<OrderResult.Order>();
                orders.add(order);
                switch (view.getId()) {
                    case R.id.bt_logistics:
                        if ("0".equals(order.getStatus())) {
                            showDialog("是否取消该订单", QRQX,order);
                        } else if ("1".equals(order.getStatus()) || "2".equals(order.getStatus())) {
                            //查看物流。
                            ToastUtil.showToast(getContext(), "暂时无法查看物流");
                        }
                        return;
                    case R.id.bt_receipt:
                        if ("0".equals(order.getStatus())) {
                            orderResult.setData(orders);
                            PayActivity.openPayActivity(getActivity(), orderResult);
                        } else if ("1".equals(order.getStatus()) || "2".equals(order.getStatus())) {
                            showDialog("是否确认收到货", QRSH,order);
                        }
                        return;

                }
                OrderDetailActivity.openOrderDetailActivityForResult(getActivity(), Integer.parseInt(datas.get(postion).getId()));
            }
        });

        refreshDate(true, getType());

        dialog = new SweetAlertDialog(getActivity());
        dialog.setTitleText("查询中...");
        dialog.show();

    }

    private void showDialog(String message, final int type,final OrderResult.Order order) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity()).setMessage(message)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (type == QRQX) {
                            upOrderStatu(order.getId(), -1, 1);
                        } else if (type == QRSH) {
                            upOrderStatu(order.getId(), 3, 1);
                        }
                    }
                });
        alertDialog.show();
    }

    /**
     * @param statu   1：款之后，-1：取消订单，3：确认收货
     * @param payType 1：支付宝，2：微信
     */
    private void upOrderStatu(String orderid, int statu, int payType) {
        presenter.upOrderStatu(Integer.parseInt(orderid), statu, payType, new BasePresenter.OnUIThreadListener<Integer>() {
            @Override
            public void onResult(Integer result) {
                if (result == 1) {
                    refreshDate(true, getType());
                    ToastUtil.showToast(getActivity(), "修改成功");
                } else {
                    ToastUtil.showToast(getActivity(), "修改失败");
                }

            }
            @Override
            public void onErro(String string) {
            }
        });
    }

    private void showErrorView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            refreshDate(true, getType());
        }
    }

    private void refreshDate(final boolean isDownRefresh, int t) {
        presenter.getOrders(isDownRefresh, String.valueOf(t), new BasePresenter.OnUIThreadListener<OrderResult>() {
            @Override
            public void onResult(OrderResult result) {
                if (null != result) {
                    List<OrderResult.Order> orders = result.getData();
                    if (null != orders || orders.size() != 0) {
                        if (isDownRefresh) {
                            datas.clear();
                        }
                        datas.addAll(orders);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtil.showToast(getContext(), "加载数据失败");
                }
                dialog.dismiss();
            }

            @Override
            public void onErro(String string) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public MVPBasePresenter bindPresenter() {
        presenter = new MinePresenter(getActivity());
        return presenter;
    }
}
