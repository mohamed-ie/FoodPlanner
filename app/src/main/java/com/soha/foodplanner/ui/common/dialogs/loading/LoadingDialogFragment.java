package com.soha.foodplanner.ui.common.dialogs.loading;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import android.view.View;
import android.widget.Button;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragment;

import java.util.Collections;


public class LoadingDialogFragment extends BaseDialogFragment {
    public static final String CANCEL = "cancel";
    Button button_cancel;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_loading;
    }

    @Override
    public void initViews(@NonNull View view) {
        button_cancel = view.findViewById(R.id.btn_cancelling);
    }

    @Override
    public void setListeners() {
        button_cancel.setOnClickListener((View v) -> sendDataViaBackStackEntry(Collections.singletonMap(CANCEL, true)));
    }
}