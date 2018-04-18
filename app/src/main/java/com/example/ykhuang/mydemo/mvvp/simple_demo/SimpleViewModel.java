package com.example.ykhuang.mydemo.mvvp.simple_demo;

import android.app.Activity;

import com.example.ykhuang.mydemo.databinding.ActivitySimpleMvvpBinding;

/**
 * Created by hyk on 2018/4/17.
 * 这边负责吊起网络请求来获取model里面的数据
 */

public class SimpleViewModel {
    private Activity mActivity;
    private ActivitySimpleMvvpBinding activitySimpleMvvpBinding;
    private SimpleModel mSimpleModel;

    public SimpleViewModel(Activity activity,ActivitySimpleMvvpBinding binding){
        mActivity = activity;
        this.activitySimpleMvvpBinding = binding;

        mSimpleModel = new SimpleModel();
        mSimpleModel.setAge("18");
        mSimpleModel.setHeight("119");
        mSimpleModel.setName("阿姆斯特丹洛特");

        activitySimpleMvvpBinding.setModel(mSimpleModel);

    }


}
