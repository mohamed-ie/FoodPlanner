package com.soha.foodplanner.ui.main.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.data.local.model.User;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.fragment.BaseFragment;
import com.soha.foodplanner.ui.main.profile.presenter.ProfilePresenter;
import com.soha.foodplanner.ui.main.profile.presenter.ProfilePresenterFactory;
import com.soha.foodplanner.ui.main.profile.presenter.ProfilePresenterListener;


public class ProfileFragment extends BaseFragment<ProfilePresenter> implements ProfilePresenterListener {

    private ImageButton imageButtonUpload;
    private Button buttonChangePassword;
    private Button buttonLogout;
    private ImageView imageViewProfile;
    private TextView textViewName;
    private TextView textViewEmail;
    ActivityResultLauncher<PickVisualMediaRequest> pickMediaActivityResultLauncher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickMediaActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), this::onActivityResult);
    }

    void onActivityResult(Uri uri) {
        if (uri != null) {
            Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .circleCrop()
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imageViewProfile.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            imageViewProfile.setImageDrawable(placeholder);
                        }
                    });
        }
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected Factory<ProfilePresenter> getPresenterFactory() {
        return new ProfilePresenterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getAuthRepository(), this);
    }

    @Override
    protected void initViews(View view) {
        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        //
        imageButtonUpload = view.findViewById(R.id.imageButtonUpload);
        //
        buttonChangePassword = view.findViewById(R.id.buttonChangePassword);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        //
        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.editTextName);
    }

    @Override
    protected void updateUi() {
        User user = presenter.getState().getUser();
        if (user.getPhotoUri() != null)
            Glide.with(this)
                    .load(user.getPhotoUri())
                    .circleCrop()
                    .into(imageViewProfile);

        textViewName.setText(user.getName());

        textViewEmail.setText(user.getEmail());
    }

    @Override
    protected void setListeners() {
//        buttonLogout.setOnClickListener(v -> navController.navigate(com.soha.foodplanner.ui.profile.ProfileFragmentDirections.actionProfileFragmentToConfirmDialogFragment(R.string.logout_confirm)));
//        imageButtonUpload.setOnClickListener(v -> pickMediaActivityResultLauncher
//                .launch(new PickVisualMediaRequest.Builder()
//                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()));
    }

    @Override
    protected void onDialogConfirm(Boolean yes) {
        if (yes) {
            presenter.logout();
            presenter.updateRememberMe();
        }
    }

    @Override
    public void onLogoutLoading() {

    }

    @Override
    public void onLogoutSuccess() {
        Toast.makeText(requireContext(), R.string.sign_out_successfully, Toast.LENGTH_SHORT).show();
//        navController.navigate(com.soha.foodplanner.ui.profile.ProfileFragmentDirections.actionProfileFragmentToAuthNavGraph());
    }

    @Override
    public void onLogoutError(String message) {

    }
}