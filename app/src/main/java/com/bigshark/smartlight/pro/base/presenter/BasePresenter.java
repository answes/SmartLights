package com.bigshark.smartlight.pro.base.presenter;

import android.content.Context;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;

/**
 * Created by bigShark on 2016/12/19.
 */

public abstract class BasePresenter extends MVPBasePresenter {

    private Context context;
    //private Gson gson;

     public Context getContext(){
         return  context;
     }

//    public Gson getGson(){
//        return  gson;
//    }

    public interface onUiThreadListener<T>{
        void onResult(T result);
    }

}
