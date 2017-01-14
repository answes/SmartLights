package com.bigshark.smartlight.pro.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.bean.Messge;
import com.bigshark.smartlight.utils.VolleyUtils;
import com.bigshark.smartlight.weight.XCRoundRectImageView;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class MessgeListAdapter extends RecyclerView.Adapter<MessgeListAdapter.MyViewHolder> {
    private List<Messge> list;
    private Context context;

    public MessgeListAdapter(Context context, List<Messge> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mine_messge_layout,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.title.setLeftString(list.get(position).getTitle());
        holder.title.setRightString(list.get(position).getDate());
      holder.price.setText(list.get(position).getMessge());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        SuperTextView title;
        TextView price;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (SuperTextView) itemView.findViewById(R.id.stv_title);
            price = (TextView) itemView.findViewById(R.id.tv_messge);
        }
    }
}
