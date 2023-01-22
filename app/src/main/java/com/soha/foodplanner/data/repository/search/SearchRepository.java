package com.soha.foodplanner.data.repository.search;

public interface SearchRepository {

    void searchByFirstLetter(char c);

    void getAllAres();

    void getAllIngredients();

    void getAllCategories();
}
