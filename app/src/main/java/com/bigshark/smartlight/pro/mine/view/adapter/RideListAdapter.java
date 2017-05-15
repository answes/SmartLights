package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Ride;
import com.bigshark.smartlight.utils.DateFomat;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class RideListAdapter extends RecyclerView.Adapter<RideListAdapter.MyViewHolder> {
    private List<Ride.Bike>  list;
    private Context context;

    public RideListAdapter(Context context, List<Ride.Bike> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_ride_list_layout,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Double cny = Double.parseDouble(list.get(position).getDistance());//转换成Double
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        holder.distance.setText(df.format(cny));
        holder.time.setText(DateFomat.convertSecond2DateSS(list.get(position).getCre_tm()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView distance;
        TextView time;
        public MyViewHolder(View itemView) {
            super(itemView);
            SupportMultipleScreensUtil.scale(itemView);
            distance = (TextView) itemView.findViewById(R.id.tv_distance);
            time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
