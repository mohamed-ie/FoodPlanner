package com.soha.foodplanner.ui.addapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.utils.UiUtils;

public class VerticalStackTransformer implements ViewPager2.PageTransformer {

    private final float offscreenPageLimit;
    private final float DEFAULT_TRANSLATION_Y = .0f;

    private final float SCALE_FACTOR = .12f;
    private final float DEFAULT_SCALE = 1f;

    private final float ALPHA_FACTOR = .5f;
    private final float DEFAULT_ALPHA = 1f;

    public VerticalStackTransformer(float offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }


    @Override
    public void transformPage(@NonNull View page, float position) {
        ViewCompat.setElevation(page, -Math.abs(position));

        float scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE;
        float alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA;

        if (position <= 0f) {
            page.setTranslationY(DEFAULT_TRANSLATION_Y);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(DEFAULT_ALPHA + position);
        } else if (position <= offscreenPageLimit - 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(DEFAULT_SCALE);
            page.setTranslationY(-page.getHeight() * position + UiUtils.dpTpPx(10, page.getResources()) * position);
            page.setAlpha(alphaFactor);
        }else{
            page.setTranslationY(DEFAULT_TRANSLATION_Y);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
            page.setAlpha(alphaFactor);
        }
    }
}
