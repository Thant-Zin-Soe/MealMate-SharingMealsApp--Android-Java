//package com.example.mealmate.ui.weeklyMealPlan;
//
//import android.app.Application;
//
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.mealmate.ui.home.AppDatabase;
//
//import java.util.List;
//
//public class WeeklyMealPlanViewModel extends AndroidViewModel {
//
//    // LiveData to hold the list of weekly meal plans with recipes
//    private final LiveData<List<WeeklyMealPlanWithRecipe>> weeklyMealPlans;
//
//    public WeeklyMealPlanViewModel(Application application) {
//        super(application);
//
//        // Initialize the database instance and DAO
//        AppDatabase database = AppDatabase.getInstance(application);
//        WeeklyMealPlanDao weeklyMealPlanDao = database.weeklyMealPlanDao();
//
//        // Fetch the weekly meal plans joined with recipe details
//        weeklyMealPlans = weeklyMealPlanDao.getAllWeeklyMealPlansWithRecipes();
//    }
//
//    /**
//     * Returns the LiveData containing the list of weekly meal plans with recipes.
//     * This will be observed by the fragment to update the UI automatically when data changes.
//     */
//    public LiveData<List<WeeklyMealPlanWithRecipe>> getWeeklyMealPlans() {
//        return weeklyMealPlans;
//    }
//
//    /**
//     * Deletes a specific meal plan from the weekly meal plan table by recipeId.
//     * This method runs the delete operation on a background thread.
//     */
//    public void deleteMealPlanByRecipeId(int recipeId) {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            AppDatabase.getInstance(getApplication())
//                    .weeklyMealPlanDao()
//                    .deleteMealPlanByRecipeId(recipeId);
//        });
//    }
//
//    public LiveData<List<RecipeWithIngredients>> getRecipeWithIngredients(int planId) {
//        MutableLiveData<List<RecipeWithIngredients>> data = new MutableLiveData<>();
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            data.postValue(AppDatabase.getInstance(getApplication()).weeklyMealPlanDao().getRecipeWithIngredients(planId));
//        });
//        return data;
//    }
//
//
//
//
//}
package com.example.mealmate.ui.weeklyMealPlan;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mealmate.IngredientGroupedByType;
import com.example.mealmate.ui.home.AppDatabase;

import java.util.List;

public class WeeklyMealPlanViewModel extends AndroidViewModel {

    // LiveData to hold the list of weekly meal plans with recipes
    private final LiveData<List<WeeklyMealPlanWithRecipe>> weeklyMealPlans;
    private final WeeklyMealPlanDao weeklyMealPlanDao; // Ensure this is final and properly assigned

    public WeeklyMealPlanViewModel(Application application) {
        super(application);

        // Initialize the database instance and DAO
        AppDatabase database = AppDatabase.getInstance(application);
        weeklyMealPlanDao = database.weeklyMealPlanDao(); // Assign to the class-level field

        // Fetch the weekly meal plans joined with recipe details
        weeklyMealPlans = weeklyMealPlanDao.getAllWeeklyMealPlansWithRecipes();
    }

    public void updateIngredientPurchaseState(int ingredientId, boolean isPurchased) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            if (weeklyMealPlanDao != null) { // Add a null check for safety
                // Fetch recipe IDs associated with the ingredient
                List<Integer> recipeIds = weeklyMealPlanDao.getRecipeIdsForIngredient(ingredientId);
                int isPurchasedValue = isPurchased ? 1 : 0;

                // Update isPurchased for each associated recipe
                for (int recipeId : recipeIds) {
                    weeklyMealPlanDao.updateIsPurchased(recipeId, ingredientId, isPurchasedValue);
                }
            } else {
                throw new IllegalStateException("WeeklyMealPlanDao is null!");
            }
        });
    }

    /**
     * Returns the LiveData containing the list of weekly meal plans with recipes.
     * This will be observed by the fragment to update the UI automatically when data changes.
     */
    public LiveData<List<WeeklyMealPlanWithRecipe>> getWeeklyMealPlans() {
        return weeklyMealPlans;
    }

    /**
     * Deletes a specific meal plan from the weekly meal plan table by recipeId.
     * This method runs the delete operation on a background thread.
     */
    public void deleteMealPlanByRecipeId(int recipeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            weeklyMealPlanDao.deleteMealPlanByRecipeId(recipeId);
        });
    }

    /**
     * Fetches the recipes with their ingredients for a specific plan ID.
     * @param planId The ID of the weekly meal plan.
     * @return LiveData containing the list of recipes with their ingredients.
     */
    public LiveData<List<RecipeWithIngredients>> getRecipeWithIngredients(int planId) {
        MutableLiveData<List<RecipeWithIngredients>> data = new MutableLiveData<>();
        AppDatabase.databaseWriteExecutor.execute(() -> {
            data.postValue(weeklyMealPlanDao.getRecipeWithIngredients(planId));
        });
        return data;
    }

    /**
     * Fetches the ingredients grouped by type from the weekly meal plan table.
     * @return LiveData containing the grouped ingredients with their total quantities.
     */
    public LiveData<List<IngredientGroupedByType>> getIngredientsGroupedByType() {
        return weeklyMealPlanDao.getIngredientsGroupedByType();
    }
}
