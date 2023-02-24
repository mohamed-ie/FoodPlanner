package com.soha.foodplanner.ui.auth.signup.presenter;

import com.soha.foodplanner.ui.common.dialogs.listeners.ShowErrorDialog;
import com.soha.foodplanner.ui.common.dialogs.listeners.ShowLoadingDialog;
import com.soha.foodplanner.ui.common.dialogs.listeners.ShowLoginDialog;
import com.soha.foodplanner.ui.common.dialogs.listeners.ShowRetryDialog;

public interface SignupListener
extends ShowRetryDialog, ShowErrorDialog, ShowLoadingDialog, ShowLoginDialog {
}
