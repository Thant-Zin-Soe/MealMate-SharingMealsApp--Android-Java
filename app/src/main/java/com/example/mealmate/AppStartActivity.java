package com.example.mealmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.ui.home.AppDatabase;
import com.example.mealmate.ui.home.Ingredient;
import com.example.mealmate.ui.home.MealRecipe;
import com.example.mealmate.ui.home.RecipeIngredient;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class AppStartActivity extends AppCompatActivity {
    EditText mail;
    EditText password;
    Button signIn;
    Button signUp;
    Button forgotPassword;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    AppDatabase db;

    // Initialize UI elements


    // Initialize the database and insert test data
    //initializeDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        mail = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        signIn = findViewById(R.id.buttonSingIn);
        signUp = findViewById(R.id.buttonSignUp);
        forgotPassword = findViewById(R.id.buttonForgot);

        db = AppDatabase.getInstance(this);

        insertPredefinedIngredients();

        // Example Usage: Add Recipe and Link Ingredients
//        new Thread(() -> {
//            long recipeId = insertRecipe("Beef Curry", "Beef", null, "Cook beef with spices.");
//            List<Integer> selectedIngredientIds = List.of(1, 2, 3); // Example ingredient IDs
//            List<String> quantities = List.of("500g", "200g", "1kg"); // Example quantities
//            linkIngredientsToRecipe((int) recipeId, selectedIngredientIds, quantities);
//        }).start();

        // Sign-in button click listener
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = mail.getText().toString().trim();
                String userPassword = password.getText().toString().trim();

                if (userMail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(AppStartActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    signInFirebase(userMail, userPassword);
                }
            }
        });

        // Sign-up button click listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppStartActivity.this, SignUp.class);
                startActivity(i);
                finish();
            }
        });

        // Forgot password button click listener
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppStartActivity.this, ForgetActivity.class);
                startActivity(i);
            }
        });

    }

    private void insertPredefinedIngredients() {
        AppDatabase db = AppDatabase.getInstance(this);

        // Run this in a background thread to avoid blocking the UI
        new Thread(() -> {
            // Check if the table already has data
            if (db.ingredientDao().getIngredientCount() == 0) {
                Ingredient[] ingredients = {
                        new Ingredient(0, "Carrot", "Vegetable", 0.50),
                        new Ingredient(0, "Onion", "Vegetable", 0.30),
                        new Ingredient(0, "Potato", "Vegetable", 0.20),
                        new Ingredient(0, "Tomato", "Vegetable", 0.40),
                        new Ingredient(0, "Spinach", "Vegetable", 0.90),
                        new Ingredient(0, "Broccoli", "Vegetable", 1.20),
                        new Ingredient(0, "Cauliflower", "Vegetable", 1.00),
                        new Ingredient(0, "Cucumber", "Vegetable", 0.60),
                        new Ingredient(0, "Zucchini", "Vegetable", 0.70),
                        new Ingredient(0, "Capsicum (Bell Pepper)", "Vegetable", 0.80),
                        new Ingredient(0, "Cabbage", "Vegetable", 0.55),
                        new Ingredient(0, "Eggplant", "Vegetable", 0.85),
                        new Ingredient(0, "Okra", "Vegetable", 0.65),
                        new Ingredient(0, "Sweet Corn", "Vegetable", 1.50),
                        new Ingredient(0, "Green Peas", "Vegetable", 1.40),
                        new Ingredient(0, "Pumpkin", "Vegetable", 0.75),
                        new Ingredient(0, "Radish", "Vegetable", 0.35),
                        new Ingredient(0, "Beetroot", "Vegetable", 0.50),
                        new Ingredient(0, "Celery", "Vegetable", 0.60),
                        new Ingredient(0, "Garlic", "Vegetable", 0.20),
                        new Ingredient(0, "Ginger", "Vegetable", 0.25),
                        new Ingredient(0, "Mushroom", "Vegetable", 1.80),
                        new Ingredient(0, "Chili", "Vegetable", 0.15),
                        new Ingredient(0, "Spring Onion", "Vegetable", 0.45),
                        new Ingredient(0, "Rice", "Grain", 1.50),
                        new Ingredient(0, "Wheat", "Grain", 1.20),
                        new Ingredient(0, "Oats", "Grain", 1.00),
                        new Ingredient(0, "Quinoa", "Grain", 2.50),
                        new Ingredient(0, "Millet", "Grain", 1.30),
                        new Ingredient(0, "Buckwheat", "Grain", 2.00),
                        new Ingredient(0, "Amaranth", "Grain", 2.20),
                        new Ingredient(0, "Farro", "Grain", 1.70),
                        new Ingredient(0, "Spelt", "Grain", 1.60),
                        new Ingredient(0, "Teff", "Grain", 2.80),
                        new Ingredient(0, "Couscous", "Grain", 1.40),
                        new Ingredient(0, "Sorghum", "Grain", 1.10),
                        new Ingredient(0, "Barley Flakes", "Grain", 1.20),
                        new Ingredient(0, "Barley", "Grain", 1.00),
                        new Ingredient(0, "Corn", "Grain", 0.90),
                        new Ingredient(0, "Salt", "Spice", 0.10),
                        new Ingredient(0, "Cinnamon", "Spice", 0.50),
                        new Ingredient(0, "Clove", "Spice", 0.60),
                        new Ingredient(0, "Nutmeg", "Spice", 0.75),
                        new Ingredient(0, "Cardamom", "Spice", 0.90),
                        new Ingredient(0, "Coriander", "Spice", 0.40),
                        new Ingredient(0, "Bay Leaf", "Spice", 0.35),
                        new Ingredient(0, "Star Anise", "Spice", 0.80),
                        new Ingredient(0, "Fenugreek", "Spice", 0.50),
                        new Ingredient(0, "Mustard Seeds", "Spice", 0.30),
                        new Ingredient(0, "Chili Flakes", "Spice", 0.25),
                        new Ingredient(0, "Pepper", "Spice", 0.45),
                        new Ingredient(0, "Cumin", "Spice", 0.50),
                        new Ingredient(0, "Turmeric", "Spice", 0.55),
                        new Ingredient(0, "Paprika", "Spice", 0.40),
                        new Ingredient(0, "Chicken", "Meat", 5.00),
                        new Ingredient(0, "Beef", "Meat", 6.50),
                        new Ingredient(0, "Pork", "Meat", 4.50),
                        new Ingredient(0, "Fish", "Meat", 7.00),
                        new Ingredient(0, "Lamb", "Meat", 8.00)

                        // Add more ingredients here
                };

                db.ingredientDao().insertIngredients(ingredients);
            }
        }).start();
    }

    // Update Purchase Status
    private void updatePurchaseStatus(int ingredientId, boolean isPurchased) {
        new Thread(() -> {
            db.recipeIngredientDao().updateIngredientPurchaseStatus(ingredientId, isPurchased);
        }).start();
    }
