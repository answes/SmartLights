package com.bigshark.smartlight.pro.index.view.adapter.viewhold;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bigshark.smartlight.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2017/4/17.
 *
 * @email 869360026@qq.com
 */

public class BluetoothScanAdapter extends RecyclerView.Adapter <BluetoothScanAdapter.MyViewHolder>{

    private List<BluetoothDevice> devices = new ArrayList<>();

    private onItemOnclickListner onItemOnclickListner;

    private Context context;

    public BluetoothScanAdapter setOnItemOnclickListener(onItemOnclickListner onItemOnclickListner){
        this.onItemOnclickListner  = onItemOnclickListner;
        return  this;
    }

    public BluetoothScanAdapter(Context context,List<BluetoothDevice> devices){
        this.context = context;
        this.devices = devices;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,null);
        MyViewHolder viewHolder = new MyViewHolder(view,onItemOnclickListner);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        BluetoothDevice device = devices.get(position);
        holder.equName.setText(device.getName()+"");
//        holder.superTextView.setLeftBottomString(devices.get(position).getAddress()+"");
//        holder.superTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.onItemOnclickListner.onItemClick(position);
//            }
//        });
        holder.link.setText("连接");
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        //SuperTextView superTextView;
        TextView equName;
        Button link;
        onItemOnclickListner onItemOnclickListner;
        public MyViewHolder(View itemView,onItemOnclickListner onItemOnclickListner) {
            super(itemView);
            this.onItemOnclickListner = onItemOnclickListner;
//            superTextView = (SuperTextView) itemView.findViewById(R.id.device);
            equName = (TextView)itemView.findViewById(R.id.tv_equname);
            link = (Button) itemView.findViewById(R.id.bt_link);
            link.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(null != onItemOnclickListner){
                onItemOnclickListner.onItemClick(getPosition());
            }
        }
    }

    public interface  onItemOnclickListner{
        void onItemClick(int potsion);
    }
}
