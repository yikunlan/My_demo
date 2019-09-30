package com.example.ykhuang.mydemo.animation;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int startX = startValue.x;
        int endX = endValue.x;

        int startY = startValue.y;
        int endY = endValue.y;

        int curValueX = (int) (startX + fraction * (endX - startX));
        int curValueY = (int) (startY + fraction * (endY - startY));
        return new Point(curValueX,curValueY);
    }
}
