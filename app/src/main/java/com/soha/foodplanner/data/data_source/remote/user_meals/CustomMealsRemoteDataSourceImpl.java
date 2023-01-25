package com.soha.foodplanner.data.data_source.remote.user_meals;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class CustomMealsRemoteDataSourceImpl implements CustomMealsRemoteDataSource {
    private final FirebaseFirestore firestore;
    private final Subject<CompleteMeal> completeMealPublisher = ReplaySubject.create();
    private ListenerRegistration listenerRegistration;

    public CustomMealsRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public Completable insertMeal(CompleteMeal completeMeal) {
        return new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                firestore.collection(MEALS).document().set(completeMeal).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) observer.onComplete();
                    if (task.isCanceled()) observer.onError(task.getException());
                });
            }
        };
    }


    @Override
    public Flowable<CompleteMeal> getAllMeals() {
        return completeMealPublisher.toFlowable(BackpressureStrategy.BUFFER);
    }

    private void startListening() {
        listenerRegistration = firestore.collection(MEALS).addSnapshotListener((value, error) -> {
            if (error != null) {
                completeMealPublisher.onError(error);
                return;
            }

            if (value == null) return;

            for (QueryDocumentSnapshot doc : value) {
                completeMealPublisher.onNext(doc.toObject(CompleteMeal.class));
            }
        });
    }

    @Override
    public void closeAll() {
        listenerRegistration.remove();
    }
}
