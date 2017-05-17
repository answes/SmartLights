package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.utils.DateFomat;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class RideListAdapter extends BaseRecyclerAdapter<RideListAdapter.MyViewHolder> {
    private List<Ride.Bike>  list;
    private Context context;
    private ItemOnClickListener itemOnClickListener;

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public RideListAdapter(Context context, List<Ride.Bike> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder getViewHolder(View view) {
        return new MyViewHolder(view,false,itemOnClickListener);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_ride_list_layout,parent,false),true,itemOnClickListener);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, boolean isItem) {
        Double cny = Double.parseDouble(list.get(position).getDistance());//转换成Double
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        holder.distance.setText(df.format(cny));
        holder.time.setText(DateFomat.convertSecond2DateSS(list.get(position).getCre_tm()));
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView distance;
        TextView time;
        private ItemOnClickListener itemOnClickListener;
        public MyViewHolder(View itemView,boolean is,ItemOnClickListener itemOnClickListener) {
            super(itemView);
            SupportMultipleScreensUtil.scale(itemView);
            if(is){
                this.itemOnClickListener = itemOnClickListener;
                distance = (TextView) itemView.findViewById(R.id.tv_distance);
                time = (TextView) itemView.findViewById(R.id.tv_time);
                itemView.setOnClickListener(this);
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
