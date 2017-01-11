package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class CarListAdapter extends BaseAdapter {
    private List<CarGoods> list;
    private Context context;

    public CarListAdapter(Context context, List<CarGoods> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_market_miunte_layout, viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.add = (Button) view.findViewById(R.id.bt_add);
            viewHolder.sub = (Button) view.findViewById(R.id.bt_sub);
            viewHolder.title = (SuperTextView) view.findViewById(R.id.stv_title);
            viewHolder.number = (EditText) view.findViewById(R.id.et_number);
            viewHolder.price = (TextView) view.findViewById(R.id.tv_price);
            viewHolder.goodImg = (ImageView) view.findViewById(R.id.iv_img);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setLeftString(list.get(i).getName());
        viewHolder.number.setText(list.get(i).getNumber());
        viewHolder.price.setText("价格：￥".concat(list.get(i).getPrice()+""));
        //viewHolder.goodImg
        return view;
    }

    class ViewHolder {
        SuperTextView title;
        Button add;
        Button sub;
        EditText number;
        ImageView goodImg;
        TextView price;
    }
}
