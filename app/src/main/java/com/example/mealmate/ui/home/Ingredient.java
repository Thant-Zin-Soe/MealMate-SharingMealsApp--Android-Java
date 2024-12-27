package com.example.mealmate.ui.home;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients_table")
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int ingredientId;

    private String name;
    private String typeOfIngredient; // e.g., "Vegetables", "Grains"
    private double price;

    // Default constructor (optional if needed)
    public Ingredient() {
    }

    // Parameterized constructor
    public Ingredient(int ingredientId, String name, String typeOfIngredient, double price) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.typeOfIngredient = typeOfIngredient;
        this.price = price;
    }

    // Getters and Setters
    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeOfIngredient() {
        return typeOfIngredient;
    }

    public void setTypeOfIngredient(String typeOfIngredient) {
        this.typeOfIngredient = typeOfIngredient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
