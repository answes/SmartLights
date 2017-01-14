package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.utils.VolleyUtils;
import com.bigshark.smartlight.weight.XCRoundRectImageView;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    private List<CarGoods> list;
    private Context context;

    public CarListAdapter(Context context, List<CarGoods> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_car_goods_list_layout,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.title.setLeftString(list.get(position).getName());
      holder.number.setText(list.get(position).getNumber()+"");
      holder.price.setText("价格：￥".concat(list.get(position).getPrice()+""));
        VolleyUtils.loadImage(context,holder.goodImg,list.get(position).getImgUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        SuperTextView title;
        Button add;
        Button sub;
        EditText number;
        XCRoundRectImageView goodImg;
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            add = (Button) itemView.findViewById(R.id.bt_add);
            sub = (Button) itemView.findViewById(R.id.bt_sub);
            title = (SuperTextView) itemView.findViewById(R.id.stv_title);
            number = (EditText) itemView.findViewById(R.id.et_number);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            goodImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
