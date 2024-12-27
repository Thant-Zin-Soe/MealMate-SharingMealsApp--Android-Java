package com.example.mealmate;

import androidx.room.ColumnInfo;

public class IngredientGroupedByType {

    @ColumnInfo(name = "ingredient_type")
    private String ingredientType;

    @ColumnInfo(name = "ingredient_name")
    private String ingredientName;

    @ColumnInfo(name = "ingredient_id")
    private int ingredientId;

    @ColumnInfo(name = "total_quantity")
    private double totalQuantity;

    @ColumnInfo(name = "isPurchased")
    private boolean isPurchased;

    // Default constructor
    public IngredientGroupedByType() {
        // Default constructor for Room or any deserialization needs
    }

    // Parameterized constructor
    public IngredientGroupedByType(int ingredientId, String ingredientName, String ingredientType, double totalQuantity, boolean isPurchased) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientType = ingredientType;
        this.totalQuantity = totalQuantity;
        this.isPurchased = isPurchased;
    }

    // Getters and Setters
    public String getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
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

    public void setPurchased(boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    @Override
    public String toString() {
        return "IngredientGroupedByType{" +
                "ingredientType='" + ingredientType + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientId=" + ingredientId +
                ", totalQuantity=" + totalQuantity +
                ", isPurchased=" + isPurchased +
                '}';
    }
}
