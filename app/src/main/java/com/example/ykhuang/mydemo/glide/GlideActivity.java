package com.example.ykhuang.mydemo.glide;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ykhuang.mydemo.R;

public class GlideActivity extends Activity {

    private ImageView mIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide);

        mIv = findViewById(R.id.iv_glide);
    }

    public void gauseClick(View view) {
        Glide.with(this)
                .load("https://c-ssl.duitang.com/uploads/blog/201409/07/20140907092134_Qzyfw.jpeg")
                .apply(RequestOptions.bitmapTransform(new GlideBlurformation(this,20F)))
                .into(mIv);


    }
}
