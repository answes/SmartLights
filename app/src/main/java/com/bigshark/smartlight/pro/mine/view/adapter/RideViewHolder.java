package com.bigshark.smartlight.pro.mine.view.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.bigshark.smartlight.bean.Ride;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by bigShark on 2016/12/28.
 */

public class RideViewHolder extends BaseViewHolder<Ride> {

    private TextView distance;
    private TextView time;

    public RideViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_mine_ride_list_layout);
        distance = $(R.id.tv_distance);
        time = $(R.id.tv_time);
    }

    @Override
    public void setData(Ride data) {
        super.setData(data);
        distance.setText(data.getData().get(0).getDistance()+"");
        time.setText(data.getData().get(0).getCre_tm());
    }
}
