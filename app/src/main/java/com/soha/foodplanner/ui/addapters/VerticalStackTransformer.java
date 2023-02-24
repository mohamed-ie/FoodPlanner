package com.soha.foodplanner.ui.addapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.utils.UiUtils;

public class VerticalStackTransformer implements ViewPager2.PageTransformer {

    private final float offscreenPageLimit;
    @SuppressWarnings("FieldCanBeLocal")
    private final float DEFAULT_TRANSLATION_X = 1.2f;
    @SuppressWarnings("FieldCanBeLocal")
    private final float DEFAULT_SCALE = 1f;
    private final float SCALE_FACTOR = .14f;

    public VerticalStackTransformer(float offscreenPageLimit) {
        this.offscreenPageLimit = offscreenPageLimit;
    }


    @Override
    public void transformPage(@NonNull View page, float position) {
        float scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE;
            page.setElevation(Constants.INSPIRATION_COUNT-position);

        if (position <= 0f) {
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
        } else if (position <= offscreenPageLimit - 1) {
            page.setScaleY(scaleFactor);
            page.setTranslationX(((page.getWidth() - UiUtils.dpTpPx(16)) * position));
//            page.setAlpha(alphaFactor);
        } else {
            page.setTranslationX(DEFAULT_TRANSLATION_X);
            page.setScaleX(DEFAULT_SCALE);
            page.setScaleY(DEFAULT_SCALE);
        }
    }
}
