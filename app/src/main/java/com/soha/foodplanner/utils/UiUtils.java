package com.soha.foodplanner.utils;

import android.content.res.Resources;
import android.util.TypedValue;

public class UiUtils {
    public static float dpTpPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                Resources.getSystem().getDisplayMetrics()
        );
    }
}
