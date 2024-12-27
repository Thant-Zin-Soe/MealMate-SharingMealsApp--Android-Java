package com.example.mealmate.ui.home;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface RecipeIngredientDao {
    @Insert
    void insert(RecipeIngredient recipeIngredient);

    @Delete
    void delete(RecipeIngredient recipeIngredient);

    @Query("SELECT * FROM recipe_ingredients_table WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);

    @Query("SELECT * FROM recipe_ingredients_table WHERE ingredientId = :ingredientId")
    List<RecipeIngredient> getRecipesForIngredient(int ingredientId);

    @Query("SELECT SUM(quantity) AS totalQuantity FROM recipe_ingredients_table WHERE ingredientId = :ingredientId")
    int getTotalQuantityForIngredient(int ingredientId);
    // NEW: Update purchase status for an ingredient
    @Query("UPDATE recipe_ingredients_table SET isPurchased = :isPurchased WHERE id = :id")
    void updateIngredientPurchaseStatus(int id, boolean isPurchased);

    // NEW: Get purchased ingredients for a recipe
    @Query("SELECT * FROM recipe_ingredients_table WHERE recipeId = :recipeId AND isPurchased = 1")
    List<RecipeIngredient> getPurchasedIngredientsForRecipe(int recipeId);

    // NEW: Get unpurchased ingredients for a recipe
    @Query("SELECT * FROM recipe_ingredients_table WHERE recipeId = :recipeId AND isPurchased = 0")
    List<RecipeIngredient> getUnpurchasedIngredientsForRecipe(int recipeId);

//    @Query("DELETE FROM recipe_ingredients_table WHERE recipeId = :recipeId")
//    void deleteIngredientsByRecipeId(int recipeId);


//    @Query("DELETE FROM recipe_ingredients_table WHERE recipeId = :recipeId AND ingredientId NOT IN (55, 56, 57, 58, 59)")
//    void deleteIngredientsByRecipeId(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipeIngredient(RecipeIngredient recipeIngredient);

    @Query("SELECT quantity FROM recipe_ingredients_table WHERE recipeId = :recipeId AND ingredientId = :ingredientId LIMIT 1")
    String getQuantityByRecipeAndIngredient(int recipeId, int ingredientId);

    @Query("DELETE FROM recipe_ingredients_table WHERE recipeId = :recipeId")
    void deleteIngredientsByRecipeId(int recipeId);

    @Query("SELECT ri.recipeId " +
            "FROM recipe_ingredients_table ri " +
            "INNER JOIN weekly_meal_plan_table wmp ON ri.recipeId = wmp.recipeId " +
            "WHERE ri.ingredientId = :ingredientId")
    List<Integer> getRecipeIdsForIngredient(int ingredientId);

    @Query("UPDATE recipe_ingredients_table " +
            "SET isPurchased = 1 " +
            "WHERE recipeId = :recipeId AND ingredientId = :ingredientId")
    void updateIsPurchased(int recipeId, int ingredientId);



    @Query("UPDATE recipe_ingredients_table SET isPurchased = :isPurchased WHERE recipeId = :recipeId AND ingredientId = :ingredientId")
    void updateIsPurchased(int recipeId, int ingredientId, int isPurchased);









}
