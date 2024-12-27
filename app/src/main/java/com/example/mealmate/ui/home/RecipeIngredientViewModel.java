package com.example.mealmate.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class RecipeIngredientViewModel extends AndroidViewModel {
    private final RecipeIngredientDao recipeIngredientDao;

    public RecipeIngredientViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        recipeIngredientDao = database.recipeIngredientDao();
    }

    public void deleteIngredientsByRecipeId(int recipeId) {
        AppDatabase.databaseWriteExecutor.execute(() -> recipeIngredientDao.deleteIngredientsByRecipeId(recipeId));
    }
}
