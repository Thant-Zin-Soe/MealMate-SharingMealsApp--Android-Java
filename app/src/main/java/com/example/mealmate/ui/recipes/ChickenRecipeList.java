package com.example.mealmate.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.ui.home.AppDatabase;
import com.example.mealmate.ui.home.MealRecipe;
import com.example.mealmate.ui.home.MealRecipeAdapter;
import com.example.mealmate.ui.home.MealRecipeViewModel;
import com.example.mealmate.ui.home.RecipeDetailActivity;
import com.example.mealmate.ui.home.RecipeIngredientViewModel;

import java.util.ArrayList;
import java.util.List;
public class ChickenRecipeList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MealRecipeAdapter recipeAdapter;
    private MealRecipeViewModel mealRecipeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_recipe_list);

        // Set up Toolbar with Back Arrow
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbarChickenRecipes);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Chicken Recipes");
        }



        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeAdapter = new MealRecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);

        // Initialize ViewModel
        mealRecipeViewModel = new ViewModelProvider(this).get(MealRecipeViewModel.class);

        // Observe filtered recipes
        mealRecipeViewModel.getRecipesByType("Chicken").observe(this, recipes -> {
            if (recipes != null) {
                recipeAdapter.setRecipeList(recipes);
                recyclerView.scrollToPosition(0);
            }
        });

        // Set up click listener for recipe details
        recipeAdapter.setOnRecipeClickListener(recipe -> {
            new Thread(() -> {
                List<String> ingredients = mealRecipeViewModel.getIngredientsForRecipe(recipe.getRecipeId());

                runOnUiThread(() -> {
                    Intent intent = new Intent(ChickenRecipeList.this, RecipeDetailActivity.class);
                    intent.putExtra("recipeId", recipe.getRecipeId());
                    intent.putExtra("recipeName", recipe.getName());
                    intent.putExtra("recipeInstruction", recipe.getInstruction());
                    intent.putExtra("recipeImage", recipe.getPhoto());

                    if (ingredients != null && !ingredients.isEmpty()) {
                        intent.putStringArrayListExtra("ingredients", new ArrayList<>(ingredients));
                    } else {
                        intent.putStringArrayListExtra("ingredients", new ArrayList<>(List.of("No ingredients available")));
                    }
                    startActivity(intent);
                });
            }).start();
        });

        // Add Swipe to Delete functionality
        enableSwipeToDelete();
    }

//    private void enableSwipeToDelete() {
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false; // No drag functionality
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                MealRecipe recipeToDelete = recipeAdapter.getRecipeAt(viewHolder.getAdapterPosition());
//                mealRecipeViewModel.delete(recipeToDelete);
//                Toast.makeText(ChickenRecipeList.this, "Recipe deleted", Toast.LENGTH_SHORT).show();
//                recipeAdapter.notifyDataSetChanged();
//            }
//        }).attachToRecyclerView(recyclerView);
//    }

    private void enableSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // No drag functionality
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                MealRecipe recipeToDelete = recipeAdapter.getRecipeAt(viewHolder.getAdapterPosition());

                // Delete the recipe from meal_recipes_table
                mealRecipeViewModel.delete(recipeToDelete);



                // Delete associated ingredients from recipe_ingredients_table
//                AppDatabase.databaseWriteExecutor.execute(() -> {
//                    AppDatabase.getInstance(ChickenRecipeList.this)
//                            .recipeIngredientDao()
//                            .deleteIngredientsByRecipeId(recipeToDelete.getRecipeId());
//                });

                Toast.makeText(ChickenRecipeList.this, "Recipe and its ingredients deleted", Toast.LENGTH_SHORT).show();
                recipeAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(recyclerView);
    }

}
