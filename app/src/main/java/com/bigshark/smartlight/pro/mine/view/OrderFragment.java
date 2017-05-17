package com.bigshark.smartlight.pro.mine.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.BaseOrderAdapter;
import com.bigshark.smartlight.utils.ToastUtil;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;

    private MinePresenter presenter;
    private BaseOrderAdapter adapter;
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

        xrefreshview.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xrefreshview.setPullLoadEnable(true);
        // 设置刷新完成以后，headerview固定的时间
        xrefreshview.setPinnedTime(1000);
        // 静默加载模式不能设置footerview
        // 设置支持自动刷新
        xrefreshview.setAutoLoadMore(true);
        //设置静默加载时提前加载的item个数
//		xRefreshView.setPreLoadCount(2);

        rvContent.setHasFixedSize(true);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BaseOrderAdapter(getActivity(), datas);
        rvContent.setAdapter(adapter);

        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));

        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                ToastUtil.showToast(getContext(), "刷新");
                refreshDate(true, getType());
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                ToastUtil.showToast(getContext(), "更多");
                refreshDate(false, getType());
            }
        });
        //refreshDate(true, getType());


//        adapter.setItemOnClickListener(new OrderListAdapter.ItemOnClickListener() {
//            @Override
//            public void onItemClick(View view, int postion) {
//                OrderResult orderResult = new OrderResult();
//                OrderResult.Order order = datas.get(postion);
//                List<OrderResult.Order> orders = new ArrayList<OrderResult.Order>();
//                orders.add(order);
//                switch (view.getId()) {
//                    case R.id.bt_logistics:
//                        if ("0".equals(order.getStatus())) {
//                            showDialog("是否取消该订单", QRQX,order);
//                        } else if ("1".equals(order.getStatus()) || "2".equals(order.getStatus())) {
//                            //查看物流。
//                            ToastUtil.showToast(getContext(), "暂时无法查看物流");
//                        }
//                        return;
//                    case R.id.bt_receipt:
//                        if ("0".equals(order.getStatus())) {
//                            orderResult.setData(orders);
//                            PayActivity.openPayActivity(getActivity(), orderResult);
//                        } else if ("1".equals(order.getStatus()) || "2".equals(order.getStatus())) {
//                            showDialog("是否确认收到货", QRSH,order);
//                        }
//                        return;
//
//                }
//                OrderDetailActivity.openOrderDetailActivityForResult(getActivity(), Integer.parseInt(datas.get(postion).getId()));
//            }
//        });


    }

    private void showDialog(String message, final int type, final OrderResult.Order order) {
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
            public void onResult(final OrderResult result) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isDownRefresh) {
                            xrefreshview.stopRefresh();
                        } else {
                            xrefreshview.stopLoadMore();
                        }
                        if (result == null) {
                            ToastUtil.showToast(getContext(), "加载失败");
                        } else {
                            ToastUtil.showToast(getContext(), "数据有 "+result.getData().size());
                            if (isDownRefresh) {
                                datas.clear();
                            }
                            datas.addAll(result.getData());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, 1000);
            }

            @Override
            public void onErro(String string) {

            }
        });
    }

    @Override
    public MVPBasePresenter bindPresenter() {
        presenter = new MinePresenter(getActivity());
        return presenter;
    }
}
