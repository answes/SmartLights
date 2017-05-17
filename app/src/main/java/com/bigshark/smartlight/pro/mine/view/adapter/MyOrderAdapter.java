package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.utils.JSONUtil;
import com.bigshark.smartlight.weight.OrderGoodListView;

import java.util.List;

/**
 * Created by jlbs1 on 2017/5/16.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder>{

    private Context context;
    private  List<OrderResult.Order> datas;

    public MyOrderAdapter(Context context, List<OrderResult.Order> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_order_list_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, int position) {
        OrderResult.Order order = datas.get(position);
        holder.btLogistics.setVisibility(View.VISIBLE);
        holder.btReceipt.setVisibility(View.VISIBLE);
        holder.stvNo.setLeftString("订单编号：".concat(order.getOrder_num()));

        //订单状态0:待付款,1:待发货，2:已发货,3:已完成,-1,已取消
        holder.stvNo.setRightTVColor(Color.parseColor("#ee3c57"));
        if ("0".equals(order.getStatus())) {
            holder.stvNo.setRightString("待付款");
            holder.btLogistics.setText("取消");
            holder.btReceipt.setText("付款");
        } else if ("1".equals(order.getStatus())) {
            holder.stvNo.setRightString("待发货");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setText("收货");
        } else if ("2".equals(order.getStatus())) {
            holder.stvNo.setRightString("已发货");
            holder.btLogistics.setText("查看物流");
            holder.btReceipt.setText("收货");
        } else if ("3".equals(order.getStatus())) {
            holder.stvNo.setRightString("已完成");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
        } else if ("-1".equals(order.getStatus())) {
            holder.stvNo.setRightString("已取消");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
            holder.stvNo.setRightTVColor(Color.parseColor("#7D7D7D"));
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
    public int getItemCount() {
        return datas.size();
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder{

        private SuperTextView stvNo;
        private OrderGoodListView ogList;
        private TextView tvPrice;
        private Button btLogistics;
        private Button btReceipt;

        public MyOrderViewHolder(View itemView) {
            super(itemView);
            stvNo = (SuperTextView) itemView.findViewById(R.id.stv_no);
            ogList = (OrderGoodListView) itemView.findViewById(R.id.og_list);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            btLogistics = (Button) itemView.findViewById(R.id.bt_logistics);
            btReceipt = (Button) itemView.findViewById(R.id.bt_receipt);
        }
    }
}
