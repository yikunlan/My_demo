package com.example.ykhuang.mydemo.animation;

import android.animation.TypeEvaluator;

public class PathAnimator implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {

        Float startX = startValue;
        Float endX = endValue;


        Float curValueX = (startX + fraction * (endX - startX));
        return curValueX;
    }
}
