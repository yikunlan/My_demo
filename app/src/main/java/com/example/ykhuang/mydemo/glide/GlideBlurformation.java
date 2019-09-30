package com.example.ykhuang.mydemo.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.security.MessageDigest;
/**
 * Created by yukuoyuan on 2017/9/29.
 */
public class GlideBlurformation extends BitmapTransformation {
    private Context context;
    private Float mRadius = 10F;

    /**
     *
     * @param context
     * @param radius  0 < radius <= 25
     */
    public GlideBlurformation(Context context,Float radius) {
        this.context = context;
        this.mRadius = radius;
    }
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Log.i("sdfsfsf","添加模糊效果");
        return BlurBitmapUtil.instance().blurBitmap(context, toTransform, mRadius,outWidth,outHeight);
    }
    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}

