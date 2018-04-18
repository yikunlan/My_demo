package com.example.ykhuang.mydemo.mvvp.simple_demo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.ykhuang.mydemo.BR;

/**
 * Created by hyk on 2018/4/16.
 *
 * 让你的绑定数据类继承BaseObservable，然后通过调用notifyPropertyChanged方法来通知界面属性改变
 *
 * 在需要通知的属性的get方法上加上@Bindable，这样编译阶段会生成BR.[property name]，
 * 然后使用这个调用方法notifyPropertyChanged就可以通知界面刷新了。
 * 如果你的数据绑定类不能继承BaseObservable，那你就只能自己实现Observable接口，可以参考BaseObservable的实现。
 */

public class SimpleModel extends BaseObservable {
    private String name;
    private String age;
    private String height;
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
    @Bindable
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
        notifyPropertyChanged(BR.height);
    }

    public void click(){
        setName("卡卡罗特.阿波罗");
        setAge("10");
        setHeight("118");
    }
}
