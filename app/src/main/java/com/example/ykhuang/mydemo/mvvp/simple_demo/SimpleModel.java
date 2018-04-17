package com.example.ykhuang.mydemo.mvvp.simple_demo;

import android.databinding.BaseObservable;

/**
 * Created by hyk on 2018/4/16.
 */

public class SimpleModel extends BaseObservable {
    private String name;
    private String age;
    private String height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void click(){
        setName("卡卡罗特.阿波罗");
        setAge("10");
        setHeight("118");
    }
}
