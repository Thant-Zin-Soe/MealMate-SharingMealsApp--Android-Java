package com.example.mealmate.ui.home;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredients_table ORDER BY ingredientId ASC")
    LiveData<List<Ingredient>> getAllIngredients();

    // New method for inserting multiple ingredients at once
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(Ingredient... ingredients);

    @Query("SELECT COUNT(*) FROM ingredients_table")
    int getIngredientCount();

    @Query("SELECT ingredientId FROM ingredients_table WHERE name = :ingredientName LIMIT 1")
    int getIngredientIdByName(String ingredientName);


    @Query("SELECT quantity FROM recipe_ingredients_table WHERE ingredientId = :ingredientId LIMIT 1")
    String getQuantityByIngredientId(int ingredientId);


}
