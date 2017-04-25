package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.CarGoods;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.VolleyUtils;
import com.bigshark.smartlight.weight.XCRoundRectImageView;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {
    private List<CarGoods.Good> list;
    private Context context;
    private OnClickAddBTListener onClickAddBTListener;
    private OnClickSubBTListener onClickSubBTListener ;
    private OnClickDelListenr onClickDelListenr;
    private OnCheckBoxListener onCheckBoxListener;

    public void setOnClickAddBTListener(OnClickAddBTListener onClickAddBTListener){
        this.onClickAddBTListener = onClickAddBTListener;
    }

    public  void setOnClickSubBTListener (OnClickSubBTListener onClickSubBTListener){
        this.onClickSubBTListener = onClickSubBTListener;
    }
    public  void setOnClickDelListenr ( OnClickDelListenr onClickDelListenr){
        this.onClickDelListenr = onClickDelListenr;
    }

    public  void setOnCheckBoxListener (OnCheckBoxListener onCheckBoxListener){
        this.onCheckBoxListener = onCheckBoxListener;
    }

    public CarListAdapter(Context context, List<CarGoods.Good> list){
        this.list = list;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_goods_list_layout,parent,false);
        SupportMultipleScreensUtil.scale(view); //这里进行缩放适配
        MyViewHolder myViewHolder = new MyViewHolder(view, onClickAddBTListener, onClickSubBTListener,onClickDelListenr,onCheckBoxListener);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.title.setText(list.get(position).getName());
      holder.number.setText(list.get(position).getNum());
      holder.price.setText("价格：￥".concat(list.get(position).getPrice()));
        VolleyUtils.loadImage(context,holder.goodImg,list.get(position).getImgUrl());
        if(list.get(position).isCheck()){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        TextView title;
        Button add;
        Button sub;
        EditText number;
        XCRoundRectImageView goodImg;
        TextView price;
        CheckBox checkBox;
        ImageView del;

        OnClickAddBTListener onClickAddBTListener;
        OnClickSubBTListener onClickSubBTListener;
        OnClickDelListenr onClickDelListenr;
        OnCheckBoxListener onCheckBoxListener;

        public MyViewHolder(View itemView , OnClickAddBTListener onClickAddBTListener,
                OnClickSubBTListener onClickSubBTListener,final OnClickDelListenr onClickDelListenr,OnCheckBoxListener onCheckBoxListener) {
            super(itemView);

            this.onClickAddBTListener = onClickAddBTListener;
            this.onClickSubBTListener = onClickSubBTListener;
            this.onClickDelListenr= onClickDelListenr;
            this.onCheckBoxListener = onCheckBoxListener;

            add = (Button) itemView.findViewById(R.id.bt_add);
            sub = (Button) itemView.findViewById(R.id.bt_sub);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            number = (EditText) itemView.findViewById(R.id.et_number);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_select);
            goodImg = (XCRoundRectImageView) itemView.findViewById(R.id.iv_img);
            del = (ImageView)itemView.findViewById(R.id.iv_del);

            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            del.setOnClickListener(this);
            checkBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.bt_add:
                    onClickAddBTListener.onResult(getLayoutPosition());
                    break;
                case R.id.bt_sub:
                    onClickSubBTListener.onResult(getLayoutPosition());
                    break;
                case R.id.iv_del:
                    onClickDelListenr.onResult(getLayoutPosition());
                    break;
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            onCheckBoxListener.onChanged(b,getLayoutPosition());
        }
    }


    public interface OnClickAddBTListener{
        void onResult(int postion);
    }

    public interface OnClickSubBTListener{
        void onResult(int postion);
    }

    public interface  OnClickDelListenr{
        void onResult(int postion);
    }

    public interface OnCheckBoxListener{
        void onChanged(boolean b,int postion);
    }

}