//Retrieve Purchased Ingredients
    private void getPurchasedIngredientsForRecipe(int recipeId) {
        new Thread(() -> {
            List<RecipeIngredient> purchasedIngredients = db.recipeIngredientDao().getPurchasedIngredientsForRecipe(recipeId);
            runOnUiThread(() -> {
                // Use the purchased ingredients (e.g., display in UI)
                Toast.makeText(this, "Purchased: " + purchasedIngredients.size(), Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    //Retrieve Unpurchased Ingredients
    private void getUnpurchasedIngredientsForRecipe(int recipeId) {
        new Thread(() -> {
            List<RecipeIngredient> unpurchasedIngredients = db.recipeIngredientDao().getUnpurchasedIngredientsForRecipe(recipeId);
            runOnUiThread(() -> {
                // Use the unpurchased ingredients (e.g., display in UI)
                Toast.makeText(this, "Unpurchased: " + unpurchasedIngredients.size(), Toast.LENGTH_SHORT).show();
            });
        }).start();
    }





    private long insertRecipe(String name, String type, byte[] photo, String instruction) {
        MealRecipe recipe = new MealRecipe();
        recipe.setName(name);
        recipe.setType(type);
        recipe.setPhoto(photo);
        recipe.setInstruction(instruction);
        return db.mealRecipeDao().insert(recipe);
    }

    private void linkIngredientsToRecipe(int recipeId, List<Integer> ingredientIds, List<String> quantities) {
        for (int i = 0; i < ingredientIds.size(); i++) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setIngredientId(ingredientIds.get(i));
            recipeIngredient.setQuantity(quantities.get(i));
            db.recipeIngredientDao().insert(recipeIngredient);
        }
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public void signInFirebase(String userMail, String userPassword) {
        auth.signInWithEmailAndPassword(userMail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(AppStartActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(AppStartActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent i = new Intent(AppStartActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
