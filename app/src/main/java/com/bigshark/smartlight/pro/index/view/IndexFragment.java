package com.bigshark.smartlight.pro.index.view;

import android.view.View;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.BaseFragment;
import com.bigshark.smartlight.pro.index.view.navigation.IndexNavigationBuilder;

/**
 * Created by bigShark on 2016/12/28.
 */

public class IndexFragment extends BaseFragment {
    @Override
    public int getContentView() {
        return R.layout.fragment_index;
    }

    @Override
    public void initContentView(View viewContent) {
 initToolbar(viewContent);
    }

    private void initToolbar(View viewContent) {
        IndexNavigationBuilder toolbar = new IndexNavigationBuilder(getActivity());
        toolbar.setTitle(R.string.main_index_text)
                .createAndBind((ViewGroup)viewContent);
    }
}
