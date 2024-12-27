package com.example.mealmate.ui.home;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mealmate.R;
import java.util.ArrayList;
import java.util.List;

public class MealRecipeAdapter extends RecyclerView.Adapter<MealRecipeAdapter.RecipeViewHolder> {

    private final List<MealRecipe> recipeList = new ArrayList<>();
    private OnRecipeClickListener onRecipeClickListener;

    // Setter for the click listener
    public void setOnRecipeClickListener(OnRecipeClickListener listener) {
        this.onRecipeClickListener = listener;
    }

    public void setRecipeList(List<MealRecipe> recipes) {
        recipeList.clear();
        recipeList.addAll(recipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        MealRecipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        holder.recipeInstruction.setText(recipe.getInstruction());

        // Optional: Set recipe image if available
        if (recipe.getPhoto() != null) {
            holder.recipeImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(recipe.getPhoto(), 0, recipe.getPhoto().length)
            );
        }

        // Set the click listener
        holder.itemView.setOnClickListener(v -> {
            if (onRecipeClickListener != null) {
                onRecipeClickListener.onRecipeClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public MealRecipe getRecipeAt(int position) {
        return recipeList.get(position);
    }

    // Interface for recipe click listener
    public interface OnRecipeClickListener {
        void onRecipeClick(MealRecipe recipe);
    }

    // ViewHolder for the recipe item
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeInstruction;
        ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.textViewRecipeName);
            recipeInstruction = itemView.findViewById(R.id.textViewInstruction);
            recipeImage = itemView.findViewById(R.id.imageView);
        }
    }
}
