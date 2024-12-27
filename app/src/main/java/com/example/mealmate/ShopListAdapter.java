package com.example.mealmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.R;
import com.example.mealmate.IngredientSummary;

import java.util.ArrayList;
import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private List<IngredientSummary> ingredientList = new ArrayList<>();
    private final OnIngredientCheckedListener listener;

    // Constructor for the adapter
    public ShopListAdapter(OnIngredientCheckedListener listener) {
        this.listener = listener;
    }

    // Method to set ingredient list and notify data changes
    public void setIngredientList(List<IngredientSummary> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientSummary ingredient = ingredientList.get(position);

        // Bind ingredient name and total quantity
        holder.ingredientName.setText(ingredient.getIngredientName());
        holder.quantity.setText(String.valueOf(ingredient.getTotalQuantity()));

        // Set the initial state of the CheckBox
        holder.purchaseCheckbox.setOnCheckedChangeListener(null); // Clear previous listeners
        holder.purchaseCheckbox.setChecked(ingredient.isPurchased());

        // Handle CheckBox change events
        holder.purchaseCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onIngredientChecked(ingredient.getIngredientId(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // ViewHolder class to define the item layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName, quantity;
        CheckBox purchaseCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            quantity = itemView.findViewById(R.id.tv_ingredient_quantity);
            purchaseCheckbox = itemView.findViewById(R.id.checkbox_purchased);
        }
    }

    // Listener interface to handle CheckBox events
    public interface OnIngredientCheckedListener {
        void onIngredientChecked(int ingredientId, boolean isChecked);
    }
}
