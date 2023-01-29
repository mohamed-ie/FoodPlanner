package com.soha.foodplanner.data.repository.meals;

import android.util.Pair;

import com.soha.foodplanner.data.data_source.remote.backup.BackupStrategy;
import com.soha.foodplanner.data.data_source.remote.restore.RestoreStrategy;
import com.soha.foodplanner.data.local.entities.FavouriteMealsWithMeal;
import com.soha.foodplanner.data.local.entities.Meal;
import com.soha.foodplanner.data.local.entities.PlanedMealWithMeal;
import com.soha.foodplanner.data.local.image_saver.InternalStorageImageSaver;
import com.soha.foodplanner.data.local.model.CompleteIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;
import com.soha.foodplanner.data.local.model.MinIngredient;
import com.soha.foodplanner.data.local.model.MinMeal;
import com.soha.foodplanner.data.data_source.remote.meals.MealsRemoteDataSource;
import com.soha.foodplanner.data.repository.MealsLocalDataSource;

import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsRepositoryImpl implements MealsRepository {
    private final MealsRemoteDataSource remoteDataSource;
    private final MealsLocalDataSource localDataSource;
    private final InternalStorageImageSaver imageSaver;
    private final GlideImageDownloader glideImageDownloader;
    private final BackupStrategy backupStrategy;
    private final RestoreStrategy restoreStrategy;

    public MealsRepositoryImpl(MealsRemoteDataSource remoteDataSource, MealsLocalDataSource localDataSource, InternalStorageImageSaver imageSaver, GlideImageDownloader glideImageDownloader, BackupStrategy backupStrategy, RestoreStrategy restoreStrategy) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.imageSaver = imageSaver;
        this.glideImageDownloader = glideImageDownloader;
        this.backupStrategy = backupStrategy;
        this.restoreStrategy = restoreStrategy;
    }

    @Override
    public Single<List<MinMeal>> getMealsByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category).zipWith(localDataSource.getAllFavouriteMealsIds(), this::updateIsFavoured);
    }

    private List<MinMeal> updateIsFavoured(List<MinMeal> minMeals, List<Long> longs) {
        for (MinMeal minMeal : minMeals) {
            for (long long1 : longs) {
                if (long1 == minMeal.getId()) {
                    minMeal.setFavoured(true);
                    break;
                }
            }
        }
        return minMeals;
    }

    @Override
    public Single<List<MinMeal>> getMealsByArea(String area) {
        return remoteDataSource.getMealsByArea(area).zipWith(localDataSource.getAllFavouriteMealsIds(), this::updateIsFavoured);
    }

    @Override
    public Single<List<MinMeal>> loadMeals(int count) {
        return null;
    }

    @Override
    public Single<List<MinMeal>> loadByCategory(String category) {
        return remoteDataSource.getMealsByCategory(category).zipWith(localDataSource.getAllFavouriteMealsIds(), this::updateIsFavoured);
    }

    @Override
    public Single<List<Pair<Long, String>>> searchByFirstLetter(char c) {
        return remoteDataSource.getAllMealsByFirstLetter(c);
    }

    @Override
    public Single<List<String>> getAllAres() {
        return remoteDataSource.getAreas();
    }

    @Override
    public Single<List<MinIngredient>> getAllIngredients() {
        return remoteDataSource.getAllIngredients();
    }

    @Override
    public Single<List<String>> getAllCategories() {
        return remoteDataSource.getAllCategories();
    }

    @Override
    public Single<Meal> selectMealById(long id) {
        return localDataSource.selectMealById(id).zipWith(localDataSource.getAllFavouriteMealsIds(), (meal, longs) -> {
            boolean isFavoured = false;
            for (long long1 : longs) {
                if (long1 == meal.getId()) {
                    isFavoured = true;
                    break;
                }
            }
            meal.setFavoured(isFavoured);
            return meal;
        });
    }

    @Override
    public Completable deleteFavMeal(FavouriteMealsWithMeal mealFav) {
        return backupStrategy.deleteFavouriteMeal(mealFav.getMeal().getId()).andThen(localDataSource.deleteFavMeal(mealFav));
    }

    @Override
    public Completable deletePlannedMeal(PlanedMealWithMeal planedMealWithMeal) {
        return localDataSource.deletePlannedMeal(planedMealWithMeal);
    }

    @Override
    public Flowable<List<FavouriteMealsWithMeal>> getFavMeal() {
        return localDataSource.getFavMeal();
    }

    private Single<Boolean> checkForFavouriteBackups() {
        return Single.create((SingleOnSubscribe<Boolean>) source -> {
            List<Long> remoteIds = restoreStrategy.restoreFavouriteMeals().toList().blockingGet();
            List<Long> localIds = localDataSource.getAllFavouriteMealsIds().blockingGet();
            if (remoteIds.size() > localIds.size()) source.onSuccess(true);
            source.onSuccess(false);
        }).observeOn(Schedulers.io());
    }

    private Single<Boolean> updateFavouriteFromBackups() {
        return Single.create((SingleOnSubscribe<Boolean>) source -> {
            List<Long> remoteIds = restoreStrategy.restoreFavouriteMeals().toList().blockingGet();
            List<Long> localIds = localDataSource.getAllFavouriteMealsIds().blockingGet();
            if (remoteIds.size() > localIds.size()) for (long id : remoteIds) {
                if (!localIds.contains(id)) insertFavMeal(id);
            }
        }).observeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<PlanedMealWithMeal>> getPlanedMeal() {
        return localDataSource.getPlanedMeal();
    }

    @Override
    public Completable insertFavMeal(long id) {
        return remoteDataSource.getMealDetailsById(id).subscribeOn(Schedulers.io()).flatMapCompletable(completeMeal -> {
            try {
                completeMeal.getMeal().setPhotoUri(imageSaver.saveImage(glideImageDownloader.download(completeMeal.getMeal().getPhotoUri()), completeMeal.getMeal().getName()));
                for (CompleteIngredient completeIngredient : completeMeal.getIngredients()) {
                    completeIngredient.setThumbnailUrl(imageSaver.saveImage(glideImageDownloader.download(completeIngredient.getThumbnailUrl()), completeIngredient.getName()));
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return localDataSource.insertFavMeal(completeMeal).andThen(backupStrategy.addFavouriteMeal(completeMeal.getMeal().getId()));
        });
    }

    @Override
    public Completable insertPlanMeal(CompleteMeal completeMeal, long date, String mealTime) {
        return backupStrategy.addPlannedMeal(completeMeal.getMeal().getId(), date, mealTime)
                .ambWith(Completable.create(source -> {
                    try {
                        completeMeal.getMeal().setPhotoUri(imageSaver.saveImage(glideImageDownloader.download(completeMeal.getMeal().getPhotoUri()), completeMeal.getMeal().getName()));
                        for (CompleteIngredient completeIngredient : completeMeal.getIngredients()) {
                            completeIngredient.setThumbnailUrl(imageSaver.saveImage(glideImageDownloader.download(completeIngredient.getThumbnailUrl()), completeIngredient.getName()));
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                   localDataSource.insertPlanMeal(completeMeal, date, mealTime).subscribe();
                    source.onComplete();
                }));
    }

    @Override
    public Completable insertPlanMeal(long id, long date, String mealTime) {
        return remoteDataSource.getMealDetailsById(id).subscribeOn(Schedulers.io()).flatMapCompletable(completeMeal -> {
            try {
                completeMeal.getMeal().setPhotoUri(imageSaver.saveImage(glideImageDownloader.download(completeMeal.getMeal().getPhotoUri()), completeMeal.getMeal().getName()));
                for (CompleteIngredient completeIngredient : completeMeal.getIngredients()) {
                    completeIngredient.setThumbnailUrl(imageSaver.saveImage(glideImageDownloader.download(completeIngredient.getThumbnailUrl()), completeIngredient.getName()));
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return localDataSource.insertPlanMeal(completeMeal, date, mealTime).andThen(backupStrategy.addFavouriteMeal(completeMeal.getMeal().getId()));
        });
    }

    @Override
    public Single<List<Long>> getAllFavouriteMealsIds() {
        return localDataSource.getAllFavouriteMealsIds();
    }


    @Override
    public Single<List<MinMeal>> getMealsByIngredient(String ingredient) {
        return remoteDataSource.getAllMealsByIngredient(ingredient);
    }

    @Override
    public Flowable<Pair<CompleteMeal, Integer>> getAllCompleteMeals() {
        return remoteDataSource.getAllCompleteMeals();
//        return remoteDataSource.getAllCompleteMeals().zipWith(Flowable.fromSingle(localDataSource.getAllFavouriteMealsIds()), new BiFunction<Pair<CompleteMeal, Integer>, List<Long>, Pair<CompleteMeal, Integer>>() {
//            @Override
//            public Pair<CompleteMeal, Integer> apply(Pair<CompleteMeal, Integer> completeMealIntegerPair, List<Long> longs) throws Throwable {
//                boolean isFavoured = false;
//                for (long long1 : longs) {
//                    if (long1 == completeMealIntegerPair.first.getMeal().getId()) {
//                        isFavoured = true;
//                        break;
//                    }
//                }
//                completeMealIntegerPair.first.getMeal().setFavoured(isFavoured);
//                return completeMealIntegerPair;
//            }
//        });
//    }
    }

    @Override
    public Completable restoreFavouriteMeals() {
        return restoreStrategy.restoreFavouriteMeals().flatMapCompletable(this::insertFavMeal);
    }

    @Override
    public Completable restorePlannedMeals() {
        return restoreStrategy.restorePlannedMeals().flatMapCompletable(plannedMeals -> insertPlanMeal(plannedMeals.getId(), plannedMeals.getDate(), plannedMeals.getMealTime()));
    }

    @Override
    public Flowable<List<PlanedMealWithMeal>> getPlannedMeals() {
        return localDataSource.getPlanedMeal();
    }

    @Override
    public Single<CompleteMeal> getMealById(long id) {
        return remoteDataSource.getMealDetailsById(id);
    }
}
