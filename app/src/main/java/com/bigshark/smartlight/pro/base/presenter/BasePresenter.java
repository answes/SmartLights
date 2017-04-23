package com.bigshark.smartlight.pro.base.presenter;

import android.content.Context;

import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.model.BaseModel;
import com.google.gson.Gson;

/**
 * Created by bigShark on 2016/12/19.
 */

public abstract class BasePresenter<M extends BaseModel> extends MVPBasePresenter {

    private Context context;
    private Gson gson;
    private M model;

    public BasePresenter(Context context){
        this.context = context;
        this.gson = new Gson();
        this.model = bindModel();
    }

    public M getModel() {
        return model;
    }

    public abstract M bindModel();

    public Context getContext(){
         return  context;
     }

    public Gson getGson(){
        return  gson;
    }

    public interface OnUIThreadListener<T>{
        void onResult(T result);
        void onErro(String string);
    }

}
