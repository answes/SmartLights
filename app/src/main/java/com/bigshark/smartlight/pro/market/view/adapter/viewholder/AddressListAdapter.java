package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Address;
import com.bigshark.smartlight.bean.AddressBean;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import java.util.List;

/**
 * Created by luyanhong on 16/9/28.
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {
    private List<AddressBean> list;
    private Context context;

    private OnEditClickLinstener onEditClickLinstener;
    private OnDelClickLinstener onDelClickLinstener;
    private OnCheckBoxLinstener onCheckBoxLinstener;
    private OnDefaultClickLinstenr onDefaultClickLinstenr;


    public void setOnEditClickLinstener(OnEditClickLinstener onEditClickLinstener) {
        this.onEditClickLinstener = onEditClickLinstener;
    }

    public void setOnDelClickLinstener(OnDelClickLinstener onDelClickLinstener) {
        this.onDelClickLinstener = onDelClickLinstener;
    }

    public void setOnCheckBoxLinstener(OnCheckBoxLinstener onCheckBoxLinstener) {
        this.onCheckBoxLinstener = onCheckBoxLinstener;
    }

    public void setOnDefaultClickLinstenr(OnDefaultClickLinstenr onDefaultClickLinstenr){
        this.onDefaultClickLinstenr = onDefaultClickLinstenr;
    }

    public AddressListAdapter(Context context, List<AddressBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_address_list_layout, parent, false);
        SupportMultipleScreensUtil.scale(view);
        MyViewHolder myViewHolder = new MyViewHolder(view, onEditClickLinstener, onDelClickLinstener, onCheckBoxLinstener,onDefaultClickLinstenr);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AddressBean address = list.get(position);
        holder.name.setLeftString(address.getName());
        holder.name.setRightString(address.getTel());
        holder.address.setText(address.getProvince().concat(address.getCity()).concat(address.getDistrict()).concat(address.getDetail()));
        if (address.getIs_default()!= null && address.getIs_default().equals("1")) {
            holder.dftAds.setTextColor(context.getResources().getColor(R.color.red));
            holder.dftAds.setText("默认地址");
        } else {
            holder.dftAds.setTextColor(context.getResources().getColor(R.color.text_fist));
            holder.dftAds.setText("设置为默认地址");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        SuperTextView name;
        CheckBox select;
        TextView dftAds;
        TextView address;
        Button edit;
        Button del;
        OnEditClickLinstener onEditClickLinstener;
        OnDelClickLinstener onDelClickLinstener;
        OnCheckBoxLinstener onCheckBoxLinstener;
        OnDefaultClickLinstenr onDefaultClickLinstenr;

        public MyViewHolder(View itemView, final OnEditClickLinstener onEditClickLinstener,
                            final OnDelClickLinstener onDelClickLinstener, final OnCheckBoxLinstener onCheckBoxLinstener, final OnDefaultClickLinstenr onDefaultClickLinstenr) {
            super(itemView);
            this.onCheckBoxLinstener = onCheckBoxLinstener;
            this.onDelClickLinstener = onDelClickLinstener;
            this.onEditClickLinstener = onEditClickLinstener;
            this.onDefaultClickLinstenr = onDefaultClickLinstenr;

            name = (SuperTextView) itemView.findViewById(R.id.stv_name);
            select = (CheckBox) itemView.findViewById(R.id.cb_select);
            dftAds = (TextView) itemView.findViewById(R.id.tv_defAds);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            edit = (Button) itemView.findViewById(R.id.bt_edit);
            del = (Button) itemView.findViewById(R.id.bt_del);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditClickLinstener.onReslut(getLayoutPosition());
                }
            });

            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDelClickLinstener.onReslut(getLayoutPosition());
                }
            });

            dftAds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDefaultClickLinstenr.onResult(getLayoutPosition());
                }
            });

            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    onCheckBoxLinstener.onResult(getLayoutPosition(), b);
                }
            });

        }

    }

    public interface OnEditClickLinstener {
        void onReslut(int postion);
    }

    public interface OnDelClickLinstener {
        void onReslut(int postion);
    }

    public interface OnCheckBoxLinstener {
        void onResult(int postion, boolean b);
    }

    public interface OnDefaultClickLinstenr {
        void onResult(int postion);
    }
}
