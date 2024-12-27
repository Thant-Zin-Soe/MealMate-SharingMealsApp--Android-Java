package com.example.mealmate;

public class IngredientSummary {

    private int ingredientId; // Unique ID of the ingredient
    private String ingredientName; // Name of the ingredient
    private double totalQuantity; // Total quantity across all recipes
    private boolean isPurchased; // Purchase status

    // Constructor
    public IngredientSummary(int ingredientId, String ingredientName, double totalQuantity, boolean isPurchased) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.totalQuantity = totalQuantity;
        this.isPurchased = isPurchased;
    }

    // Getters and Setters
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
