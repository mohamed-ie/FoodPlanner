package com.soha.foodplanner.ui.auth.registration_options;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

public class GoogleActivityResultContract extends ActivityResultContract<Intent, Task<GoogleSignInAccount>> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Intent intent) {
        return intent;
    }
    @Override
    public Task<GoogleSignInAccount> parseResult(int i, @Nullable Intent intent) {
        if (i != Activity.RESULT_OK)
            return null;
        return GoogleSignIn.getSignedInAccountFromIntent(intent);
    }
}

