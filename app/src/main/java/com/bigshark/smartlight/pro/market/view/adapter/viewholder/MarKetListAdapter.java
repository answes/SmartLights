package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.VolleyUtils;

import java.util.List;

/**
 * Created by bigShark on 2017/1/20.
 */

public class MarKetListAdapter extends BaseRecyclerAdapter<MarKetListAdapter.ViewHolder> {
    private Context context;
    private List<Market.Goods> datas;
    private ItemOnClickListener itemOnClickListener;

    public MarKetListAdapter(Context context, List<Market.Goods> datas,int itemHeight) {
        this.context = context;
        this.datas = datas;
        this.itemHeight = itemHeight;
    }

    public MarKetListAdapter(Context context, List<Market.Goods> datas) {
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
                R.layout.item_market_miunte_layout, parent, false);
        SupportMultipleScreensUtil.scale(v);
        if(itemHeight != - 1) {
            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = itemHeight;
            v.setLayoutParams(params);
        }
        ViewHolder viewHolder = new ViewHolder(v, true, itemOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {
        Market.Goods market = datas.get(position);
        holder.title.setText(market.getName());
        VolleyUtils.loadImage(context,holder.ivMiunte,market.getImg());
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
        private NetworkImageView ivMiunte;
        private TextView title;

        public ViewHolder(View itemView, boolean isItem, ItemOnClickListener itemOnClickListener) {
            super(itemView);
            this.itemOnClickListener = itemOnClickListener;
            if (isItem) {
                ivMiunte = (NetworkImageView) itemView.findViewById(R.id.iv_miunte_img);
                title = (TextView) itemView.findViewById(R.id.tv_miunte_name);
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

    private int itemHeight = -1;
    public  void setItemHeight(int itemHeight){
        this.itemHeight = itemHeight;
    }


    public interface ItemOnClickListener {
        void onItemClick(View view, int postion);
    }
}
