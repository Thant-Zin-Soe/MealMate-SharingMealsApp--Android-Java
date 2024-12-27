package com.example.mealmate.ui.home;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealRecipeRepository {
    private final MealRecipeDao mealRecipeDao;
    private final LiveData<List<MealRecipe>> allRecipes;
    private final ExecutorService executorService;

    public MealRecipeRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mealRecipeDao = database.mealRecipeDao();
        allRecipes = mealRecipeDao.getAllRecipes();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(MealRecipe recipe) {
        executorService.execute(() -> mealRecipeDao.insert(recipe));
    }

    public void update(MealRecipe recipe) {
        executorService.execute(() -> mealRecipeDao.update(recipe));
    }

    public void delete(MealRecipe recipe) {
        executorService.execute(() -> mealRecipeDao.delete(recipe));
    }

    public LiveData<List<MealRecipe>> getAllRecipes() {
        return allRecipes;
    }

    public LiveData<List<MealRecipe>> getRecipesByType(String recipeType) {
        return mealRecipeDao.getRecipesByType(recipeType);
    }

    // Fetch ingredients for a specific recipe
    public List<String> getIngredientsForRecipe(int recipeId) {
        return mealRecipeDao.getIngredientsForRecipe(recipeId);
    }
}
