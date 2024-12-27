package com.example.mealmate.ui.weeklyMealPlan;
//
//public class RecipeWithIngredients {
//    private int recipeId;
//    private String recipeName;
//    private String instruction;
//    private byte[] photo;
//    private String ingredientName; // Ingredient for this recipe
//
//    // Constructor
//    public RecipeWithIngredients(int recipeId, String recipeName, String instruction, byte[] photo, String ingredientName) {
//        this.recipeId = recipeId;
//        this.recipeName = recipeName;
//        this.instruction = instruction;
//        this.photo = photo;
//        this.ingredientName = ingredientName;
//    }
//
//    // Getters
//    public int getRecipeId() {
//        return recipeId;
//    }
//
//    public String getRecipeName() {
//        return recipeName;
//    }
//
//    public String getInstruction() {
//        return instruction;
//    }
//
//    public byte[] getPhoto() {
//        return photo;
//    }
//
//    public String getIngredientName() {
//        return ingredientName;
//    }
//}
public class RecipeWithIngredients {
    private int recipeId;       // Recipe ID
    private String recipeName;  // Recipe Name
    private String instruction; // Recipe Instructions
    private byte[] photo;       // Recipe Photo
    private String recipeType;  // Recipe Type (New Field)
    private String ingredientName; // Ingredient Name

    // Getters and Setters
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
