package com.example.mealmate.ui.weeklyMealPlan;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weekly_meal_plan_table")
public class WeeklyMealPlan {
    @PrimaryKey(autoGenerate = true)
    private int planId;

    private int recipeId; // Foreign Key from MealRecipesTable
    private boolean isPurchased;

    // Getters and Setters
    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
