package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.VolleyUtils;
import com.bigshark.smartlight.weight.XCRoundRectImageView;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class ConfiremGoodsListAdapter extends RecyclerView.Adapter<ConfiremGoodsListAdapter.MyViewHolder> {
    private List<CarGoods.Good> list;
    private Context context;

    public ConfiremGoodsListAdapter(Context context, List<CarGoods.Good> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_car_confirm_goods_list_layout,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.name.setText(list.get(position).getName());
        VolleyUtils.loadImage(context,holder.goodImg,list.get(position).getImgUrl());
        holder.details.setText("颜色：".concat("null").concat("\n数量: ".concat(list.get(position).getNum())));
        holder.price.setText("价格：￥".concat(list.get(position).getPrice()));
        VolleyUtils.loadImage(context,holder.goodImg,list.get(position).getImgUrl());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView details;
        XCRoundRectImageView goodImg;
        TextView price;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            SupportMultipleScreensUtil.scale(itemView);
            name= (TextView) itemView.findViewById(R.id.tv_name);
            details = (TextView) itemView.findViewById(R.id.stv_details);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            goodImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
