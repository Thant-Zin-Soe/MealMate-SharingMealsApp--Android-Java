package com.example.mealmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface MealRecipeDao {

    // Insert a new MealRecipe and return the generated recipeId
    @Insert
    long insert(MealRecipe mealRecipe);

    @Update
    void update(MealRecipe mealRecipe);

    @Delete
    void delete(MealRecipe mealRecipe);

    @Query("SELECT * FROM MEAL_RECIPES_TABLE ORDER BY recipeId ASC")
    LiveData<List<MealRecipe>> getAllRecipes();

    @Query("SELECT * FROM MEAL_RECIPES_TABLE WHERE type = :recipeType ORDER BY recipeId ASC")
    LiveData<List<MealRecipe>> getRecipesByType(String recipeType);

    // New method to fetch ingredients for a specific recipe
    @Query("SELECT name FROM ingredients_table " +
            "INNER JOIN recipe_ingredients_table " +
            "ON ingredients_table.ingredientId = recipe_ingredients_table.ingredientId " +
            "WHERE recipe_ingredients_table.recipeId = :recipeId")
    List<String> getIngredientsForRecipe(int recipeId);
    @Query("UPDATE meal_recipes_table SET name = :recipeName, instruction = :instruction WHERE recipeId = :recipeId")
    void updateRecipeNameAndInstruction(int recipeId, String recipeName, String instruction);


}
