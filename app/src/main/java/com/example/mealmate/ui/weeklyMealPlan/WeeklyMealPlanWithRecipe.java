package com.example.mealmate.ui.weeklyMealPlan;

public class WeeklyMealPlanWithRecipe {
    private int recipeId;       // Recipe ID
    private String name;        // Recipe name
    private String instruction; // Recipe instructions
    private byte[] photo;       // Recipe photo (optional)

    // Constructor
    public WeeklyMealPlanWithRecipe(int recipeId, String name, String instruction, byte[] photo) {
        this.recipeId = recipeId;
        this.name = name;
        this.instruction = instruction;
        this.photo = photo;
    }

    // Getters
    public int getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getInstruction() {
        return instruction;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
