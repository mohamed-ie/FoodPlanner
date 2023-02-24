package com.soha.foodplanner.ui.common.dialogs.loading;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import android.view.View;
import android.widget.Button;

import com.soha.foodplanner.R;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragment;
import com.soha.foodplanner.ui.common.dialogs.BaseDialogFragmentWithArgs;

import java.util.Collections;


public class LoadingDialogFragment extends BaseDialogFragmentWithArgs<LoadingDialogFragmentArgs> {
    public static final String CANCEL = "cancel";
    private Button buttonCancel;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_loading;
    }

    @Override
    public void initViews(@NonNull View view) {
        buttonCancel = view.findViewById(R.id.buttonCancel);
    }

    @Override
    public void setListeners() {
        buttonCancel.setOnClickListener((View v) -> sendDataViaBackStackEntry(Collections.singletonMap(CANCEL, true)));
    }

    @Override
    public LoadingDialogFragmentArgs getSafeArgs() {
        return LoadingDialogFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public void updateUiFromSafeArgs(LoadingDialogFragmentArgs args) {
        if(!args.getCancelable())
            buttonCancel.setVisibility(View.GONE);
    }
}