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

public class OrderListAdapter extends BaseRecyclerAdapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private List<OrderResult.Order> datas;
    private ItemOnClickListener itemOnClickListener;

    public OrderListAdapter(Context context, List<OrderResult.Order> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view, false, itemOnClickListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.item_mine_order_list_layout, parent, false);
        SupportMultipleScreensUtil.scale(v);
        ViewHolder viewHolder = new ViewHolder(v, true, itemOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        OrderResult.Order order = datas.get(position);
       // holder.stvNo.setLeftString("订单编号：".concat(order.getOrderNum()));
        //订单状态0:待付款,1:待发货，2:已发货,3:已完成,-1,已取消
        holder.stvNo.setRightTVColor(Color.parseColor("#ee3c57"));
        if("0".equals(order.getStatus())){
            holder.stvNo.setRightString("待付款");
            holder.btLogistics.setText("取消");
            holder.btReceipt.setText("付款");
        }else if("1".equals(order.getStatus())){
            holder.stvNo.setRightString("待发货");
            holder.btLogistics.setText("查看物流");
            holder.btReceipt.setText("收货");
        }else if("2".equals(order.getStatus())){
            holder.stvNo.setRightString("已发货");
            holder.btLogistics.setText("查看物流");
            holder.btReceipt.setText("收货");
        }else if("3".equals(order.getStatus())){
            holder.stvNo.setRightString("已完成");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
        }else if("-1".equals(order.getStatus())){
            holder.stvNo.setRightString("已取消");
            holder.btLogistics.setVisibility(View.GONE);
            holder.btReceipt.setVisibility(View.GONE);
            holder.stvNo.setRightTVColor(Color.parseColor("#7D7D7D"));
        }

        if(null != order.getGitems()){
            List<OrderResult.Gitem> gitems = JSONUtil.getObjects(order.getGitems(),OrderResult.Gitem.class);
            if(null != gitems || gitems.size() != 0) {
                holder.ogList.setData(gitems);
                holder.tvPrice.setText("共".concat(String.valueOf(gitems.size())).concat("件商品， 合计：¥ ").concat(order.getOmoney()));
            }
        }


    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    @Override
    public int getAdapterItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemOnClickListener itemOnClickListener;
        private SuperTextView stvNo;
        private OrderGoodListView ogList;
        private TextView tvPrice;
        private Button btLogistics;
        private Button btReceipt;

        public ViewHolder(View itemView, boolean isItem, ItemOnClickListener itemOnClickListener) {
            super(itemView);
            this.itemOnClickListener = itemOnClickListener;
            if (isItem) {
                stvNo = (SuperTextView) itemView.findViewById(R.id.stv_no);
                ogList = (OrderGoodListView) itemView.findViewById(R.id.og_list);
                tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
                btLogistics = (Button) itemView.findViewById(R.id.bt_logistics);
                btReceipt = (Button)itemView.findViewById(R.id.bt_receipt);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            if(itemOnClickListener != null){
                itemOnClickListener.onItemClick(view,getPosition());
            }
        }
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, int postion);
    }
}
