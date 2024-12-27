package com.example.mealmate.ui.weeklyMealPlan;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.mealmate.IngredientGroupedByType;

import java.util.List;

@Dao
public interface WeeklyMealPlanDao {
    @Insert
    void insert(WeeklyMealPlan weeklyMealPlan);

    @Update
    void update(WeeklyMealPlan weeklyMealPlan);

    @Delete
    void delete(WeeklyMealPlan weeklyMealPlan);

    @Query("SELECT * FROM weekly_meal_plan_table ORDER BY planId ASC")
    LiveData<List<WeeklyMealPlan>> getAllPlans();

    @Query("SELECT * FROM weekly_meal_plan_table WHERE isPurchased = 1")
    LiveData<List<WeeklyMealPlan>> getPurchasedPlans();

    @Query("SELECT * FROM weekly_meal_plan_table WHERE recipeId = :recipeId")
    WeeklyMealPlan getPlanByRecipeId(int recipeId);



//    @Query("SELECT mr.name AS name, mr.instruction AS instruction, mr.photo AS photo " +
//            "FROM weekly_meal_plan_table AS wmp " +
//            "INNER JOIN meal_recipes_table AS mr ON wmp.recipeId = mr.recipeId " +
//            "ORDER BY wmp.planId ASC")
//    LiveData<List<WeeklyMealPlanWithRecipe>> getAllWeeklyMealPlansWithRecipes();

    @Query("SELECT wmp.recipeId AS recipeId, mr.name AS name, mr.instruction AS instruction, mr.photo AS photo " +
            "FROM weekly_meal_plan_table AS wmp " +
            "INNER JOIN meal_recipes_table AS mr ON wmp.recipeId = mr.recipeId " +
            "ORDER BY wmp.planId ASC")
    LiveData<List<WeeklyMealPlanWithRecipe>> getAllWeeklyMealPlansWithRecipes();


    @Query("DELETE FROM weekly_meal_plan_table WHERE recipeId = :recipeId")
    void deleteMealPlanByRecipeId(int recipeId);




//    @Query("SELECT mr.recipeId, mr.name AS recipeName, mr.instruction, mr.photo, ing.name AS ingredientName " +
//            "FROM meal_recipes_table AS mr " +
//            "INNER JOIN recipe_ingredients_table AS ri ON mr.recipeId = ri.recipeId " +
//            "INNER JOIN ingredients_table AS ing ON ri.ingredientId = ing.ingredientId " +
//            "WHERE mr.recipeId = :recipeId")
//    List<RecipeWithIngredients> getRecipeWithIngredients(int recipeId);

//

    @Query("SELECT mr.recipeId, mr.name AS recipeName, mr.instruction, mr.photo, mr.type AS recipeType, " +
            "i.name AS ingredientName " +
            "FROM meal_recipes_table mr " +
            "INNER JOIN recipe_ingredients_table ri ON mr.recipeId = ri.recipeId " +
            "INNER JOIN ingredients_table i ON ri.ingredientId = i.ingredientId " +
            "WHERE mr.recipeId = :recipeId")
    List<RecipeWithIngredients> getRecipeWithIngredients(int recipeId);



//    @Query("SELECT i.typeOfIngredient AS ingredient_type, " +
//            "i.name AS ingredient_name, " +
//            "SUM(ri.quantity) AS total_quantity " +
//            "FROM recipe_ingredients_table ri " +
//            "JOIN weekly_meal_plan_table wmp ON ri.recipeId = wmp.recipeId " +
//            "JOIN ingredients_table i ON ri.ingredientId = i.ingredientId " +
//            "GROUP BY i.typeOfIngredient, ri.ingredientId " +
//            "ORDER BY i.typeOfIngredient")
//    LiveData<List<IngredientGroupedByType>> getIngredientsGroupedByType();




    @Query("SELECT DISTINCT recipeId FROM recipe_ingredients_table WHERE ingredientId = :ingredientId")
    List<Integer> getRecipeIdsForIngredient(int ingredientId);

    @Query("UPDATE recipe_ingredients_table SET isPurchased = :isPurchased WHERE recipeId = :recipeId AND ingredientId = :ingredientId")
    void updateIsPurchased(int recipeId, int ingredientId, int isPurchased);


    @Query("SELECT i.typeOfIngredient AS ingredient_type, " +
            "i.name AS ingredient_name, " +
            "ri.ingredientId AS ingredient_id, " +
            "SUM(CAST(ri.quantity AS REAL)) AS total_quantity, " +
            "MAX(ri.isPurchased) AS isPurchased " +
            "FROM weekly_meal_plan_table wmp " +
            "INNER JOIN recipe_ingredients_table ri ON wmp.recipeId = ri.recipeId " +
            "INNER JOIN ingredients_table i ON ri.ingredientId = i.ingredientId " +
            "GROUP BY i.typeOfIngredient, ri.ingredientId " +
            "ORDER BY i.typeOfIngredient")
    LiveData<List<IngredientGroupedByType>> getIngredientsGroupedByType();



}
