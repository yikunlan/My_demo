package com.example.ykhuang.mydemo.mvvp.simple_demo;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ykhuang.mydemo.R;
import com.example.ykhuang.mydemo.databinding.ActivitySimpleMvvpBinding;

/**
 * Created by hyk on 2018/4/16.
 */

public class SimpleMvvmActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_simple_mvvp);
//        ActivitySimpleMvvpBinding是根据layout来生成的，具体怎么生成的应该要去看databinding的架构
        ActivitySimpleMvvpBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_simple_mvvp);

        SimpleViewModel simpleViewModel = new SimpleViewModel(this,binding);
//        binding.executePendingBindings();
    }
}
