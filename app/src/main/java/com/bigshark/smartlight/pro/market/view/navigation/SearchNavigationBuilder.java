package com.bigshark.smartlight.pro.market.view.navigation;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.pro.base.view.navigation.NavigationBuilderAdapter;

/**
 * Created by bigShark on 2016/12/28.
 */

public class SearchNavigationBuilder extends NavigationBuilderAdapter {
    private String search;
    private SearchLinstener searchLinstener;

    public SearchNavigationBuilder setSearch(String search){
        this.search = search;
        return this;
    }

    public SearchNavigationBuilder setSearchListener(SearchLinstener searchLinstener){
        this.searchLinstener = searchLinstener;
        return this;
    }

    public SearchNavigationBuilder(Context context) {
        super(context);
    }
    @Override
    public int getLayoutId() {
        return R.layout.toolbar_search_layout;
    }

    @Override
    public void createAndBind(ViewGroup parent) {
        super.createAndBind(parent);
        setImageViewStyle(R.id.iv_left,getLeftIconRes(),getLeftIconOnClickListener());
        setSearchAndTextView(R.id.et_search,R.id.tv_right,getSearch(),getSearchLinstener());
    }

    public void setSearchAndTextView(int editViewId,int viewId, String title,final SearchLinstener searchLinstener){
        TextView textView = (TextView)getContentView().findViewById(viewId);
        final EditText editText = (EditText)getContentView().findViewById(editViewId);
        if (TextUtils.isEmpty(title)){
            textView.setVisibility(View.GONE);
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != searchLinstener){
                    searchLinstener.search(editText.getText().toString().trim());
                }
            }
        });
    }

    private String getSearch(){
        return search;
    }

    public SearchLinstener getSearchLinstener(){
        return searchLinstener;
    }
    public interface SearchLinstener{
        void search(String searchText);
    }
}
