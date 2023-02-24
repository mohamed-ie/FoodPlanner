package com.soha.foodplanner.utils.card_profile;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MealChefBackgroundDrawale extends Drawable {
    private final float width;
    private final float height;
    private final float padding;
    @ColorInt
    private final int backgroundColor;

    public MealChefBackgroundDrawale(float width, float height, float padding, int backgroundColor) {
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, 0);
        path.lineTo(width, height / 2);
        path.arcTo(width-padding, height/2-padding*2 , width+padding, height/2, 90,90 , false);
        path.arcTo(padding, padding*2/3, width-padding, (height/2-padding)*2, 0,-180 , false);
        path.arcTo(-padding, height/2-padding*2 , padding, height/2, 0,90 , false);
        path.close();

        Path path1 = new Path();
        path1.moveTo(0,0);
        path1.lineTo(width,0);
        path1.lineTo(width,height);
        path1.lineTo(0,height);
        path1.close();
        path1.op(path, Path.Op.DIFFERENCE);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(backgroundColor);
        canvas.drawPath(path1, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

}
