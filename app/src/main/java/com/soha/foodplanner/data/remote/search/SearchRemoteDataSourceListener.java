package com.soha.foodplanner.data.remote.search;

import com.soha.foodplanner.data.model.MinIngredient;
import com.soha.foodplanner.data.remote.common.Failed;
import com.soha.foodplanner.data.remote.common.Loading;
import com.soha.foodplanner.data.remote.common.Success;

import java.util.List;

public interface SearchRemoteDataSourceListener extends  Failed, Loading {

    void onSearchSuccess(List<String> names);

    void onGetAllAreasSuccess(List<String> strings);

    void onGetAllIngredientSuccess(List<MinIngredient> ingredients);

    void onGetAllCategoriesSuccess(List<String> categories);
}
