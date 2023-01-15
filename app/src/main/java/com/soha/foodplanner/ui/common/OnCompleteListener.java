package com.soha.foodplanner.ui.common;

import androidx.annotation.StringRes;

public interface OnCompleteListener {
    void onSuccess();
    void onFailure(@StringRes int messageResource);
}
