package com.soha.foodplanner.data.data_source.remote.backup;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.soha.foodplanner.data.local.entities.PlannedMeals;

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
        backupDocument.update(Collections.singletonMap(LAST_UPDATE, FieldValue.serverTimestamp()))
                .addOnFailureListener(e -> backupDocument.set(Collections.singletonMap(LAST_UPDATE, FieldValue.serverTimestamp())));
    }

    @Override
    public Completable addFavouriteMeal(long mealId) {
        return Completable.create(source -> backupDocument.update(Collections.singletonMap(FAVOURITE_MEALS_ARRAY, FieldValue.arrayUnion(String.valueOf(mealId))))
                .addOnFailureListener(e -> backupDocument.set(Collections.singletonMap(FAVOURITE_MEALS_ARRAY, FieldValue.arrayUnion(String.valueOf(mealId))))
                        .addOnCompleteListener(task -> {
                            if (task.isCanceled())
                                source.onError(task.getException());

                            else if (task.isSuccessful())
                                source.onComplete();
                        }))
        );
    }

    @Override
    public Completable addPlannedMeal(long id, long date, String mealTime) {
        Map<String, Object> data = new HashMap<>();
        data.put("meal_id", id);
        data.put("meal_time", date);
        data.put("date", mealTime);

        return Completable.create(source ->
                backupDocument.collection(PLANNED_MEALS_COLLECTION)
                        .document(String.valueOf(id))
                        .set(data)
                        .addOnCompleteListener(task -> {
                            if (task.isCanceled())
                                source.onError(task.getException());

                            if (task.isSuccessful())
                                source.onComplete();
                        })
        );
    }

    @Override
    public Completable deleteFavouriteMeal(long mealId) {
        return Completable.create(source -> backupDocument.update(Collections.singletonMap(FAVOURITE_MEALS_ARRAY, FieldValue.arrayRemove(String.valueOf(mealId))))
                .addOnCompleteListener(task -> {
                    if (task.isCanceled())
                        source.onError(task.getException());

                    else if (task.isSuccessful())
                        source.onComplete();
                }));
    }


    @Override
    public Completable deleteFromPlannedMeal(PlannedMeals plannedMeals) {
        return Completable.create(source ->
                backupDocument.collection(PLANNED_MEALS_COLLECTION)
                        .document(String.valueOf(plannedMeals.getMealId()))
                        .delete()
                        .addOnCompleteListener(task -> {
                            if (task.isCanceled())
                                source.onError(task.getException());

                            if (task.isSuccessful())
                                source.onComplete();
                        })
        );
    }
}
