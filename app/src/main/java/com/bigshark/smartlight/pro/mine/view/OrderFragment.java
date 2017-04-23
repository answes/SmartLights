package com.bigshark.smartlight.pro.mine.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.mine.presenter.MinePresenter;
import com.bigshark.smartlight.pro.mine.view.adapter.OrderListAdapter;
import com.bigshark.smartlight.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bigShark on 2017/1/20.
 */

public class OrderFragment extends BaseFragment {
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
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;

    private MinePresenter presenter;
    private OrderListAdapter adapter;
    private List<OrderResult.Order> datas  = new ArrayList<>();

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
        adapter = new OrderListAdapter(getActivity(),datas);
        rvContent.setAdapter(adapter);

        refreshDate(true,type);

    }

    private void showErrorView(){

    }

    private void refreshDate(final boolean isDownRefresh, final int t) {
        presenter.getOrders( isDownRefresh ,String.valueOf(t), new BasePresenter.OnUIThreadListener<OrderResult>() {
            @Override
            public void onResult(OrderResult result) {
                if(null != result){
                    List<OrderResult.Order> orders = result.getData();
                    if(null != orders || orders.size() != 0){
                        if(isDownRefresh){
                            datas.clear();
                            datas.addAll(orders);
                            adapter.notifyDataSetChanged();
                        }else{
                            datas.addAll(orders);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else{
                    ToastUtil.showToast(getContext(),"加载数据失败");
                }
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
