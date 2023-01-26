package com.soha.foodplanner.data.data_source.remote.backup;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.soha.foodplanner.data.local.PlannedMeals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;

public class FirestoreBackupStrategy implements BackupStrategy {
    private final DocumentReference backupDocument;
    @SuppressWarnings("FieldCanBeLocal")
    private final String FAVOURITE_MEALS_ARRAY = "favourite_meals";
    @SuppressWarnings("FieldCanBeLocal")
    private final String LAST_UPDATE = "last_update";
    @SuppressWarnings("FieldCanBeLocal")
    private final String BACKUP_COLLECTION = "backup";
    @SuppressWarnings("FieldCanBeLocal")
    private final String PLANNED_MEALS_COLLECTION = "planned_meals";

    public FirestoreBackupStrategy(FirebaseFirestore firestore, String userId) {
        backupDocument = firestore.collection(BACKUP_COLLECTION).document(userId);
        backupDocument.set(Collections.singletonMap(LAST_UPDATE, FieldValue.serverTimestamp()));
    }

    @Override
    public Completable addFavouriteMeal(long mealId) {
        return Completable.fromPublisher(publisher ->
                backupDocument
                        .update(FAVOURITE_MEALS_ARRAY, FieldValue.arrayUnion(String.valueOf(mealId)))
                        .addOnCompleteListener(task -> {
                            if (task.isCanceled())
                                publisher.onError(task.getException());

                            else if (task.isSuccessful())
                                publisher.onComplete();
                        })
        );
    }

    @Override
    public Completable addPlannedMeal(PlannedMeals plannedMeals) {
        Map<String, Object> data = new HashMap<>();
        data.put("meal_id", plannedMeals.getMealId());
        data.put("meal_time", plannedMeals.getMealTime());
        data.put("date", plannedMeals.getDate());

        return Completable.fromPublisher(publisher ->
                backupDocument.collection(PLANNED_MEALS_COLLECTION)
                        .document(String.valueOf(plannedMeals.getMealId()))
                        .set(data)
                        .addOnCompleteListener(task -> {
                            if (task.isCanceled())
                                publisher.onError(task.getException());

                            if (task.isSuccessful())
                                publisher.onComplete();
                        })
        );
    }
}
