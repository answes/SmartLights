package com.bigshark.smartlight.pro.market.view.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.Market;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by bigShark on 2016/12/28.
 */

public class MarketViewHolder extends BaseViewHolder<Market> {

    private TextView minuteName;
    private ImageView miunteImg;

    public MarketViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_market_miunte_layout);
        miunteImg = $(R.id.iv_miunte_img);
        minuteName = $(R.id.tv_miunte_name);
    }

    @Override
    public void setData(Market data) {
        super.setData(data);
        minuteName.setText("男款头等牛皮鞋");
    }
}
