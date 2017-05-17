package com.bigshark.smartlight.pro.mine.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.market.view.PayActivity;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.BaseOrderAdapter;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jlbs1 on 2017/5/16.
 */

public class MyOrderFragment extends BaseFragment {
    private final int QRSH = 0;
    private final int QRQX = 1;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;

    private BaseOrderAdapter adapter;
    private MinePresenter presenter;
    private List<OrderResult.Order> orderDatas = new ArrayList<>();

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_order;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
        initView();
    }

    private void initView() {
        loadDatas(true, getType());

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

        adapter = new BaseOrderAdapter(getActivity(), orderDatas);
        rvContent.setAdapter(adapter);


        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getContext()));

        xrefreshview.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                loadDatas(true, getType());
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                        loadDatas(false, getType());
            }
        });

        adapter.setItemOnClickListener(new BaseOrderAdapter.ItemOnClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                OrderResult orderResult = new OrderResult();
                OrderResult.Order order = orderDatas.get(postion);
                List<OrderResult.Order> orders = new ArrayList<>();
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
                OrderDetailActivity.openOrderDetailActivityForResult(getActivity(), Integer.parseInt(orderDatas.get(postion).getId()));
            }
        });

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
                    loadDatas(true, getType());
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

    int page = 1;

    private void loadDatas(final boolean isDownRefresh, final String type) {
        if(isDownRefresh){
            page = 1;
        }else{
            page++;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://pybike.idc.zhonxing.com/Order/orderlist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        OrderResult orderResult = JSONUtil.getObject(response, OrderResult.class);
                        if (isDownRefresh) {
                            xrefreshview.stopRefresh();
                        } else {
                            xrefreshview.stopLoadMore();
                        }
                        if (null != orderResult) {
                            if (null != orderResult.getData() && orderResult.getData().size() != 0) {
                               if(isDownRefresh){
                                   orderDatas.clear();
                               }
                                orderDatas.addAll(orderResult.getData());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", SmartLightsApplication.USER.getId());
                map.put("p", String.valueOf(page));
                if(!"-1".equals(type)){
                    map.put("status", type);
                }
                return map;
            }
        };
        SmartLightsApplication.queue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDatas(true, getType());
    }

    @Override
    public MVPBasePresenter bindPresenter() {
        presenter = new MinePresenter(getActivity());
        return presenter;
    }
}
