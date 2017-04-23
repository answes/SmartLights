package com.bigshark.smartlight.pro.index.view.adapter.viewhold;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.superTextView.setLeftTopString(devices.get(position).getName()+"");
        holder.superTextView.setLeftBottomString(devices.get(position).getAddress()+"");
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        SuperTextView superTextView;
        onItemOnclickListner onItemOnclickListner;
        public MyViewHolder(View itemView,onItemOnclickListner onItemOnclickListner) {
            super(itemView);
            this.onItemOnclickListner = onItemOnclickListner;
            superTextView = (SuperTextView) itemView.findViewById(R.id.device);
        }
    }

    public interface  onItemOnclickListner{
        void onItemClick(int potsion);
    }
}
