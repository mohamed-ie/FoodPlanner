package com.soha.foodplanner.ui.planned;

import com.soha.foodplanner.data.local.entities.MealWithIngredient;
import com.soha.foodplanner.data.local.model.CompleteMeal;

import java.util.List;

public class WeekPlan {
    private String start;
    private String end;
    private final List<MealWithIngredient>[] weekMeals = new List[7];


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<MealWithIngredient>[] getWeekMeals() {
        return weekMeals;
    }
}
