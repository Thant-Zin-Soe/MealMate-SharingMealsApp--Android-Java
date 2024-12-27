package com.example.mealmate.ui.weeklyMealPlan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;

import java.util.ArrayList;
import java.util.List;

public class WeeklyMealPlanAdapter extends RecyclerView.Adapter<WeeklyMealPlanAdapter.ViewHolder> {

    private List<WeeklyMealPlanWithRecipe> mealPlans = new ArrayList<>();
    private OnRecipeClickListener listener;

    // Interface for click handling
    public interface OnRecipeClickListener {
        void onRecipeClick(int recipeId); // Method to pass the clicked recipeId
    }

    // Method to set the listener
    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_meal_plan_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeeklyMealPlanWithRecipe mealPlan = mealPlans.get(position);

        // Bind recipe name and instruction
        holder.recipeNameTextView.setText(mealPlan.getName());
        holder.recipeInstructionTextView.setText(mealPlan.getInstruction());

        // Load the image
        if (mealPlan.getPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mealPlan.getPhoto(), 0, mealPlan.getPhoto().length);
            holder.recipeImageView.setImageBitmap(bitmap);
        } else {
            holder.recipeImageView.setImageResource(R.drawable.ic_launcher_foreground); // Placeholder
        }

        // Set item click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(mealPlan.getRecipeId()); // Pass recipeId to listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealPlans.size();
    }

    public WeeklyMealPlanWithRecipe getMealPlanAt(int position) {
        return mealPlans.get(position);
    }

    public void setMealPlans(List<WeeklyMealPlanWithRecipe> mealPlans) {
        this.mealPlans = mealPlans;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView recipeImageView;
        private final TextView recipeNameTextView;
        private final TextView recipeInstructionTextView;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            recipeImageView = itemView.findViewById(R.id.imageView);
            recipeNameTextView = itemView.findViewById(R.id.textViewRecipeName);
            recipeInstructionTextView = itemView.findViewById(R.id.textViewInstruction);
        }
    }
}
