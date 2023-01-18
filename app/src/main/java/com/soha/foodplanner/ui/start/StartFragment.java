package com.soha.foodplanner.ui.start;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Constants;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactory;
import com.soha.foodplanner.ui.common.presenter.factory.PresenterFactoryImpl;
import com.soha.foodplanner.ui.common.presenter.store.PresenterStoreImpl;
import com.soha.foodplanner.ui.start.presenter.StartPresenter;
import com.soha.foodplanner.ui.start.presenter.StartPresenterFactory;
import com.soha.foodplanner.ui.start.presenter.StartPresenterListener;


public class StartFragment extends Fragment implements StartPresenterListener {
    private Button btnSignupWithMail;
    private Button buttonGoogleSignup;
    private TextView textViewLogin;
    private NavController navController;
    private StartPresenter startPresenter;
    private SharedPreferences sharedPreferences;
    private ActivityResultLauncher<Intent> googleSignupResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        googleSignupResultLauncher = registerForActivityResult(new GoogleActivityResultContract(), this::login);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();
    }

    private void initDependencies() {
        navController = NavHostFragment.findNavController(StartFragment.this);
        sharedPreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_APP, Context.MODE_PRIVATE);
        initPresenter();
    }

    private void initPresenter() {
        PresenterFactory<StartPresenter> factory = PresenterFactoryImpl.getInstance(PresenterStoreImpl.getInstance());
        startPresenter = (StartPresenter) factory.create(getTag(), new StartPresenterFactory(FirebaseAuth.getInstance(), this));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
    }

    private void setListeners() {
        btnSignupWithMail.setOnClickListener(view ->
                navController.navigate(StartFragmentDirections.actionStartFragmentToSignUpFragment())
        );
        buttonGoogleSignup.setOnClickListener(v -> googleSignupResultLauncher.launch(getGoogleSignInIntent()));
        textViewLogin.setOnClickListener(v -> navController.navigate(StartFragmentDirections.actionStartFragmentToLoginFragment()));
    }

    private void initViews(View view) {
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
        startPresenter.login(result.getResult().getIdToken());

    }

    @Override
    public void onSuccess() {
        Toast.makeText(getContext(), R.string.login_successful, Toast.LENGTH_SHORT).show();
        navController.navigate(StartFragmentDirections.actionStartFragmentToMainFragment());
    }

    @Override
    public void onFailure(int messageResource) {
        navController.navigate(StartFragmentDirections.actionStartFragmentToErrorDialogFragment(messageResource));
    }
}