package com.soha.foodplanner.ui.create_recipe;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.soha.foodplanner.MyApp;
import com.soha.foodplanner.R;
import com.soha.foodplanner.common.Factory;
import com.soha.foodplanner.ui.MainActivity;
import com.soha.foodplanner.ui.common.fragment.BaseFragment;
import com.soha.foodplanner.ui.create_recipe.presenter.CreateRecipePresenter;
import com.soha.foodplanner.ui.create_recipe.presenter.CreateRecipePresenterFactory;
import com.soha.foodplanner.utils.UiUtils;

public class CreateRecipeFragment extends BaseFragment<CreateRecipePresenter> {
    private TextInputLayout textInputLayoutRecipeTitle;
    private TextInputLayout textInputLayoutAuthorNotes;
    //    private TextInputLayout textInputLayoutAddIngredients;
    private TextInputLayout textInputLayoutSearch;
    private MotionLayout motionLayout;
    private ImageView imageViewThumbnail;
    private ImageButton imageButtonRemoveThumbnail;
    private ImageButton imageButtonAddInstruction;
    private RadioGroup radioGroupSharingOptions;
    private Button buttonDone;

    private InstructionsAdapter instructionsAdapter;
    private ActivityResultLauncher<PickVisualMediaRequest> pickImageActivityResultLauncher;
    private final PickVisualMediaRequest pickImageOnlyVisualMediaRequest = new PickVisualMediaRequest.Builder()
            .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
            .build();
    private Uri uriThumbnail;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pickImageActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), this::onPickImageResult);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_create_recipe;
    }

    @Override
    protected Factory<CreateRecipePresenter> getPresenterFactory() {
        return new CreateRecipePresenterFactory(((MyApp) ((MainActivity) requireHost()).getApplication()).getMealsRepository());
    }

    private void onPickImageResult(Uri uri) {
        if (uri == null)
            return;
        motionLayout.transitionToState(R.id.imageButtonRemoveThumbnail_visible);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners((int) UiUtils.dpTpPx(16)));
        uriThumbnail = uri;
        Glide.with(this)
                .load(uri)
                .override(imageViewThumbnail.getWidth(), imageViewThumbnail.getHeight())
                .apply(requestOptions)
                .into(imageViewThumbnail);
    }


    @Override
    protected void initViews(View view) {
        textInputLayoutRecipeTitle = view.findViewById(R.id.textInputLayoutRecipeTitle);
//        textInputLayoutAddIngredients = view.findViewById(R.id.textInputLayoutAddIngredients);
        textInputLayoutAuthorNotes = view.findViewById(R.id.textInputLayoutAuthorNotes);

        textInputLayoutSearch = view.findViewById(R.id.textInputLayoutSearch);

        motionLayout = view.findViewById(R.id.motionLayout);
        imageViewThumbnail = view.findViewById(R.id.imageViewThumbnail);

        imageButtonAddInstruction = view.findViewById(R.id.imageButtonAddInstruction);
        imageButtonRemoveThumbnail = view.findViewById(R.id.imageButtonRemoveThumbnail);

        radioGroupSharingOptions = view.findViewById(R.id.radioGroupSharingOptions);

        buttonDone = view.findViewById(R.id.buttonDone);
        initInstructionsRecyclerView(view);
    }

    private void initInstructionsRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewInstructions);
        instructionsAdapter = new InstructionsAdapter();
        recyclerView.setAdapter(instructionsAdapter);
    }

    @Override
    protected void setListeners() {
        imageViewThumbnail.setOnClickListener(v -> {
            if (imageViewThumbnail.getDrawable() == null)
                pickImageActivityResultLauncher.launch(pickImageOnlyVisualMediaRequest);
        });


        textInputLayoutSearch.setStartIconOnClickListener(view -> {
                    if (imageViewThumbnail.getDrawable() == null)
                        motionLayout.transitionToStart();
                    else motionLayout.transitionToState(R.id.imageButtonRemoveThumbnail_visible);
                }
        );

        imageButtonRemoveThumbnail.setOnClickListener(v -> {
            uriThumbnail = null;
            imageViewThumbnail.setImageDrawable(null);
            motionLayout.transitionToState(R.id.imageButtonRemoveThumbnail_visible);
            motionLayout.transitionToStart();
        });

        textInputLayoutAuthorNotes.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            textInputLayoutAuthorNotes.setCounterEnabled(hasFocus || textInputLayoutAuthorNotes.getEditText().getText().length() > 0);
        });

        imageButtonAddInstruction.setOnClickListener(v -> instructionsAdapter.newInstruction());

//        textInputLayoutAddIngredients.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                motionLayout.animate().start();
//                return false;
//            }
//        });
//        textInputLayoutAddIngredients.getEditText().setOnClickListener(v->motionLayout.animate().start());

        buttonDone.setOnClickListener(v -> {
//            String title = String.valueOf(editTextRecipeTitle.getText());
//            String authorNotes = String.valueOf(textInputEditTextAuthorNotes.getText());
//            String photoUri = String.valueOf(textInputEditTextPassword.getText());
//            presenter.updateLoginState(email, password);
//            if (presenter.isInputsValid()) {
//                presenter.login(email, password);
//                clearEmailError();
//                clearPasswordError();
//            } else showFieldsErrors();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pickImageActivityResultLauncher.unregister();
    }
}