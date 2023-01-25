package com.soha.foodplanner.ui.start;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.common.BaseFragment;
import com.soha.foodplanner.ui.start.presenter.StartPresenter;
import com.soha.foodplanner.ui.start.presenter.StartPresenterFactory;
import com.soha.foodplanner.ui.start.presenter.StartPresenterListener;


public class StartFragment extends BaseFragment<StartPresenter> implements StartPresenterListener {
    private Button btnSignupWithMail;
    private Button buttonGoogleSignup;
    private TextView textViewLogin;

    private ActivityResultLauncher<Intent> googleSignupResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        googleSignupResultLauncher = registerForActivityResult(new GoogleActivityResultContract(), this::login);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_start;
    }

    @Override
    protected Factory<StartPresenter> getPresenterFactory() {
        return new StartPresenterFactory(
                ((MyApp) requireActivity().getApplication()).getAuthRepository(),
                this
        );
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
    }

    @Override
    protected void setListeners() {
        btnSignupWithMail.setOnClickListener(view ->
                navController.navigate(StartFragmentDirections.actionStartFragmentToSignUpFragment())
        );
        buttonGoogleSignup.setOnClickListener(v -> googleSignupResultLauncher.launch(getGoogleSignInIntent()));
        textViewLogin.setOnClickListener(v -> navController.navigate(StartFragmentDirections.actionStartFragmentToLoginFragment()));
    }

    @Override
    protected void initViews(View view) {
        btnSignupWithMail = view.findViewById(R.id.buttonSignUpWithMail);
        buttonGoogleSignup = view.findViewById(R.id.buttonGoogleSignup);
        //
        textViewLogin = view.findViewById(R.id.textViewLogin);
    }

    private Intent getGoogleSignInIntent() {
        return GoogleSignIn.getClient(requireContext(),
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()).getSignInIntent();
    }

    private void login(Task<GoogleSignInAccount> result) {
        if (result == null)
            return;
        presenter.login(result.getResult().getIdToken());
    }

    @Override
    public void onLoginWithGoogleLoading() {
        navController.navigate(StartFragmentDirections.actionStartFragmentToLoadingFragment());
    }

    @Override
    public void onLoginWithGoogleSuccess() {
        navController.popBackStack();
        Toast.makeText(getContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
        navController.navigate(StartFragmentDirections.actionStartFragmentToMainFragment());
    }

    @Override
    public void onLoginWithGoogleError(String message) {
        navController.navigate(StartFragmentDirections.actionStartFragmentToErrorDialogFragment(R.string.unexpected_error_try_again));
    }
}