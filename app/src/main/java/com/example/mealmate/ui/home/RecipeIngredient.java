//package com.example.mealmate.ui.home;
//
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "recipe_ingredients_table")
//public class RecipeIngredient {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    private int recipeId; // Foreign Key from MealRecipesTable
//    private int ingredientId; // Foreign Key from IngredientsTable
//    private String quantity; // e.g., "2 pieces", "500g"
//
//    private boolean isPurchased;
//
//    // Getters and Setters
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getRecipeId() {
//        return recipeId;
//    }
//
//    public void setRecipeId(int recipeId) {
//        this.recipeId = recipeId;
//    }
//
//    public int getIngredientId() {
//        return ingredientId;
//    }
//
//    public void setIngredientId(int ingredientId) {
//        this.ingredientId = ingredientId;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }
//
//    // Getters and Setters
//    public boolean isPurchased() {
//        return isPurchased;
//    }
//
//    public void setPurchased(boolean purchased) {
//        isPurchased = purchased;
//    }
//
//}


package com.example.mealmate.ui.home;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_ingredients_table")
public class RecipeIngredient {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int recipeId; // Foreign Key from MealRecipesTable
    private int ingredientId; // Foreign Key from IngredientsTable
    private String quantity; // e.g., "2 pieces", "500g"

    private boolean isPurchased;

    // Default Constructor
    public RecipeIngredient() {
    }

    // Constructor for initialization
    public RecipeIngredient(int recipeId, int ingredientId, String quantity, boolean isPurchased) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.isPurchased = isPurchased;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}