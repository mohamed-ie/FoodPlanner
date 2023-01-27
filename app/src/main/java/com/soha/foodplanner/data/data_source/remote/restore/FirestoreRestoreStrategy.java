package com.soha.foodplanner.data.data_source.remote.restore;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.soha.foodplanner.data.local.entities.PlannedMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FirestoreRestoreStrategy implements RestoreStrategy {
    private final DocumentReference backupDocument;
    @SuppressWarnings("FieldCanBeLocal")
    private final String FAVOURITE_MEALS_ARRAY = "favourite_meals";
    @SuppressWarnings("FieldCanBeLocal")
    private final String BACKUP_COLLECTION = "backup";
    @SuppressWarnings("FieldCanBeLocal")
    private final String PLANNED_MEALS_COLLECTION = "planned_meals";


    public FirestoreRestoreStrategy(FirebaseFirestore firestore, String userId) {
        backupDocument = firestore.collection(BACKUP_COLLECTION).document(userId);
    }

    @Override
    public Flowable<Long> restoreFavouriteMeals() {
        return Flowable.fromPublisher(publisher -> backupDocument.get().addOnCompleteListener(task -> {
            if (task.isCanceled())
                publisher.onError(task.getException());

            else if (task.isSuccessful()) {
                List<String> mealIds = (List<String>) task.getResult().get(FAVOURITE_MEALS_ARRAY);
                if (mealIds != null)
                    mealIds.forEach(l -> publisher.onNext(Long.parseLong(l)));
            }
        }));
    }

    @Override
    public Flowable<PlannedMeals> restorePlannedMeals() {
        return Flowable.fromPublisher(publisher -> backupDocument.collection(PLANNED_MEALS_COLLECTION)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isCanceled())
                        publisher.onError(task.getException());

                    else if (task.isSuccessful())
                        if (task.getResult() != null)
                            task.getResult()
                                    .getDocuments()
                                    .forEach(documentSnapshot ->
                                            publisher.onNext(new PlannedMeals(
                                                    (Long) documentSnapshot.get("meal_id"),
                                                    (Long) documentSnapshot.get("date"),
                                                    (String) documentSnapshot.get("meal_time"))));
                }));
    }
}
