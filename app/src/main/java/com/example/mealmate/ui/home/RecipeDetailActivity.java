package com.example.mealmate.ui.home;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mealmate.R;
import com.example.mealmate.ui.weeklyMealPlan.WeeklyMealPlan;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private ImageView recipeImageView;
    private TextView recipeNameTextView, recipeInstructionTextView, recipeIngredientsTextView;
    private Button toggleInstructionButton, toggleIngredientsButton;
    private Button addToWPBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarRecipeDetail);
        setSupportActionBar(toolbar);


        // Retrieve recipeId from the Intent
        int recipeId = getIntent().getIntExtra("recipeId", -1); // Default to -1 if not passed
        if (recipeId == -1) {
            Toast.makeText(this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if no valid recipeId is provided
            return;
        }

        // Log the recipeId for debugging purposes
        Log.d("RecipeDetailActivity", "Retrieved recipeId: " + recipeId);

        // Enable back navigation
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize views
        recipeImageView = findViewById(R.id.recipeImageView);
        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeInstructionTextView = findViewById(R.id.recipeInstructionTextView);
        recipeIngredientsTextView = findViewById(R.id.recipeIngredientsTextView);
        toggleInstructionButton = findViewById(R.id.toggleInstructionButton);
        toggleIngredientsButton = findViewById(R.id.toggleIngredientsButton);
        addToWPBtn = findViewById(R.id.addToWPBtn);

        // Get data from the intent
        String recipeName = getIntent().getStringExtra("recipeName");
        String recipeInstruction = getIntent().getStringExtra("recipeInstruction");
        byte[] recipeImage = getIntent().getByteArrayExtra("recipeImage");
        List<String> ingredients = getIntent().getStringArrayListExtra("ingredients");

        // Set data to views
        recipeNameTextView.setText(recipeName);
        recipeInstructionTextView.setText(recipeInstruction);

        if (recipeImage != null) {
            recipeImageView.setImageBitmap(BitmapFactory.decodeByteArray(recipeImage, 0, recipeImage.length));
        }

        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder ingredientsText = new StringBuilder();
            for (String ingredient : ingredients) {
                ingredientsText.append("- ").append(ingredient).append("\n");
            }
            recipeIngredientsTextView.setText(ingredientsText.toString());
        } else {
            recipeIngredientsTextView.setText("No ingredients available");
        }

        // Toggle functionality for Instructions
        toggleInstructionButton.setOnClickListener(v -> {
            if (recipeInstructionTextView.getVisibility() == View.GONE) {
                recipeInstructionTextView.setVisibility(View.VISIBLE);
                toggleInstructionButton.setText("Hide Instructions");
            } else {
                recipeInstructionTextView.setVisibility(View.GONE);
                toggleInstructionButton.setText("Show Instructions");
            }
        });

        // Toggle functionality for Ingredients
        toggleIngredientsButton.setOnClickListener(v -> {
            if (recipeIngredientsTextView.getVisibility() == View.GONE) {
                recipeIngredientsTextView.setVisibility(View.VISIBLE);
                toggleIngredientsButton.setText("Hide Ingredients");
            } else {
                recipeIngredientsTextView.setVisibility(View.GONE);
                toggleIngredientsButton.setText("Show Ingredients");
            }
        });


// adding to the wewkly plan and also to the database table
        addToWPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the recipe ID from the intent or your database
                int recipeId = getIntent().getIntExtra("recipeId", -1); // Replace "recipeId" with the actual key for recipe ID
                if (recipeId == -1) {
                    Toast.makeText(RecipeDetailActivity.this, "Invalid recipe ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add the recipe to the weekly plan
                addRecipeToWeeklyPlan(recipeId);
            }
        });

    }


    private void addRecipeToWeeklyPlan(int recipeId) {
        new Thread(() -> {
            // Create a new WeeklyMealPlan object
            WeeklyMealPlan weeklyMealPlan = new WeeklyMealPlan();
            weeklyMealPlan.setRecipeId(recipeId);
            weeklyMealPlan.setPurchased(false); // Default state is not purchased

            // Insert the object into the database
            AppDatabase db = AppDatabase.getInstance(this);
            WeeklyMealPlan existingPlan = db.weeklyMealPlanDao().getPlanByRecipeId(recipeId);

            // Check if the recipe is already in the weekly plan
            if (existingPlan == null) {
                db.weeklyMealPlanDao().insert(weeklyMealPlan);

                // Update UI on the main thread
                runOnUiThread(() -> Toast.makeText(this, "Recipe added to Weekly Plan!", Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Recipe already in Weekly Plan!", Toast.LENGTH_SHORT).show());
            }
        }).start();

        finish();
    }


}
