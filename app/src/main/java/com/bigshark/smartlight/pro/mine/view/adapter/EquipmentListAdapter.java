package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Equipment;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class EquipmentListAdapter extends RecyclerView.Adapter<EquipmentListAdapter.MyViewHolder> {
    private List<Equipment> list;
    private Context context;

    public EquipmentListAdapter(Context context, List<Equipment> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_equipment_list_layout,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText("设备名称：".concat(list.get(position).getName()));
        holder.numbering.setText("设备编号：".concat(list.get(position).getNumbering()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView numbering;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            SupportMultipleScreensUtil.scale(itemView);
            name= (TextView) itemView.findViewById(R.id.tv_name);
            numbering = (TextView) itemView.findViewById(R.id.tv_numbering);
        }
    }
}
