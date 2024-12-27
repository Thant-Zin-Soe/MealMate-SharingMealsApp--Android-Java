package com.example.mealmate.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MealRecipeViewModel extends AndroidViewModel {
    private final MealRecipeRepository repository;
    private final LiveData<List<MealRecipe>> allRecipes;

    public MealRecipeViewModel(Application application) {
        super(application);
        repository = new MealRecipeRepository(application);
        allRecipes = repository.getAllRecipes();
    }

    public void insert(MealRecipe recipe) {
        repository.insert(recipe);
    }

    public void update(MealRecipe recipe) {
        repository.update(recipe);
    }

    public void delete(MealRecipe recipe) {
        repository.delete(recipe);
    }

    public LiveData<List<MealRecipe>> getAllRecipes() {
        return allRecipes;
    }

    public LiveData<List<MealRecipe>> getRecipesByType(String recipeType) {
        return repository.getRecipesByType(recipeType);
    }

    // Fetch ingredients for a specific recipe
    public List<String> getIngredientsForRecipe(int recipeId) {
        return repository.getIngredientsForRecipe(recipeId);
    }
}
