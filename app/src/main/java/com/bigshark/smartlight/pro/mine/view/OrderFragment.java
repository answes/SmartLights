package com.bigshark.smartlight.pro.mine.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.andview.refreshview.XRefreshView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bigShark on 2017/1/20.
 */

public class OrderFragment extends BaseFragment {
    private int type = 0;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    @BindView(R.id.xrefreshview)
    XRefreshView xrefreshview;

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_order;
    }

    @Override
    public void initContentView(View viewContent) {
        ButterKnife.bind(this, viewContent);
    }


}
