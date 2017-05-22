package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.weight.OrderGoodListView;

import java.util.List;

/**
 * Created by bigShark on 2017/1/20.
 */

public class BaseOrderAdapter extends BaseRecyclerAdapter<BaseOrderAdapter.OrderViewHolder> {
    private Context context;
    private List<OrderResult.Order> datas;
    private ItemOnClickListener itemOnClickListener;

    public BaseOrderAdapter(Context context, List<OrderResult.Order> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public OrderViewHolder getViewHolder(View view) {
        return new OrderViewHolder(view,false,itemOnClickListener);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.item_mine_order_list_layout, parent, false);
        SupportMultipleScreensUtil.scale(v);
        return new OrderViewHolder(v,true,itemOnClickListener);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position, boolean isItem) {
        OrderResult.Order order = datas.get(position);
        holder.btLogistics.setVisibility(View.VISIBLE);
        holder.btReceipt.setVisibility(View.VISIBLE);
        holder.tvNo.setText("订单编号：".concat(order.getOrder_num()));

        //订单状态0:待付款,1:待发货，2:已发货,3:已完成,-1,已取消
        holder.tvState.setTextColor(Color.parseColor("#ee3c57"));
        if ("0".equals(order.getStatus())) {
            holder.tvState.setText("待付款");
            holder.btLogistics.setText("取消");
            holder.btReceipt.setText("付款");
        } else if ("1".equals(order.getStatus())) {
            holder.tvState.setText("待发货");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
        } else if ("2".equals(order.getStatus())) {
            holder.tvState.setText("已发货");
            holder.btLogistics.setText("查看物流");
            holder.btReceipt.setText("收货");
        } else if ("3".equals(order.getStatus())) {
            holder.tvState.setText("已完成");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
        } else if ("-1".equals(order.getStatus())) {
            holder.tvState.setText("已取消");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
            holder.tvState.setTextColor(Color.parseColor("#7D7D7D"));
        }

        if (!"null".equals(order.getGitems())) {
            List<OrderResult.Gitem> gitems = JSONUtil.getObjects(order.getGitems(), OrderResult.Gitem.class);
            if (null != gitems && gitems.size() != 0) {
                holder.ogList.setData(gitems);
                holder.tvPrice.setText("共".concat(String.valueOf(gitems.size())).concat("件商品， 合计：¥ ").concat(order.getOmoney()));
            } else {
                holder.tvPrice.setText("共".concat(String.valueOf(0)).concat("件商品， 合计：¥ ").concat(order.getOmoney()));
            }
        } else {
            holder.tvPrice.setText("共".concat(String.valueOf(1)).concat("件商品， 合计：¥ ").concat(order.getOmoney()));
        }
    }

    @Override
    public int getAdapterItemCount() {
        return this.datas.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemOnClickListener itemOnClickListener;
        private TextView tvNo;
        private TextView tvState;
        private OrderGoodListView ogList;
        private TextView tvPrice;
        private Button btLogistics;
        private Button btReceipt;

        public OrderViewHolder(View itemView,boolean isItem ,final ItemOnClickListener itemOnClickListener) {
            super(itemView);
            this.itemOnClickListener = itemOnClickListener;
            if(isItem) {
                tvNo = (TextView) itemView.findViewById(R.id.tv_no);
                tvState = (TextView) itemView.findViewById(R.id.tv_state);
                ogList = (OrderGoodListView) itemView.findViewById(R.id.og_list);
                tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
                btLogistics = (Button) itemView.findViewById(R.id.bt_logistics);
                btReceipt = (Button) itemView.findViewById(R.id.bt_receipt);
                itemView.setOnClickListener(this);
                btLogistics.setOnClickListener(this);
                btReceipt.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if (itemOnClickListener != null) {
                itemOnClickListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, int postion);
    }
}
