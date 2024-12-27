package com.example.mealmate.ui.weeklyMealPlan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

public class WeeklyMealPlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeeklyMealPlanAdapter adapter;
    private WeeklyMealPlanViewModel weeklyMealPlanViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weekly_meal_plan, container, false);

        // Initialize RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewWeeklyMealPlan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Adapter
        adapter = new WeeklyMealPlanAdapter();
        recyclerView.setAdapter(adapter);

        // Set up click listener for items in the adapter
        adapter.setOnRecipeClickListener(recipeId -> {
            // Navigate to RecipeDetailActivity
            Intent intent = new Intent(getContext(), UpdateWeeklyMealPlanActivity.class);
            intent.putExtra("RECIPE_ID", recipeId); // Pass the recipeId to the new activity
            startActivity(intent);
        });

        // Initialize ViewModel and observe data
        weeklyMealPlanViewModel = new ViewModelProvider(this).get(WeeklyMealPlanViewModel.class);
        weeklyMealPlanViewModel.getWeeklyMealPlans().observe(getViewLifecycleOwner(), mealPlans -> {
            adapter.setMealPlans(mealPlans);
        });

        // Enable swipe-to-delete functionality
        enableSwipeToDelete();

        return root;
    }

    private void enableSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // No drag functionality
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Get the swiped item from the adapter
                WeeklyMealPlanWithRecipe mealPlanToDelete = adapter.getMealPlanAt(viewHolder.getAdapterPosition());

                // Call the ViewModel to delete the meal plan by recipeId
                weeklyMealPlanViewModel.deleteMealPlanByRecipeId(mealPlanToDelete.getRecipeId());

                // Show confirmation to the user
                Toast.makeText(requireContext(), "Recipe removed from Weekly Plan", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
